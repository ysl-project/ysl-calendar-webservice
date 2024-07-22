package com.ysl.calendar.main.repository;

import java.util.ArrayList;

/**
 * Main 기능 관련된 mybatis class
 */
public interface MainRepository {

    boolean save(Object object);

    ArrayList<Object> selectAll(String key);
}
