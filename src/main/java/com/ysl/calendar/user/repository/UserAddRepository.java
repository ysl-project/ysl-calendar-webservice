package com.ysl.calendar.user.repository;

import com.ysl.calendar.domain.users.CalendarUser;

public interface UserAddRepository {
    // 회원가입
    CalendarUser save(CalendarUser calendarUser);

    // 이메일로 회원조회
    CalendarUser findByEmail(String email);

    // 아이디로 회원조회
    CalendarUser findById(String id);

    // 회원정보 조회
    CalendarUser getUserById(String id);
    
}
