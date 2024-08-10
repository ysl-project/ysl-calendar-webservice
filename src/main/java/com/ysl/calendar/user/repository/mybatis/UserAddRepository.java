package com.ysl.calendar.user.repository.mybatis;

import com.ysl.calendar.domain.users.CalendarUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserAddRepository implements com.ysl.calendar.user.repository.UserAddRepository {
    private final UserAddMapper userAddMapper;
    
    // 회원가입
    @Override
    public CalendarUser save(CalendarUser calendarUser) {
        log.debug("[회원가입]");
        try {
            userAddMapper.save(calendarUser);
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("회원가입 에러 발생");
        }
        return calendarUser;
    }

    @Override
    public CalendarUser findByEmail(String email) {
        log.info("email [" + email + "] --> 회원정보 조회");
        CalendarUser findUser = null;
        try{
            findUser = (CalendarUser) userAddMapper.findByEmail(email);
        }catch (Exception e){ // @todo : 에러 로직 수정
            log.error(e.getMessage());
            log.error("에러 발생");
        }

        return findUser;
    }

    @Override
    public CalendarUser findById(String id) {
        log.info("User ID [" + id + "] --> id를 검증하고 로그인을 진행합니다.");
        CalendarUser user = null;
        try{
            user = (CalendarUser) userAddMapper.findById(id);
        }catch (Exception e){ // @todo : 에러 로직 수정
            log.error(e.getMessage());
            log.error("에러 발생");
        }

        return user;
    }

    @Override
    public ArrayList<CalendarUser> findByNickname(String nickname) {
        ArrayList<CalendarUser> userList = null;
        try{
            userList = userAddMapper.findByNickname(nickname);
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("에러 발생");
        }

        return userList;
    }

    /* 회원정보 조회 */
    @Override
    public CalendarUser getUserById(String id) {
        log.debug("[회원정보 조회 ] ID : " + id);
        CalendarUser getUser = null;
        try {
            getUser = userAddMapper.getUserById(id);
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("회원정보 조회 에러 발생");
        }
        return getUser;
    }

    /* 회원정보 수정 */
    @Override
    public void updateUser(CalendarUser calendarUser) {
        log.debug("[회원정보 수정 ] ID : " + calendarUser.getId());
        try {
            userAddMapper.updateUser(calendarUser);
        }catch (IllegalArgumentException ie){
            log.error("회원정보 없음");
            log.error(ie.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("회원정보 수정 에러 발생");
        }
    }
}