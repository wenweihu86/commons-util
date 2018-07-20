package com.github.wenweihu86.commons.util;

import com.github.wenweihu86.commmons.util.DateUtils;
import org.junit.Test;

import java.util.Date;

public class DateUtilTest {

    @Test
    public void testDateToStr() {
        Date now = DateUtils.now();
        String nowStr = DateUtils.dateToString(now);
        System.out.println(nowStr);
    }
}
