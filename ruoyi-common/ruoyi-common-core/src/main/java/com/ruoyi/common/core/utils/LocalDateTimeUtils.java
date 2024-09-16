package com.ruoyi.common.core.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * @program: JieYiHua-Cloud
 * @description: 时间工具类
 * @author: LiYu
 * @create: 2021-08-04 15:45
 **/
public class LocalDateTimeUtils {
    /**
     * 获取指定日期所属周的周一的日期
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime getMondayForThisWeek(LocalDate localDate) {
        LocalDateTime monday = LocalDateTime.of(localDate, LocalTime.MIN).with(DayOfWeek.MONDAY);
        return monday;
    }

    /**
     * 获取指定日期所属周的周日的日期
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime getSundayForThisWeek(LocalDate localDate) {
        LocalDateTime sunday = LocalDateTime.of(localDate, LocalTime.MIN).with(DayOfWeek.SUNDAY);
        return sunday;
    }

    /**
     * 获取指定日期所属周的下周一的日期
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime getMondayForNextWeek(LocalDate localDate) {
        LocalDateTime monday = LocalDateTime.of(localDate, LocalTime.MIN).plusWeeks(1).with(DayOfWeek.MONDAY);
        return monday;
    }

    /**
     * 获取指定日期所属周的下周日的日期
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime getSundayForNextWeek(LocalDate localDate) {
        LocalDateTime sunday = LocalDateTime.of(localDate, LocalTime.MIN).plusWeeks(1).with(DayOfWeek.SUNDAY);
        return sunday;
    }

    /**
     * 指定格式为"yyyy-MM-dd HH:mm:ss"的字符串时间转化为LocalDateTime类型
     *
     * @param dateStr
     * @return
     */
    public static LocalDateTime getLocalDateTimeFromString(String dateStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return localDateTime;
    }

    /**
     * 指定格式为"yyyy-MM-ddTHH:mm:ss"的字符串时间转化为LocalDateTime类型
     *
     * @param dateStr
     * @return
     */
    public static LocalDateTime getLocalDateTimeFromString2(String dateStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd w hh:mm:ss"));
        return localDateTime;
    }

    /**
     * LocalDateTime类型转化为格式为"yyyy-MM-dd HH:mm:ss"的字符串时间类型
     *
     * @param localDateTime
     * @return
     */
    public static String getStringFromLocalDateTime(LocalDateTime localDateTime) {
        String localDateTimeStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return localDateTimeStr;
    }


