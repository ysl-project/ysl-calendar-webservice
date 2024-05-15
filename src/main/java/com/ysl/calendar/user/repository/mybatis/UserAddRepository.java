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
}