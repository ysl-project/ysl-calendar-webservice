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

    public CalendarCategory select(CalendarCategory category){
        log.debug("== 카테고리 조회 start ==");
        Object obj = null;

        try{
            obj = categoryRepository.select(Long.parseLong(category.getId()));
        }catch (Exception e){
            e.printStackTrace();
        }

        log.debug("return Obj [" + ((CalendarCategory)obj).toString() + "]");
        return (CalendarCategory) obj;
    }

    public CalendarCategory updateCategory(CalendarCategory calendarCategory){
        log.debug("== 카테고리 수정 start ==");
        CalendarCategory updateCategory = null;

        try{
            // 수정 메소드
        }catch (Exception e){
            e.printStackTrace();
        }

        return updateCategory;
    }

    public boolean deleteCategory(Long categoryId){
        log.debug("== 카테고리 삭제 start ==");
        boolean result = false;

        try{
            log.debug("삭제할 카테고리 ID [" + String.valueOf(categoryId) + "]");
            result = categoryRepository.deleteByKey(categoryId);

            log.debug("삭제 결과 [" + result + "]");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return result;
    }



}
