package com.ysl.calendar.main.category.controller;

import com.ysl.calendar.domain.categories.CalendarCategory;
import com.ysl.calendar.main.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j
@RestController()
@RequestMapping("/main/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /* 카테고리 추가 */
    @PostMapping
    public ArrayList<CalendarCategory> addCategory(@ModelAttribute CalendarCategory calendarCategory, ArrayList<CalendarCategory> categoryList){
        categoryList = categoryService.addCategory(calendarCategory);
        return categoryList;
    }

    /* 유저별 카테고리 조회 */
    @GetMapping
    public ArrayList<CalendarCategory> selectCategoryAll(@RequestParam("id") String userId, ArrayList<CalendarCategory> categoryList){
        return categoryService.selectCategoryAll(userId);
    }

    /* 카테고리 수정 */


}
