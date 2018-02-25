package com.github.wenweihu86.commons.util;

import com.github.wenweihu86.commmons.util.DateUtil;
import org.junit.Test;

import java.util.Date;

public class DateUtilTest {

    @Test
    public void testDateToStr() {
        Date now = DateUtil.now();
        String nowStr = DateUtil.dateToString(now);
        System.out.println(nowStr);
    }
}
