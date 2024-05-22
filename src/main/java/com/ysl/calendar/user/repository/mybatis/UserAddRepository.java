package com.ysl.calendar.user.repository.mybatis;

import com.ysl.calendar.domain.users.CalendarUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserAddRepository implements com.ysl.calendar.user.repository.UserAddRepository {
    private final UserAddMapper userAddMapper;
    
    // 회원가입
    @Override
    public CalendarUser save(CalendarUser calendarUser) {
        log.info("[회원가입]");
        userAddMapper.save(calendarUser);
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


}