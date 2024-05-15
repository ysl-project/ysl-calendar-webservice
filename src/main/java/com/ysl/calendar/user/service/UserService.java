package com.ysl.calendar.user.service;

import com.ysl.calendar.domain.users.CalendarUser;
import com.ysl.calendar.domain.users.CalendarUserRepository;
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

    private final CalendarUserRepository calendarUserRepository;
    private final MailService mailService;
    private final UserAddRepository userAddRepository;

    private static String emailAuthCode = null;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

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
        mailService.sendEmail(toEmail, title, authCode);

        // 4. 인증코드 static 변수에 저장 @todo : redis에 인증코드 저장하는 형식으로 수정 예정
        emailAuthCode = authCode;
    }

    /* 이메일 중복 체크 */
    private void checkDuplicateEmail(String email) throws Exception {
        Optional<CalendarUser> user = Optional.ofNullable(calendarUserRepository.findByEmail(email));
        if(user.isPresent()){
            log.debug("캘린더스 회원의 이메일입니다. 이메일 중복");
            throw new Exception(); // @TODO : 예외처리
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

        // 1. 유효시간 검증 @todo 레디스 저장시간을 통해 인증코드 유효시간 체크

        log.debug("이메일 [" + email + "] 입력된 인증코드 [" + authCode + "] 서버에 저장된 인증코드 [" + emailAuthCode + "]");

        // 2. 이메일 중복 체크
        this.checkDuplicateEmail(email);

        // 3. 서버에 저장된 이메일 인증코드와 비교하여 true/false 리턴
        boolean validationCheck = emailAuthCode.equals(authCode);
        log.debug("인증코드 결과 [" + validationCheck + "]");
        log.debug("== 이메일 인증코드 검증 end ==");
        return validationCheck;
    }

    // 회원가입
    public CalendarUser save(CalendarUser calendarUser) {
        return userAddRepository.save(calendarUser);
    }
}
