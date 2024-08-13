package com.ysl.calendar.main.repository.mybatis;

import com.ysl.calendar.domain.categories.CalendarCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CategoryMapper {

    // category 추가
    boolean save(CalendarCategory category);

    // 회원별 category 조회
    ArrayList<CalendarCategory> selectAll(String createUser);

    // id로 category 조회
    CalendarCategory select(Long categoryId);

    // category 삭제
    int deleteByKey(Long categoryId);

}
