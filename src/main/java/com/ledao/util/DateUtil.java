package com.ledao.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author LeDao
 * @company
 * @create 2022-01-25 0:23
 */
public class DateUtil {

    /**
     * 给日期加天数
     *
     * @param date 要加天数的时间
     * @param days 添加的天数
     * @return
     */
    public static Date dateAddDays(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public static String getCurrentDateStr2() throws Exception {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }
}
