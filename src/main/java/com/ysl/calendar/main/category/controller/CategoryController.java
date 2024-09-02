package com.ysl.calendar.main.category.controller;

import com.ysl.calendar.domain.categories.CalendarCategory;
import com.ysl.calendar.main.category.service.CategoryService;
import com.ysl.calendar.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    @PostMapping("/{id}")
    public CalendarCategory updateCategory(@PathVariable("id") Long id, @ModelAttribute CalendarCategory calendarCategory){


        return calendarCategory;
    }


    /* 카테고리 삭제 */
    @DeleteMapping("/delete-action/{id}")
    public ResponseEntity deleteCategory(HttpServletRequest request, @PathVariable("id") Long id){
        // http request header 에서 인증정보 확인 필요
        CommonUtil.printHttpRequestHeader(request);

        // @TODO : verify method -> 인증 완료 되면 delete 진행


        // delete 진행 @TODO : return 타입 정하기
        boolean result = categoryService.deleteCategory(id);
        if(result){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
