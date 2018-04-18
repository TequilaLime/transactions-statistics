package com.homework.utils;

import java.util.Calendar;
import java.util.Date;

public class TransactionRetentionHelper {

    public final static Long ONE_MINUTE = 60_000L;
    public final static Calendar CALENDAR = Calendar.getInstance();

    /**
     * Checks if timestamp is already old or not
     **/
    public static boolean isTimestampOld(Long timestamp) {
        return ((System.currentTimeMillis() - timestamp) > ONE_MINUTE) ? true : false;
    }

    /**
     * returns value between 0-59
     **/
    public static Integer getSecond(Long timestamp) {
        CALENDAR.setTime(new Date(timestamp));
        return CALENDAR.get(Calendar.SECOND);
    }
}
