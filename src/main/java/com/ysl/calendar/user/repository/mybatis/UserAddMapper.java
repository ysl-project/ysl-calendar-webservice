package com.ysl.calendar.user.repository.mybatis;

import com.ysl.calendar.domain.users.CalendarUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAddMapper {
    // 회원가입
    void save(CalendarUser calendarUser);

    // 회원조회
    CalendarUser findByEmail(String email);

    // 로그인
    CalendarUser findById(String id);
}