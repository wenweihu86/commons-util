package com.github.wenweihu86.commmons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (format == null) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date now() {
        return Calendar.getInstance().getTime();
    }
}
