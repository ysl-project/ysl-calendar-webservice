package com.ysl.calendar.user.repository.mybatis;

import com.ysl.calendar.domain.users.CalendarUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface UserAddMapper {
    // 회원가입
    void save(CalendarUser calendarUser);

    // 회원조회
    CalendarUser findByEmail(String email);

    // 로그인
    CalendarUser findById(String id);

    // 닉네임 체크
    ArrayList<CalendarUser> findByNickname(String nickname);
}