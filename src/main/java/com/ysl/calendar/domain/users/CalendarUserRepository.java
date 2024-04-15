package com.ysl.calendar.domain.users;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CalendarUserRepository {

    private static final Map<Long, CalendarUser> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    /* email로 user 찾는 로직 */
    public CalendarUser findByEmail(String email) {
        // DB에 저장된 값이 없기때문에 null 리턴
        // @todo : db와 연결해서 email 찾는 로직 작성 예정 [ select * from cal_user where email = #email# ]
        return null;
    }

}
