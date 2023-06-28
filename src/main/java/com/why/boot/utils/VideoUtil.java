//package com.why.boot.utils;
//
//import java.io.File;
//import java.math.BigDecimal;
//
////import it.sauronsoftware.jave.Encoder;
////import it.sauronsoftware.jave.MultimediaInfo;
//
//
///**
// * @Description: TODO
// * @author: why
// * @ClassName: VideoUtil
// * @CreateTime: 2023/3/18 12:05
// */
//
//public class VideoUtil {
//    /**
//     *
//     * @description: 根据本地视频路径返回该视频时长
//     * @param videoPath 视频路径
//     * @return: java.lang.String
//     * @author:
//     * @time: 2023/3/18 12:06
//     */
//    public static String readVideo(String videoPath) {
//        String durationStr;
//        File file = new File(videoPath);
//        Encoder encoder = new Encoder();
//        long sum = 0;
//        try {
//            MultimediaInfo m = encoder.getInfo(file);
//            sum = m.getDuration()/1000; //时长sum单位：秒
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        BigDecimal duration =BigDecimal.valueOf(sum);
//        durationStr = durationFormatToString(duration);
//        System.out.println("视频时长:"+durationStr);
//
//        return durationStr;
//    }
//
//
//    /**
//     *
//     * @description: 将视频时长转换成"00:00:00"的字符串格式
//     * @param duration 视频时长
//     * @return: java.lang.String
//     * @author:
//     * @time: 2023/3/18 12:08
//     */
//    public static String durationFormatToString(BigDecimal duration) {
//        BigDecimal nine = BigDecimal.valueOf(9);
//        BigDecimal sixty = BigDecimal.valueOf(60);
//
//        BigDecimal second = duration.divideAndRemainder(sixty)[1];
//        BigDecimal minute = duration.subtract(second).divide(sixty).divideAndRemainder(sixty)[1];
//        BigDecimal hour = duration.subtract(second).divideToIntegralValue(BigDecimal.valueOf(3600));
//
//        String str = "";
//        if (hour.compareTo(nine)>0) {
//            str += hour.intValue() + ":";
//        }else{
//            str += "0" + hour.intValue() + ":";
//        }
//        if (minute.compareTo(nine)>0) {
//            str += minute.intValue() + ":";
//        }else{
//            str += "0" + minute.intValue() + ":";
//        }
//        if (second.compareTo(nine)>0) {
//            str += second.intValue();
//        }else{
//            str +="0"+ second.intValue();
//        }
//        return str;
//    }
//
//
//    public static void main(String[] args) {
//        String path="E:\\onlineCourseVideo\\03.mp4";
//        System.out.println(readVideo(path));
//    }
//}