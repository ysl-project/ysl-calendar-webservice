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

    public ArrayList<CalendarCategory> addCategory(CalendarCategory calendarCategory){

        log.debug("== 카테고리 insert ==");
        log.debug("전달된 카테고리 정보 [" + calendarCategory.toString() + "]");
        String createUser = calendarCategory.getCreate_user();
        ArrayList<CalendarCategory> returnList = null;

        try{
            if(!categoryRepository.save(calendarCategory)){
                // 중복일 경우 또는 db insert 가 안될 경우
                throw new Exception();
            }

            log.debug("카테고리 저장 완료. 전체 카테고리 리스트를 조회 start");

            ArrayList<Object> objList = categoryRepository.selectAll(createUser);
            returnList = new ArrayList<>();
            for(Object obj : objList){
                if(obj instanceof CalendarCategory){
                    returnList.add((CalendarCategory) obj);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        log.debug("returnList [" + returnList.toString() + "]");
        return returnList;
    }

    public ArrayList<CalendarCategory> selectCategoryAll(String createUser){
        log.debug("== 전체 카테고리 리스트 조회 start ==");
        ArrayList<CalendarCategory> returnList = null;

        try{
            ArrayList<Object> objList = categoryRepository.selectAll(createUser);
            returnList = new ArrayList<>();
            for(Object obj : objList){
                if(obj instanceof CalendarCategory){
                    returnList.add((CalendarCategory) obj);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        log.debug("returnList [" + returnList.toString() + "]");
        return returnList;
    }



}
