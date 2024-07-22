package com.ysl.calendar.main.category.service;

import com.ysl.calendar.domain.categories.CalendarCategory;
import com.ysl.calendar.main.repository.mybatis.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public boolean save(CalendarCategory category){
        return categoryRepository.save(category);
    }

    public ArrayList<Object> selectAll(String createUser){
        return categoryRepository.selectAll(createUser);
    }



}
