package com.ledao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author LeDao
 * @company
 * @create 2022-01-24 23:48
 */
public class MyTest {

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        System.out.println(calendar.getTime());
    }
}
