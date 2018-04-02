package com.hyy.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author beam
 */
public class DateUtil {

    /**
     * 短日期格式
     */
    public final static String SHORTDATEFORMATER = "yyyy-MM-dd";
    /***
     * "HH:mm:ss";
     */
    public final static String SHORTTIMEFORMATER = "HH:mm:ss";
    /**
     * "yyyy-MM-dd HH:mm:ss"
     */
    public final static String LONGDATEFORMATER = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param datePattern 格式
     * @return 返回日期格式字符串（yyyy-mm-dd HH:mm:ss）
     */
    public static final String getDateByDatePattern(String datePattern) {
        return convertDateToString(datePattern, new Date());
    }

    /**
     * 把时间装成指定的字符串
     *
     * @param datePattern 转换格式
     * @param date        日期
     * @return 时间字符串
     */
    public static final String convertDateToString(String datePattern, Date date) {
        String returnValue = null;
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(datePattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 把时间装成指定的字符串 (yyyy-MM-dd HH:mm:ss)
     *
     * @param date 日期
     * @return 时间字符串
     */
    public static final String convertDateToString(Date date) {
        return convertDateToString(LONGDATEFORMATER, date);
    }

    /**
     * 把时间字符串转成指定格式的时间
     *
     * @param datePattern 转换格式
     * @param  dateStr 日期内容
     * @return 时间Date
     * @throws ParseException
     */
    public static final Date convertStringToDate(String datePattern,
                                                 String dateStr) throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(datePattern);
        try {
            date = df.parse(dateStr);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }
        return (date);
    }

    /**
     * 把时间字符串装成日期
     *
     * @param date 可传（yyyy-mm-dd HH:mm:ss）或者（yyyy-mm-dd）
     * @return
     */
    public static final Date getDateTime(String date) {
        try {
            return convertStringToDate(LONGDATEFORMATER, date);
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                return convertStringToDate(SHORTDATEFORMATER, date);
            } catch (ParseException e1) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 拼装日期返回完整格式的日期 (yyyy-MM-dd HH:mm:ss) 介绍：date传送一位数时（1）当做日期,年月用当期日期补充
     * 2011-02-01 date传送两位时（02-01）,年份用当期日期补充 2011-02-01 time传送一位时（9）当做小时，分秒用00补充
     * 09:00:00 time传送两位时（09:18）当做时分，秒用00补充 09:18:00
     *
     * @param date 任何形式的日期
     * @param date 任何形式的时间
     * @return Date 格式传输错误返回当期日期
     */
    public static final Date buildUpTime(String date, String time) {
        Date d = new Date();
        String nowDate = convertDateToString(SHORTDATEFORMATER, d);
        String buildTime = "";
        String[] dates = date.split("-");
        String[] nowDates = nowDate.split("-");
        if (dates.length == 1)
            buildTime += nowDates[0] + "-" + nowDates[1] + "-"
                    + ("".equals(dates[0]) ? nowDates[2] : dates[0]);
        if (dates.length == 2)
            buildTime += nowDates[0] + "-" + dates[0] + "-" + dates[1];
        if (dates.length == 3)
            buildTime += date;
        if (buildTime.length() == 8)
            buildTime = nowDates[0].substring(0, 1) + buildTime;

        buildTime += " ";
        if (StringUtils.areNotEmpty(time)) {
            String[] times = time.split(":");
            if (times.length == 1)
                buildTime += ("".equals(times[0]) ? "00" : times[0]) + ":00:00";
            if (times.length == 2)
                buildTime += times[0] + ":" + times[1] + ":00";
            if (times.length == 3)
                buildTime += time;
        } else {
            buildTime += "00:00:00";
        }

        return getDateTime(buildTime);
    }

    /**
     * 拼装日期返回完整格式的日期 (yyyy-MM-dd HH:mm:ss) 介绍：date传送一位数时（1）当做日��,年月用当期日期补充
     * 2011-02-01 date传送两位时（02-01）,年份用当期日期补充 2011-02-01 time传送一位时（9）当做小时，分秒用00补充
     * 09:00:00 time传送两位时（09:18）当做时分，秒用00补充 09:18:00
     *
     * @param date 任何形式的日期
     * @param date 任何形式的时间
     * @return Date
     */
    public static final String buildUpTimeStr(String date, String time) {
        Date d = new Date();
        String nowDate = convertDateToString(SHORTDATEFORMATER, d);
        String buildTime = "";
        String[] dates = date.split("-");
        String[] nowDates = nowDate.split("-");
        if (dates.length == 1)
            buildTime += nowDates[0] + "-" + nowDates[1] + "-"
                    + ("".equals(dates[0]) ? nowDates[2] : dates[0]);
        if (dates.length == 2)
            buildTime += nowDates[0] + "-" + dates[0] + "-" + dates[1];
        if (dates.length == 3)
            buildTime += date;
        if (buildTime.length() == 8)
            buildTime = nowDates[0].substring(0, 1) + buildTime;

        buildTime += " ";

        String[] times = time.split(":");
        if (times.length == 1)
            buildTime += ("".equals(times[0]) ? "00" : times[0]) + ":00:00";
        if (times.length == 2)
            buildTime += times[0] + ":" + times[1] + ":00";
        if (times.length == 3)
            buildTime += time;
        return buildTime;
    }

    /**
     * 获得两个日期之前相差的月份<br>
     *
     * @param start
     * @param end
     * @return
     */
    public static final int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }

    /**
     * 获取当前日期之前的某个日期
     *
     * @param field 年月日Calendar.YEAR等
     * @param value 加多久或减多久
     * @return
     */
    public static Date getBeforeDate(int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(field, value);
        return calendar.getTime();
    }

    /**
     * 根据时间获取int格式 返回值如：20120316
     *
     * @param date 时间
     * @return 返回值如：20120316
     */
    public static Integer getDateInt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String str = "";
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        if (month + 1 < 10) {
            str = year + "0" + (month + 1);
        } else {
            str = year + "" + month;
        }
        return Integer.parseInt(str);
    }

    /**
     * 获取今天的开始时间，如果今天是2012年5月11日，则获取到的结果是 2012-05-11 00:00:00
     *
     * @return 返回值如： 2012-05-11 00:00:00
     */
    public static String getTodayStart() {
        Date date = new Date();
        String dateString = convertDateToString(SHORTDATEFORMATER, date);
        dateString += " 00:00:00";
        return dateString;
    }

    /**
     * 获取今天的结束时间，如果今天是2012年5月11日，则获取到的结果是 2012-05-11 00:00:00
     *
     * @return 返回值如： 2012-05-11 00:00:00
     */
    public static String getTodayEnd() {
        Date date = new Date();
        String dateString = convertDateToString(SHORTDATEFORMATER, date);
        dateString += " 23:59:59";
        return dateString;
    }

    /**
     * 时间戳转换为日期  mysql时间戳是从秒开始，而java从毫秒开始
     * sDate时间戳,datePattern日期格式
     */
    public static String getDateFromTimeStamp(String sDate, String datePattern, String stampType) {
        if ("".equals(stampType)) {
            sDate = sDate + "000";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        String sd = sdf.format(new Date(Long.parseLong(sDate)));
        return sd;
    }
}
