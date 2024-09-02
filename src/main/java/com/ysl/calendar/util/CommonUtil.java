package com.ysl.calendar.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CommonUtil {

    // http request header 정보 프린트 메소드
    public static void printHttpRequestHeader(HttpServletRequest request){
        log.debug("===== http request header =====");
        request.getHeaderNames().asIterator()
                .forEachRemaining(key -> log.debug("## " + key + " : " + request.getHeader(key)));
        log.debug("===============================");
    }


}
