package com.ysl.calendar.user.repository;

import com.ysl.calendar.domain.users.CalendarUser;

public interface UserAddRepository {
    // 회원가입
    CalendarUser save(CalendarUser calendarUser);

}
