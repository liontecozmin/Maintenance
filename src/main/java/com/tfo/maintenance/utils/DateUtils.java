package com.tfo.maintenance.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Date toDate(String date)
    {
        Date finalDate= null;
        try {
            finalDate = new SimpleDateFormat("MM.dd.yyyy").parse(date);
        } catch (ParseException e) {
            return new Date("01.01.2020");
        }
        System.out.println(finalDate);
        return finalDate;
    }
}