    /**
     * LocalDateTime类型转化为格式为"yyyy-MM-dd"的字符串时间类型
     *
     * @param localDateTime 时间
     * @return 结果
     */
    public static String getStringFromLocalDateTime2(LocalDateTime localDateTime) {
        if (localDateTime == null) { return null; }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * LocalDateTime类型转化为格式为"yyyy-MM-dd HH"的字符串时间类型
     *
     * @param localDateTime 时间
     * @return 结果
     */
    public static String getStringFromLocalDateTime4(LocalDateTime localDateTime) {
        if (localDateTime == null) { return null; }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
    }

    /**
     * LocalDateTime类型转化为格式为"yyyy-MM"的字符串时间类型
     */
    public static String getStringFromLocalDateTime3(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    /**
     * Date类型时间转化为LocalDateTime类型
     *
     * @param date
     * @return
     */
    public static LocalDateTime getLocalDateTimeFromDate(Date date) {
        LocalDateTime localDateTime = date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        return localDateTime;
    }

    /**
     * LocalDateTime类型转化为Date类型时间
     *
     * @param localDateTime
     * @return
     */
    public static Date getDateFromLocalDateTime(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
        return date;
    }

    /**
     * 获取指定时间的00:00:00
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getLocalDateTimeForBegin(LocalDateTime localDateTime) {
        LocalDateTime begin = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
        return begin;
    }

    /**
     * 获取指定时间的23:59:59
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getLocalDateTimeForEnd(LocalDateTime localDateTime) {
        LocalDateTime end = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
        return end;
    }

    /**
     * 时间戳(毫秒)转化为LocalDateTime格式
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime getLocalDateTimeFromTimestamp(Long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
        return localDateTime;
    }

    /**
     * LocalDateTime格式转化为时间戳(毫秒)
     *
     * @param localDateTime
     * @return
     */
    public static Long getTimestampFromLocalDateTime(LocalDateTime localDateTime) {
        Long timestamp = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return timestamp;
    }

    /**
     * 时间戳(毫秒)转 yyyy-MM-dd HH:mm:ss
     * @param timestamp
     * @return
     */
    public static String getStringFromTimestamp(Long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(timestamp);
    }

    /**
     * 获取本月开始时间
     *
     * @return 开始时间
     */
    public static LocalDateTime getFirstDayOfMonth() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

    /**
     * 获取月初
     *
     * @param localDateTime 时间
     * @return 数据
     */
    public static LocalDateTime getTheBeginningOfTheMonth(LocalDateTime localDateTime) {
        return LocalDateTime.of(LocalDate.from(localDateTime.with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

    /**
     * 获取本月结束时间
     *
     * @return 结束时间
     */
    public static LocalDateTime getLastDay() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
    }

    public static LocalDateTime getTheEndOfTheMonth(LocalDateTime localDateTime) {
        return LocalDateTime.of(LocalDate.from(localDateTime.with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
    }

    /**
     * 获取时间差 （秒）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 秒
     */
    public static Long getTimeDifference(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toMinutes() * 60;
    }

    /**
     * 获取两个时间差 （天数）
     *
     * @param localDateTime 时间
     * @return 天数
     */
    public static Long timeDifferenceByDay(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(localDateTime, now);
        return duration.toDays();
    }

    /**
     * 获取两天时间间隔
     *
     * @param startingTime 开始时间
     * @param endTime      结束时间
     * @return 结果
     */
    public static Long twoDayInterval(LocalDateTime startingTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startingTime, endTime);
        return duration.toDays();
    }

    /**
     * 转白话
     *
     * @param localDateTime 时间
     * @return 字符串
     */
    public static String toTheVernacular(LocalDateTime localDateTime) {
        return localDateTime.getYear() + "年" + localDateTime.getMonthValue() + "月" + localDateTime.getDayOfMonth() + "日";
    }


    /**
     * 获取昨天
     *
     * @return 获取昨天
     */
    public static LocalDateTime getYesterdaySDate() {
        return LocalDateTime.now().plusDays(1);
    }
    /**
     * 获取昨天
     *
     * @return 获取昨天
     */
    public static LocalDateTime getMinusDays() {
        return LocalDateTime.now().minusDays(1);
    }
    /**
     * 获取明天
     *
     * @return 获取明天
     */
    public static LocalDateTime getPlusDays() {
        return LocalDateTime.now().plusDays(1);
    }

    /**
     * 获取今年开始时间
     *
     * @return 开始时间
     */
    public static LocalDateTime startThisYear() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear())), LocalTime.MIN);
    }


    /**
     * 根据日期获取 星期 （2019-05-06 ——> 星期一）
     *
     * @param datetime
     * @return
     */
    public static int dateToWeek(LocalDateTime datetime) {
        //获取当前时间
        LocalDateTime currentDate = LocalDateTime.now();
        //获取当前周
        int week = currentDate.getDayOfWeek().getValue();
        System.out.println("获取当前周:" + week);
        return week;
    }

    public static void main(String[] args) {
        System.out.println(twoDayInterval(LocalDateTime.now(),LocalDateTime.now()));
        System.out.println(getNowBeforeHourTime(-24L));
    }



    /**
     * 获取今天开始时间
     *
     * @return 结果
     */
    public static LocalDateTime getTodayStartTime() {
        return getLocalDateTimeForBegin(LocalDateTime.now());
    }

    /**
     * 获取今天结束时间
     *
     * @return 结果
     */
    public static LocalDateTime getTodayEndTime() {
        return getLocalDateTimeForEnd(LocalDateTime.now());
    }





    /**
     * 相隔所有时间
     *
     * @param startingTime 开始时间
     * @param endTime      结束时间
     * @return 结果
     */
    public static List<String> allTimeApart(String startingTime, String endTime) {
        Long size = LocalDateTimeUtils.twoDayInterval(LocalDateTimeUtils.getLocalDateTimeFromString(startingTime), LocalDateTimeUtils.getLocalDateTimeFromString(endTime));
        List<String> list = new ArrayList<>(Math.toIntExact(size));
        LocalDateTime time = getLocalDateTimeFromString(endTime);
        for (int i = 0; i <= size; i++) {
            LocalDateTime localDateTime = time.minusDays(i);
            list.add(getStringFromLocalDateTime2(localDateTime));
        }
        return list;
    }


    /**
     * 判断当前用户登录时段是否在09:00-18:00
     * @param
     * @return
     */
    public static boolean setFirstLogTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");//获取时，分
        //当前系统时间
        Date data = new Date();
        String dateString = formatter.format(data);
        String format = "HH:mm";
        try{
            Date nowTime = new SimpleDateFormat(format).parse(dateString);
            Date startTime = new SimpleDateFormat(format).parse("09:00");
            Date endTime = new SimpleDateFormat(format).parse("18:00");
            return isEffectiveDate(nowTime, startTime, endTime);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前日期是否大于某个日期
     * @param date yyyy-MM-dd
     * @return
     */
    public static boolean afterDate(LocalDateTime date){
        //针对好享管家图片相反
        String times = "2022-08-23";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //把String转为LocalDate
        LocalDate localTime=LocalDate.parse(times,dtf);
        //判断当前日期是否大于指定日期
        return date.toLocalDate().isAfter(localTime);
    }


    /**
     * 获取当前时间指定偏移多长小时前的时间
     * @param hour 负向前偏移 正向后偏移
     * @return
     */
    public static LocalDateTime getNowBeforeHourTime(Long hour){
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.systemDefault());
        return localDateTime.plusHours(hour);
    }

    public static Long timeDifferenceByLocalDate(LocalDateTime localDateTime) {
        LocalDate toLocalDate = localDateTime.toLocalDate();
        LocalDate now = LocalDate.now();
        long until = toLocalDate.until(now, ChronoUnit.DAYS);
        return toLocalDate.until(now, ChronoUnit.DAYS);
    }

    /**
     * 判断时间是否是今天
     * @param localDateTime 时间
     * @return 结果
     */
    public static boolean isToday(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().equals(LocalDate.now());
    }

    /**
     * 判断日期是否是指定天数内
     * @param localDateTime 时间
     * @param day 天数
     * @return 结果
     */
    public static boolean isDay(LocalDateTime localDateTime,Long day) {
        return localDateTime.toLocalDate().equals(LocalDate.now().plusDays(day));
    }

    public static long getTimeStamp(String dateTime){
        LocalDateTime time = LocalDateTimeUtils.getLocalDateTimeFromString(dateTime);
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
