package com.ysl.calendar.user.service;

import com.ysl.calendar.db.util.RedisUtil;
import com.ysl.calendar.domain.users.CalendarUser;
import com.ysl.calendar.user.repository.UserAddRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final MailService mailService;
    private final UserAddRepository userAddRepository;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.auth-code-expiration}")
    private long authCodeExpirationSec;

    /* email 인증코드 전송 로직 */
    public void sendCodeToEmail(String toEmail) throws Exception {
        log.debug("== 이메일 인증 코드 전송 준비 ==");

        // 1. 이메일 중복 체크
        this.checkDuplicateEmail(toEmail);

        // 2. title 및 인증코드 생성
        String title = "캘린더스 이메일 인증 코드";
        String authCode = this.createCode();
        log.debug("이메일 title [" + title + "] 인증코드 [" + authCode + "]");

        // 3. 이메일 전송
        log.debug("이메일 전송 start");
        mailService.sendEmail(toEmail, title, authCode);

        // 4. 인증코드 redis에 저장 key : email / value : authcode
        log.debug("redis 에 이메일 인증코드 저장 -> 인증코드 저장시간은 [" + authCodeExpirationSec + "] 만큼 유효합니다.");
        redisUtil.setRedisDataExpire(toEmail, authCode, authCodeExpirationSec); // @todo : 인증코드 저장시간 안 먹음 --> 확인 필요
    }

    /* 이메일 중복 체크 */
    private void checkDuplicateEmail(String email) throws Exception {
        Optional<CalendarUser> user = Optional.ofNullable(userAddRepository.findByEmail(email));
        if(user.isPresent()){
            log.debug("캘린더스 회원의 이메일입니다. 이메일 중복");
            throw new Exception("이미 가입된 이메일입니다."); // @TODO : 예외처리 (에러메세지 화면으로 리턴)
        }
        log.debug("중복된 이메일이 아닙니다. 이메일 중복 체크 완료");
    }

    /* 이메일 인증 코드 생성 */
    private String createCode() throws NoSuchAlgorithmException {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("이메일 인증코드 생성 중 오류 발생");
            log.error(e.getMessage());
            throw e;
        }
    }

    /* 이메일 인증코드 검증 */
    public boolean verifiedCode(String email, String authCode) throws Exception{
        log.debug("== 이메일 인증코드 검증 start ==");

        // 1. redis 에서 인증코드 조회
        String emailAuthCode = redisUtil.getRedisData(email);
        if(emailAuthCode == null){
            log.error("인증코드 유효시간이 지났습니다. 재생성이 필요합니다.");
            throw new Exception(); // @TODO : 예외처리 (에러메세지 화면으로 리턴)
        }

        log.debug("이메일 [" + email + "] 입력된 인증코드 [" + authCode + "] 서버에 저장된 인증코드 [" + emailAuthCode + "]");

        // 2. 이메일 중복 체크
        this.checkDuplicateEmail(email);

        // 3. redis에 저장된 이메일 인증코드와 비교하여 true/false 리턴
        boolean validationCheck = emailAuthCode.equals(authCode);
        log.debug("인증코드 결과 [" + validationCheck + "]");

        // 4. redis code reset
        redisUtil.deleteRedisData(email);
        log.debug("이메일 인증코드 reset"); // @todo : reset을 할지 안할지 결정해서 정하기

        log.debug("== 이메일 인증코드 검증 end ==");
        return validationCheck;
    }

    // 회원가입
    public CalendarUser save(CalendarUser calendarUser) {
        return userAddRepository.save(calendarUser);
    }

    // 로그인
    public boolean login(CalendarUser calendarUser) {
        CalendarUser loginUser = null;
        String tempPasswd = calendarUser.getPassword();
        boolean loginChk = false;

        try{
            loginUser = (CalendarUser) userAddRepository.findById(calendarUser.getId());
            if(loginUser == null){
                log.error("일치하는 회원정보가 없습니다.");
                throw new NullPointerException();
            }

            // @todo : 복호화 로직
            String passWd = loginUser.getPassword();
            loginChk = tempPasswd.equals(passWd) ? true : false;
        }catch (Exception e){
            log.error("로그인 중 에러 발생");
            log.error(e.getMessage());
        }

        return loginChk;
    }

    // 닉네임 중복 체크
    public boolean checkNickname(String nickname){
        log.debug("== 닉네임 중복 체크 start ==");
        boolean result = false;

        try{
            if(userAddRepository.findByNickname(nickname).size() > 0){
                result = false;
            }else{
                result = true;
            }
        }catch (Exception e){
            log.error("닉네임 중복 체크 중 에러 발생");
            log.error(e.getMessage());
            result = false;
        }

        log.debug("닉네임 중복 체크 결과 [" + result + "]");
        return result;
    }
}
