package com.ysl.calendar.user.repository.mybatis;

import com.ysl.calendar.domain.users.CalendarUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAddMapper {
    // 회원가입
    void save(CalendarUser calendarUser);

    // 회원조회
    CalendarUser findByEmail(String email);

    // 로그인
    CalendarUser findById(String id);

    // 회원정보 조회
    CalendarUser getUserById(@Param("id") String id);
}