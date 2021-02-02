package com.etl.ds.core.utils;


import com.etl.ds.core.exception.BizException;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Author: huyisen@cvte.com
 * Date: 2020-03-11
 * Copyright © 2020 CVTE. All Rights Reserved.
 */
public class DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HH_MM = "HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new BizException("时间字符串格式错误，期待格式：" + YYYY_MM_DD + "，真实格式：" + dateStr);
        }
        return date;
    }

    public static Time parseTime(String timeStr) {
        return Time.valueOf(timeStr.trim() + ":00");
    }

    public static String formatAtDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        return sdf.format(date);
    }

    public static String formatAtLocalDate(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        return dtf.format(date);
    }

    public static String formatAtLocalTime(LocalDateTime time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(HH_MM);
        return dtf.format(time);
    }

    public static String formatAtTime(Time time) {
        SimpleDateFormat sdf = new SimpleDateFormat(HH_MM_SS);
        return sdf.format(time);
    }

    public static String formatAtSecond(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return sdf.format(date);
    }

    public static String formatAtMillis(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
        return sdf.format(date);
    }

    private static final String FORMAT_PATTERN = "yyyy-MM-dd";
    private static final String FORMAT_PATTERN_MONTH = "yyyy-MM";


    public static String bizDate2VarDate(String bizDate) {
        String varDate;
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        try {
            varDate = sdf.format(new Date(sdf.parse(bizDate).getTime() + TimeUnit.DAYS.toMillis(1)));
        } catch (ParseException e) {
            throw new BizException("时间字符串格式错误，期待格式：" + YYYY_MM_DD + "，真实格式：" + bizDate);
        }
        return varDate;
    }

    public static String varDate2BizDate2(String varDate) {
        String bizDate;
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        try {
            bizDate = sdf.format(new Date(sdf.parse(varDate).getTime() - TimeUnit.DAYS.toMillis(1)));
        } catch (ParseException e) {
            throw new BizException("时间字符串格式错误，期待格式：" + YYYY_MM_DD + "，真实格式：" + varDate);
        }
        return bizDate;
    }
}
