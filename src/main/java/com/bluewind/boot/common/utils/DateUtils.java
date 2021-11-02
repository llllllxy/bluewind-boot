package com.bluewind.boot.common.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author liuxingyu01
 * @version 创建时间：2020-11-18 10:24:23
 * @description 时间工具类(普通)
 */
public class DateUtils {

    // 用来全局控制 上一周，本周，下一周的周数变化
    private static int weeks = 0;
    // 一年最大天数
    private static int MaxYear;


    /**
     * 获取当前日期 默认得时间格式yyyyMMdd
     *
     * @return yyyyMMdd
     */
    public static String getCurrentDate() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(dt);
    }

    /**
     * 获取当前日期 比如 DateTool.getCurrentDate("yyyy-MM-dd"); 返回值为 2012-05-15
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentDate(String format) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dt);
    }

    /**
     * 获取当前时间 默认得时间格式yyyyMMddHHmmss
     *
     * @return yyyyMMddHHmmss
     */
    public static String getCurrentTime() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(dt);
    }

    /**
     * 获取当前时间 比如 DateTool.getCurrentTime("yyyy-MM-dd HH:mm:ss"); 返回值为 2012-05-15 23:44:20
     *
     * @param format
     * @return String
     */
    public static String getCurrentTime(String format) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(dt);
        return date;
    }

    /**
     * 将一个日期时间字符串，由一个格式转换成另一个格式 比如:DateTool.timeFormat("20120506123344",
     * "yyyyMMddHHmmss", "yyyy年M月d日 HH:mm") 返回值为2012年5月6日 12:33
     *
     * @param time      时间字符串
     * @param oldFormat 旧格式
     * @param newFormat 新格式
     * @return
     */
    public static String timeFormat(String time, String oldFormat, String newFormat) {
        try {
            SimpleDateFormat oldSdf = new SimpleDateFormat(oldFormat);
            Date date = oldSdf.parse(time);
            SimpleDateFormat newSdf = new SimpleDateFormat(newFormat);
            return newSdf.format(date);
        } catch (Exception e) {
            return time;
        }
    }

    /**
     * 计量时间差 (time2 - time1)，返回秒数 比如 DateTool.timeDiff("2012-10-25 02:49:15",
     * "yyyyMMdd HH:mm:ss", "20121025025030", "yyyyMMddHHmmss") 返回值为 75
     *
     * @param time1   时间1
     * @param format1 格式1
     * @param time2   时间2
     * @param format2 格式2
     * @return
     */
    public static int timeDiff(String time1, String format1, String time2, String format2) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
            Date date1 = sdf1.parse(time1);

            SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
            Date date2 = sdf2.parse(time2);

            return Long.valueOf(date2.getTime() - date1.getTime()).intValue() / 1000;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 计量时间差 (time2 - time1)，返回秒数 比如 DateTool.timeDiff("20111025025030",
     * "20121025025030",) 返回值为 75
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return
     */
    public static int timeDiff(String time1, String time2) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date1 = sdf1.parse(time1);

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date2 = sdf2.parse(time2);

            return Long.valueOf(date2.getTime() - date1.getTime()).intValue() / 1000;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间间隔天数2008-12-1~2008-9-29
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return
     */
    public static String getDays(String date1, String date2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
        long day = 0;
        try {
            Date date = myFormatter.parse(date1);
            Date mydate = myFormatter.parse(date2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int dayDiff(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
            return Long.valueOf(date.getTime() - mydate.getTime()).intValue() / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate 日期
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 将短时间格式字符串转换为时间 yyyyMMdd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 计算当月最后一天,返回字符串
     */
    public static String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取自然月的最后一天日期 "YYYYMMDD"
     *
     * @param date1:"201201" 或者 "20120102"
     * @return
     */
    public static String getLastDayOfMonth(String date1) {
        try {
            if (date1.length() == 6) date1 = date1 + "01";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Date date = formatter.parse(date1);
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            int lastDay = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
            return date1.substring(0, 6) + lastDay;
        } catch (ParseException e) {
            throw new RuntimeException("传入日期出错." + e);
        }
    }

    /**
     * 获取自然月的第一天日期 "YYYYMMDD"
     *
     * @param date1:"201201" 或者 "20120102"
     * @return
     */
    public static String getFirstDayOfMonth(String date1) {
        if (date1 == null || "".equals(date1) || date1.length() < 6
                || date1.length() == 7 || date1.length() > 8) {
            throw new RuntimeException("传入输入日期有误！");
        }
        try {
            String month1 = date1.substring(0, 6);
            return month1 + "01";
        } catch (Exception e) {
            throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
        }
    }


    /**
     * 上月第一天
     *
     * @return
     */
    public static String getPreviousMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
        // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }


    /**
     * 获得本周星期日的日期
     *
     * @return
     */
    public static String getCurrentWeekday() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String preMonday = df.format(monday);
        return preMonday;
    }


    /**
     * 获得当前日期与本周日相差的天数
     *
     * @return
     */
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    /**
     * 获得本周一的日期
     *
     * @return
     */
    public static String getMondayOFWeek() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 获得相应周的周六的日期
     *
     * @return
     */
    public static String getSaturday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得上周星期日的日期
     *
     * @return
     */
    public static String getPreviousWeekSunday() {
        weeks = 0;
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得上周星期一的日期
     *
     * @return
     */
    public static String getPreviousWeekday() {
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得下周星期一的日期
     *
     * @return
     */
    public static String getNextMonday() {
        weeks++;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得下周星期日的日期
     *
     * @return
     */
    public static String getNextSunday() {

        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String preMonday = df.format(monday);
        return preMonday;
    }

    private static int getMonthPlus() {
        Calendar cd = Calendar.getInstance();
        int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
        cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        //一月最大天数
        int maxDate = cd.get(Calendar.DATE);
        if (monthOfNumber == 1) {
            return -maxDate;
        } else {
            return 1 - monthOfNumber;
        }
    }

    /**
     * 获得上月最后一天的日期
     *
     * @return
     */
    public static String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得下个月第一天的日期
     *
     * @return
     */
    public static String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得下个月最后一天的日期
     *
     * @return
     */
    public static String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得明年最后一天的日期
     *
     * @return
     */
    public static String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得明年第一天的日期
     *
     * @return
     */
    public static String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;

    }

    /**
     * 获得本年有多少天
     *
     * @return
     */
    private static int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    private static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    /**
     * 获得本年第一天的日期
     *
     * @return
     */
    public static String getCurrentYearFirst() {
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    /**
     * 获得本年最后一天的日期 *
     *
     * @return
     */
    public static String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        return years + "1231";
    }

    /**
     * 获得上年第一天的日期 *
     *
     * @return
     */
    public static String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "0101";
    }

    /**
     * 获得上年最后一天的日期
     *
     * @return
     */
    public static String getPreviousYearEnd() {
        weeks--;
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
                + (MaxYear - 1));
        Date yearDay = currentDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String preYearDay = df.format(yearDay);
        getThisSeasonTime(11);
        return preYearDay;
    }

    /**
     * 获得本季度
     *
     * @param month
     * @return
     */
    public static String getThisSeasonTime(int month) {
        int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_monthTemp = array[season - 1][0];
        int end_monthTemp = array[season - 1][2];
        String start_month = "";
        if (start_monthTemp < 10) {
            start_month = "0" + start_monthTemp;
        } else {
            start_month = "" + start_monthTemp;
        }
        String end_month = "";
        if (end_monthTemp < 10) {
            end_month = "0" + end_monthTemp;
        } else {
            end_month = "" + end_monthTemp;
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_daysTemp = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_monthTemp);
        String start_days = "";

        if (start_daysTemp < 10) {
            start_days = "0" + start_daysTemp;
        } else {
            start_days = "" + start_daysTemp;
        }
        String seasonDate = years_value + start_month + start_days
                + "," + years_value + end_month + end_days;
        return seasonDate;

    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year  年
     * @param month 月
     * @return 最后一天
     */
    private static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return true or false
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 获取输入日期的前 3天 (n为负整数)或后3天(n为正整数)。 getBeforeOrNextDay("20120720",1) 返回
     * 20120721 getBeforeOrNextDay("20120720",-1) 返回 20120719
     *
     * @param day
     * @param n
     * @return
     */
    public static String getBeforeOrNextDay(String day, int n) {
        if (day == null || "".equals(day) || day.length() != 8) {
            throw new RuntimeException("由于缺少必要的参数，获取输入日期的前 n天 或后n天出错：day=" + day + ";n=" + n);
        }
        try {
            String sYear = day.substring(0, 4);
            int year = Integer.parseInt(sYear);
            String sMonth = day.substring(4, 6);
            int month = Integer.parseInt(sMonth);
            String sDay = day.substring(6, 8);
            int dday = Integer.parseInt(sDay);
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, dday);
            cal.add(Calendar.DATE, n);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            return df.format(cal.getTime());
        } catch (Exception e) {
            throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
        }
    }

    /**
     * 获取昨天日期
     *
     * @return
     */
    public static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
        return yesterday;
    }


    /**
     * 获取明天日期
     *
     * @return
     */
    public static String getTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        String tomorrow = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
        return tomorrow;
    }

    /**
     * 获取上月
     *
     * @return
     */
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String lastMonth = new SimpleDateFormat("yyyyMM").format(cal.getTime());
        return lastMonth;
    }

    /**
     * 获取去年同月
     *
     * @return
     */
    public static String getYearSameMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        String yearSameMonth = new SimpleDateFormat("yyyyMM").format(cal.getTime());
        return yearSameMonth;
    }


    /**
     * 取工作时间
     *
     * @ param startTime 开始时间
     * @ param endTime 结束时间
     * @ param timeFormat 时间格式，例如：yyyy-MM-dd_HH:mm:ss
     * @ return String
     */
    public static String getWorkTime(String startTime, String endTime, String timeFormat) {

        String workTime = "";

        SimpleDateFormat dateFormatter = new SimpleDateFormat(timeFormat);
        Date startTimeDate;
        Date endTimeDate;
        try {
            startTimeDate = dateFormatter.parse(startTime);
            endTimeDate = dateFormatter.parse(endTime);

            Calendar c_start_time = Calendar.getInstance();
            c_start_time.setTime(startTimeDate);
            Calendar c_end_time = Calendar.getInstance();
            c_end_time.setTime(endTimeDate);

            long time1 = c_start_time.getTimeInMillis();
            long time2 = c_end_time.getTimeInMillis();
            long diff = time2 - time1;

            if (diff > 0) {
                long hour = diff / (60 * 60 * 1000);
                long minute = (diff - hour * 60 * 60 * 1000) / (60 * 1000);
                long second = (diff - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
                if (second >= 60) {
                    second = second % 60;
                    minute += second / 60;
                }
                if (minute >= 60) {
                    minute = minute % 60;
                    hour += minute / 60;
                }
                String sh = String.valueOf(hour);
                String sm = String.valueOf(minute);
                String ss = String.valueOf(second);
                if (sh != null && !sh.equals("")) {
                    if (sh.length() == 1) {
                        sh = "0" + sh;
                    }
                }
                if (sm != null && !sm.equals("")) {
                    if (sm.length() == 1) {
                        sm = "0" + sm;
                    }
                }
                if (ss != null && !ss.equals("")) {
                    if (ss.length() == 1) {
                        ss = "0" + ss;
                    }
                }
                //workTime = sh+":"+sm+":"+ss;
                if (sh != null && !sh.equals("") && !sh.equals("00")) {
                    workTime = sh + "小时" + sm + "分" + ss + "秒";
                } else if (sm != null && !sm.equals("") && !sm.equals("00")) {
                    workTime = sm + "分" + ss + "秒";
                } else {
                    workTime = ss + "秒";
                }
            } else {
                workTime = "0秒";
            }
        } catch (Exception e) {
        }
        return workTime;
    }

    /**
     * 字符串时间转换为Unix时间戳
     *
     * @param dateTime1 yyyyMMddHHMMSS
     * @return
     */
    public static long dateToStamp(String dateTime1) {
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date dateTime11 = dateTimeFormatter.parse(dateTime1);
            long mil = dateTime11.getTime() / 1000;
            return mil;
        } catch (Exception e) {
            throw new RuntimeException("日期格式错误！");
        }
    }

    /**
     * Unix时间戳转换成字符串时间
     *
     * @param sed long
     * @return yyyyMMddHHMMSS
     */
    public static String stampToDate(long sed) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        long lt = sed * 1000;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取若干秒后的时间 time格式yyyyMMddHHmmss
     *
     * @param later
     * @return
     */
    public static String getLaterTime(String time, int later) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date date = sdf.parse(time);
            Calendar Cal = Calendar.getInstance();
            Cal.setTime(date);
            Cal.add(Calendar.SECOND, later);
            return sdf.format(Cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取若干秒后的时间
     *
     * @param later
     * @return
     */
    public static String getLaterTime(int later) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.SECOND, later);
            return sdf.format(nowTime.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
