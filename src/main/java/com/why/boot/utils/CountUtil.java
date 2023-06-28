package com.why.boot.utils;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: CountUtil
 * @CreateTime: 2023/4/1 20:33
 */

@Component("CountUtil")
public class CountUtil {

    /**
     *
     * @description:   两数相除转位百分比

     * @param a        除数
     * @param b        被除数
     * @return: java.lang.String
     * @author: why
     * @time: 2023/4/1 20:35
     */
    public static String toPercent(int a,int b) {
        if (a%b==0) {
            return a/b*100+"%";
        }else {
            return (double)Math.round( a/(double)b*1000)/10+"%";
        }
    }


    /**
     *
     * @description:      遍历集合计算时间秒和

     * @param timeList     时间集合
     * @return: int
     * @author: why
     * @time: 2023/4/1 20:35
     */
    public static int totalSecond(List<Object> timeList){
        int totalSecond=0;
        for(Object object:timeList){
            String time=object.toString();
            totalSecond+=stringFormatToS(time);
        }
        return  totalSecond;
    }



    /**
     *
     * @description:   将时分秒时间格式转换为秒

     * @param time     时分秒格式时间字符串
     * @return: int
     * @author: why
     * @time: 2023/4/1 20:36
     */
    public static int stringFormatToS(String time){
        String[] timeStr=time.split(":");
        int hour=Integer.parseInt(timeStr[0]);
        int min=Integer.parseInt(timeStr[1]);
        int sec=Integer.parseInt(timeStr[2]);
        return hour*3600+min*60+sec;
    }


    public static Double percentToNum(String percent) throws ParseException {
        if(percent.equals("0%")){
            return 0.0;
        }else if(percent.equals("100%")){
            return 1.0;
        }else{
            return (Double) NumberFormat.getPercentInstance().parse(percent);
        }
    }

    public static String timeConvert(String timeStr) throws ParseException {
        // 字符串转为日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(timeStr);

       // 日期格式化为字符串
       return formatter.format(date);
    }



    public static void main(String[] args) {

    }
}