package com.numble.webnovelservice.util.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter{

    private static final String DATE_FORMAT = "yyyy.MM.dd";

    public static String toStringFormat(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTime.format(formatter);
    }
}
