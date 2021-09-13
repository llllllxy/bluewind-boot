package com.bluewind.boot.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author liuxingyu01
 * @date 2021-4-25 16:29:08
 * @description 日期时间计算公共类(纯JDK8)
 **/
public class DateTool {
    private final static String MILLI_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String DATE_FORMAT = "yyyy-MM-dd";


    /**
     * 获取当前时间 比如 DateTool.getCurrentTime("yyyy-MM-dd HH:mm:ss"); 返回值为 2012-05-15 23:44:20
     * <p>
     * 和下面的getNowTime方法一样的，为了方便迁移
     *
     * @param format
     * @return dateTimeStr
     */
    public static String getCurrentTime(String format) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(dateTime);
    }


    /**
     * 获取当前时间 比如 DateTool.getNowTime(); 返回值为 20120515234420
     * <p>
     * 和下面的getNowTime方法一样的，为了方便迁移
     *
     * @return dateTimeStr
     */
    public static String getCurrentTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return dateTimeFormatter.format(dateTime);
    }


    /**
     * 获取当前日期，返回固定格式 yyyyMMdd
     *
     * @return
     */
    public static String getCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return dateTimeFormatter.format(today);
    }


    /**
     * 获取当前日期，返回固定格式 yyyyMMdd
     *
     * @return
     */
    public static String getCurrentDate(String format) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(today);
    }


    /**
     * 获取当前时间 比如 DateTool.getNowTime("yyyy-MM-dd HH:mm:ss"); 返回值为 2012-05-15
     * 23:44:20
     *
     * @param format
     * @return
     */
    public static String getNowTime(String format) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(dateTime);
    }


    /**
     * 获取当前时间，返回固定格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return dateTimeFormatter.format(dateTime);
    }

    /**
     * 获取当前日期 比如 DateTool.getToday("yyyyMMdd"); 返回值为 20120515
     *
     * @param format
     * @return
     */
    public static String getToday(String format) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(today);
    }

    /**
     * 获取当前日期，返回固定格式 yyyy-MM-dd
     *
     * @return
     */
    public static String getToday() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTimeFormatter.format(today);
    }


    /**
     * 将一个日期时间字符串，由一个格式转换成另一个格式 比如:DateTool.timeFormat("20120506123344",
     * "yyyyMMddHHmmss", "yyyy年M月d日 HH:mm") 返回值为2012年5月6日 12:33
     *
     * @param oldTime
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public static String timeFormat(String oldTime, String oldFormat, String newFormat) {
        try {
            DateTimeFormatter olddf = DateTimeFormatter.ofPattern(oldFormat);
            LocalDateTime localDateTime = LocalDateTime.parse(oldTime, olddf);
            DateTimeFormatter newdf = DateTimeFormatter.ofPattern(newFormat);

            return newdf.format(localDateTime);
        } catch (Exception e) {
            return oldTime;
        }
    }

    /**
     * 将一个日期时间字符串，由一个格式转换成另一个格式 比如:DateTool.timeFormat("20120506123344",
     * "yyyyMMdd", "yyyy年M月d日") 返回值为2012年5月6日 12:33
     *
     * @param oldDate
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public static String dateFormat(String oldDate, String oldFormat, String newFormat) {
        try {
            DateTimeFormatter olddf = DateTimeFormatter.ofPattern(oldFormat);
            LocalDate localDate = LocalDate.parse(oldDate, olddf);
            DateTimeFormatter newdf = DateTimeFormatter.ofPattern(newFormat);

            return newdf.format(localDate);
        } catch (Exception e) {
            return oldDate;
        }
    }

    /**
     * 在当前时间上增加 若干小时
     *
     * @param hourCount
     * @return 固定格式 yyyy-MM-dd HH:mm:ss
     */
    public static String plusHours(int hourCount) {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime newTime = time.plusHours(hourCount); // 增加几小时
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return dateTimeFormatter.format(newTime);
    }

    /**
     * 在指定时间上增加 若干小时
     *
     * @param hourCount
     * @return 固定格式 yyyy-MM-dd HH:mm:ss
     */
    public static String plusHours(String oldTime, String oldFormat, int hourCount) {
        DateTimeFormatter olddf = DateTimeFormatter.ofPattern(oldFormat);
        LocalDateTime localDateTime = LocalDateTime.parse(oldTime, olddf);
        LocalDateTime newTime = localDateTime.plusHours(hourCount); // 增加几小时
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return dateTimeFormatter.format(newTime);
    }

    /**
     * 在当前时间上增加 若干分钟
     *
     * @param minutesCount
     * @return 固定格式 yyyy-MM-dd HH:mm:ss
     */
    public static String plusMinutes(int minutesCount) {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime newTime = time.plusMinutes(minutesCount); // 增加几分钟
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return dateTimeFormatter.format(newTime);
    }

    /**
     * 在指定时间上增加 若干分钟
     *
     * @param minutesCount
     * @return 固定格式 yyyy-MM-dd HH:mm:ss
     */
    public static String plusMinutes(String oldTime, String oldFormat, int minutesCount) {
        DateTimeFormatter olddf = DateTimeFormatter.ofPattern(oldFormat);
        LocalDateTime localDateTime = LocalDateTime.parse(oldTime, olddf);
        LocalDateTime newTime = localDateTime.plusMinutes(minutesCount); // 增加几分钟
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return dateTimeFormatter.format(newTime);
    }

    /**
     * 在当前时间上增加 若干秒
     *
     * @param secondsCount
     * @return 固定格式 yyyy-MM-dd HH:mm:ss
     */
    public static String plusSeconds(int secondsCount) {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime newTime = time.plusSeconds(secondsCount); // 增加几秒
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return dateTimeFormatter.format(newTime);
    }

    /**
     * 在指定时间上增加 若干秒
     *
     * @param secondsCount
     * @return 固定格式 yyyy-MM-dd HH:mm:ss
     */
    public static String plusSeconds(String oldTime, String oldFormat, int secondsCount) {
        DateTimeFormatter olddf = DateTimeFormatter.ofPattern(oldFormat);
        LocalDateTime localDateTime = LocalDateTime.parse(oldTime, olddf);
        LocalDateTime newTime = localDateTime.plusSeconds(secondsCount); // 增加几秒
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return dateTimeFormatter.format(newTime);
    }

    /**
     * 在当前时间上减少 若干秒
     *
     * @param secondsCount
     * @return 固定格式 yyyy-MM-dd HH:mm:ss
     */
    public static String minusSeconds(int secondsCount) {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime newTime = time.minusSeconds(secondsCount); // 增加几秒
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return dateTimeFormatter.format(newTime);
    }

    /**
     * 在指定时间上减少 若干秒
     *
     * @param secondsCount
     * @return 固定格式 yyyy-MM-dd HH:mm:ss
     */
    public static String minusSeconds(String oldTime, String oldFormat, int secondsCount) {
        DateTimeFormatter olddf = DateTimeFormatter.ofPattern(oldFormat);
        LocalDateTime localDateTime = LocalDateTime.parse(oldTime, olddf);
        LocalDateTime newTime = localDateTime.minusSeconds(secondsCount); // 增加几秒
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return dateTimeFormatter.format(newTime);
    }

    /**
     * 获取昨天
     *
     * @return 固定格式 yyyy-MM-dd
     */
    public static String getYesterday() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.minus(1, ChronoUnit.DAYS); // 使用变量赋值
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTimeFormatter.format(nextWeek);
    }

    /**
     * 获取明天
     *
     * @return 固定格式 yyyy-MM-dd
     */
    public static String getTomorrow() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plus(1, ChronoUnit.DAYS); // 使用变量赋值
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTimeFormatter.format(nextWeek);
    }

    /**
     * 获取一周后的日期
     *
     * @return 固定格式 yyyy-MM-dd
     */
    public static String nextWeek() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS); // 使用变量赋值
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTimeFormatter.format(nextWeek);
    }

    /**
     * 获取1年前的日期
     *
     * @return 固定格式 yyyy-MM-dd
     */
    public static String previousYear() {
        LocalDate today = LocalDate.now();
        LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTimeFormatter.format(previousYear);
    }

    /**
     * 获取1年后的日期
     *
     * @return 固定格式 yyyy-MM-dd
     */
    public static String nextYear() {
        LocalDate today = LocalDate.now();
        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTimeFormatter.format(nextYear);
    }

    /**
     * 判断今年是否闰年
     *
     * @return
     */
    public static boolean isLeapYear() {
        LocalDate today = LocalDate.now();
        return today.isLeapYear();
    }

    /**
     * 判断某天是否闰年
     *
     * @return
     */
    public static boolean isLeapYear(String date, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(date, df);
        return localDate.isLeapYear();
    }

    /**
     * 计量时间差 (time2 - time1)，返回秒数 比如 DateTool.timeDiff("2012-10-25 02:49:15",
     * "yyyy-MM-dd HH:mm:ss", "20121025025030", "yyyyMMddHHmmss") 返回值为 75
     *
     * @param previousTime
     * @param previousFormat
     * @param nextTime
     * @param nextFormat
     * @return
     */
    public static long timeDiff(String previousTime, String previousFormat, String nextTime, String nextFormat) {
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern(previousFormat);
        LocalDateTime previousDateTime = LocalDateTime.parse(previousTime, df1);
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern(nextFormat);
        LocalDateTime nextDateTime = LocalDateTime.parse(nextTime, df2);
        long previousSecond = previousDateTime.toEpochSecond(ZoneOffset.ofHours(0));
        long nextSecond = nextDateTime.toEpochSecond(ZoneOffset.ofHours(0));

        return Math.abs(nextSecond - previousSecond);
    }


    /**
     * 计量时间差 (time2 - time1)，返回秒数 比如 DateTool.timeDiffSeconds("2012-10-25 02:49:15",
     * "yyyy-MM-dd HH:mm:ss", "20121025025030", "yyyyMMddHHmmss") 返回值为 75
     *
     * @param previousTime
     * @param previousFormat
     * @param nextTime
     * @param nextFormat
     * @return
     */
    public static long timeDiffSeconds(String previousTime, String previousFormat, String nextTime, String nextFormat) {
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern(previousFormat);
        LocalDateTime previousDateTime = LocalDateTime.parse(previousTime, df1);

        DateTimeFormatter df2 = DateTimeFormatter.ofPattern(nextFormat);
        LocalDateTime nextDateTime = LocalDateTime.parse(nextTime, df2);

        Duration duration = Duration.between(previousDateTime, nextDateTime);

        long days = duration.toDays(); // 相差的天数(所有的)
        long hours = duration.toHours(); // 相差的小时数(所有的)
        long minutes = duration.toMinutes(); // 相差的分钟数(所有的)
        long millis = duration.toMillis(); // 相差毫秒数(所有的)
        long nanos = duration.toNanos(); // 相差的纳秒数(所有的)

        return Math.abs(Math.round(millis / 1000L));
    }


    /**
     * 计量时间差 (time2 - time1)，返回秒数 比如 DateTool.timeDiffSeconds("2012-10-25 02:49:15","2012-10-25 02:50:30") 返回值为 75
     *
     * @param previousTime
     * @param nextTime
     * @return
     */
    public static long timeDiffSeconds(String previousTime, String nextTime) {
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalDateTime previousDateTime = LocalDateTime.parse(previousTime, df1);

        DateTimeFormatter df2 = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalDateTime nextDateTime = LocalDateTime.parse(nextTime, df2);

        Duration duration = Duration.between(previousDateTime, nextDateTime);

        long days = duration.toDays(); // 相差的天数(所有的)
        long hours = duration.toHours(); // 相差的小时数(所有的)
        long minutes = duration.toMinutes(); // 相差的分钟数(所有的)
        long millis = duration.toMillis(); // 相差毫秒数(所有的)
        long nanos = duration.toNanos(); // 相差的纳秒数(所有的)

        return Math.abs(Math.round(millis / 1000L));
    }


    /**
     * 计量时间差 (time2 - time1)，返回分钟数 比如 DateTool.timeDiffSeconds("2012-10-25
     * 02:49:15", "yyyy-MM-dd HH:mm:ss", "20121025025030", "yyyyMMddHHmmss") 返回值为 1
     *
     * @param previousTime
     * @param previousFormat
     * @param nextTime
     * @param nextFormat
     * @return
     */
    public static long timeDiffMinutes(String previousTime, String previousFormat, String nextTime, String nextFormat) {
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern(previousFormat);
        LocalDateTime previousDateTime = LocalDateTime.parse(previousTime, df1);

        DateTimeFormatter df2 = DateTimeFormatter.ofPattern(nextFormat);
        LocalDateTime nextDateTime = LocalDateTime.parse(nextTime, df2);

        Duration duration = Duration.between(previousDateTime, nextDateTime);

        long days = duration.toDays(); // 相差的天数(所有的)
        long hours = duration.toHours(); // 相差的小时数(所有的)
        long minutes = duration.toMinutes(); // 相差的分钟数(所有的)
        long millis = duration.toMillis(); // 相差毫秒数(所有的)
        long nanos = duration.toNanos(); // 相差的纳秒数(所有的)

        return Math.abs(minutes);
    }


    /**
     * 计量时间差 (time2 - time1)，返回分钟数 比如 DateTool.timeDiffSeconds("2012-10-25
     * 02:49:15", "2012-10-25 02:50:30") 返回值为 1
     *
     * @param previousTime
     * @param nextTime
     * @return
     */
    public static long timeDiffMinutes(String previousTime, String nextTime) {
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalDateTime previousDateTime = LocalDateTime.parse(previousTime, df1);

        DateTimeFormatter df2 = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalDateTime nextDateTime = LocalDateTime.parse(nextTime, df2);

        Duration duration = Duration.between(previousDateTime, nextDateTime);

        long days = duration.toDays(); // 相差的天数(所有的)
        long hours = duration.toHours(); // 相差的小时数(所有的)
        long minutes = duration.toMinutes(); // 相差的分钟数(所有的)
        long millis = duration.toMillis(); // 相差毫秒数(所有的)
        long nanos = duration.toNanos(); // 相差的纳秒数(所有的)

        return Math.abs(minutes);
    }


    /**
     *
     * 获取两个日期之间间隔天数2008-12-1~2008-9.29
     */
    /**
     * 计量时间差 (date2 - date1)，返回分钟数 比如 DateTool.timeDiffSeconds("2012-10-25",
     * "yyyy-MM-dd", "20121026", "yyyyMMdd") 返回值为 1
     *
     * @param previousFormat
     * @param nextFormat
     * @return
     */
    public static long dayDiff(String previousDate, String previousFormat, String nextDate, String nextFormat) {
        previousDate = previousDate + " 000000";
        nextDate = nextDate + " 000000";

        DateTimeFormatter df1 = DateTimeFormatter.ofPattern(previousFormat + " HHmmss");
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern(nextFormat + " HHmmss");

        LocalDateTime previousDateTime = LocalDateTime.parse(previousDate, df1);
        LocalDateTime nextDateTime = LocalDateTime.parse(nextDate, df2);
        Duration duration = Duration.between(previousDateTime, nextDateTime);

        long days = duration.toDays(); // 相差的天数(所有的)
        return Math.abs(days);
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @return
     */
    public static String getWeek(String sDate, String sFormat) {
        LocalDate currentDate = null;
        if (sDate != null && !"".equals(sDate) && sFormat != null && !"".equals(sFormat)) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(sFormat);
            currentDate = LocalDate.parse(sDate, df);
        } else {
            currentDate = LocalDate.now();
        }
        // 再转换为时间
        String[][] strArray = {{"MONDAY", "一"}, {"TUESDAY", "二"}, {"WEDNESDAY", "三"}, {"THURSDAY", "四"},
                {"FRIDAY", "五"}, {"SATURDAY", "六"}, {"SUNDAY", "日"}};
        String k = String.valueOf(currentDate.getDayOfWeek());
        // 获取行数
        for (int i = 0; i < strArray.length; i++) {
            if (k.equals(strArray[i][0])) {
                k = strArray[i][1];
                break;
            }
        }
        return "星期" + k;
    }

    /**
     * 字符串时间转换为Unix时间戳(支持秒级)
     *
     * @param dateTime1 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long dateToStamp(String dateTime1) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime1, dateTimeFormatter);
        Instant instant1 = localDateTime.toInstant(ZoneOffset.ofHours(8));
        long timeFromLocal1 = instant1.toEpochMilli();
        return timeFromLocal1 / 1000;
    }

    /**
     * 字符串时间转换为Unix时间戳(支持毫秒级)
     *
     * @param dateTime1 yyyy-MM-dd HH:mm:ss:SSS
     * @return
     */
    public static long dateToStamp2(String dateTime1) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(MILLI_FORMAT);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime1, dateTimeFormatter);
        Instant instant1 = localDateTime.toInstant(ZoneOffset.ofHours(8));
        return instant1.toEpochMilli();
    }

    /**
     * Unix时间戳转换成字符串时间(仅支持精确到秒的时间戳)
     *
     * @param sed long Unix时间戳
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String stampToDate(long sed) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(sed, 0, ZoneOffset.ofHours(8));
        String dateTimeStr = dateTimeFormatter.format(dateTime);
        return dateTimeStr;
    }

    /**
     * Unix时间戳转换成字符串时间(仅支持精确到毫秒的时间戳)
     *
     * @param sed long Unix时间戳
     * @return yyyy-MM-dd HH:mm:ss:SSS
     */
    public static String stampToDate2(long sed) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(MILLI_FORMAT);
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(sed), ZoneId.of("Asia/Shanghai"));
        return dateTimeFormatter.format(dateTime);
    }

    /**
     * 获取当前时间戳(精确到秒)
     * @return
     */
    public static long getSecondTimestamp(){
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        return Integer.parseInt(timestamp);
    }

    /**
     * 获取当前时间戳(精确到秒)
     * @return
     */
    public static long getSecondTimestamp2(){
        String timestamp = String.valueOf(System.currentTimeMillis());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.parseInt(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

    /**
     * 获取当前时间戳(精确到毫秒)
     * @return
     */
    public static long getTimestampTwo(){
        return System.currentTimeMillis();
    }



    public static void main(String[] args) {


    }

}
