package com.why.boot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WebUtil {
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";


    /**
     *
     * @description: string转int
     * @param str 待转换字符串
     * @param defaultValue 出现异常时返回值
     * @return: int
     * @author: why
     * @time: 2023/3/19 22:15
     */
    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     *
     * @description: Date类型转换为String
     * @param date      日期Date类型
     * @param formatStr 标准类型
     * @return: java.lang.String
     * @author: why
     * @time: 2023/3/19 22:16
     */
    public static String dateToStrong(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        if (date == null) {
            date = new Date();
        }
        return format.format(date);
    }

    /**
     *
     * @description: long转int
     * @param value        要转换的值
     * @param defaultValue 出现异常时的默认值
     * @return: int
     * @author: why
     * @time: 2023/3/19 22:17
     */

    public static int parseLongToInt(Long value, int defaultValue) {
        try {
            return parseInt(Long.toString(value), defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
