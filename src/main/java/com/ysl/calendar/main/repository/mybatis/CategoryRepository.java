package com.ysl.calendar.main.repository.mybatis;

import com.ysl.calendar.domain.categories.CalendarCategory;
import com.ysl.calendar.main.repository.MainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoryRepository implements MainRepository {
    private final CategoryMapper categoryMapper;
    @Override
    public boolean save(Object object) {
        try{
            CalendarCategory category = (CalendarCategory) object;
            return categoryMapper.save(category);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Object> selectAll(String createUser) {
        log.debug("USER ID [" + createUser + "] 의 전체 카테고리를 조회합니다.");

        ArrayList<CalendarCategory> categoryList = null;
        try{
            categoryList = categoryMapper.selectAll(createUser);

            for(int i=0; i<categoryList.size(); i++){
                log.debug(i+1 + "번째 카테고리 [" + categoryList.get(i) + "]");
            }


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return (ArrayList<Object>) (ArrayList<?>) categoryList;
    }

    @Override
    public Object select(Object key) {
        CalendarCategory calendarCategory = null;

        try{
            if(key != null){
                calendarCategory = categoryMapper.select((Long)key);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return calendarCategory;
    }


    @Override
    public boolean deleteByKey(Object key) {
        int result = 0;
        try{
            if(key != null){
                result = categoryMapper.deleteByKey((Long)key);
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return result > 0 ? true : false;
    }
}
