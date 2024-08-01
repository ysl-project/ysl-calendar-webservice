package com.ysl.calendar.user.repository;

import com.ysl.calendar.domain.users.CalendarUser;

import java.util.ArrayList;

public interface UserAddRepository {
    // 회원가입
    CalendarUser save(CalendarUser calendarUser);

    // 이메일로 회원조회
    CalendarUser findByEmail(String email);

    // 아이디로 회원조회
    CalendarUser findById(String id);

    // 닉네임 존재 여부 확인
    public ArrayList<CalendarUser> findByNickname(String nickname);

}
