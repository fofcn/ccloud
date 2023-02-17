package com.github.ccloud.common.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String PATTERN_NORMAL = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(Date date) {
        return dateToString(date, PATTERN_NORMAL);
    }

    public static String dateToString(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date stringToDate(String dataStr) {
        return stringToDate(dataStr, PATTERN_NORMAL);
    }

    public static Date stringToDate(String dateStr, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
