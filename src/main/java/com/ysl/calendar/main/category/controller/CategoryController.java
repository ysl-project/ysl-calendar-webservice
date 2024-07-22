package com.ysl.calendar.main.category.controller;

import com.ysl.calendar.domain.categories.CalendarCategory;
import com.ysl.calendar.main.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j
@RestController()
@RequestMapping("/main/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ArrayList<CalendarCategory> addCategory(@ModelAttribute CalendarCategory calendarCategory, ArrayList<CalendarCategory> categoryList){
        log.debug("== category insert start ==");
        log.debug("전달된 카테고리 정보 [" + calendarCategory.toString() + "]");
        String createUser = calendarCategory.getCreate_user();

        try{
            if(!categoryService.save(calendarCategory)){
                throw new Exception();
            }

            log.debug("카테고리 저장 완료. 전체 리스트를 조회합니다.");

            ArrayList<Object> objList = categoryService.selectAll(createUser);
            categoryList = new ArrayList<>();
            for(Object obj : objList){
                if(obj instanceof CalendarCategory){
                    categoryList.add((CalendarCategory) obj);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return categoryList;
    }


}
