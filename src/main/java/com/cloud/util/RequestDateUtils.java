package com.cloud.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestDateUtils {

    public static String genRequestDateStr() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return localDateTime.format(dateTimeFormatter);
    }


}
