package com.micro.cloud.util.file;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期格式化
 *
 * @author 尹海业
 */
public final class DateUtils {

    /**
     * 记录日志对象
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private static final String DATE = "yyyy-MM-dd";

    private static final String MONTH = "yyyy年MM月";


    /**
     * 年月日时分秒(无下划线) yyyyMMddHHmmss
     */
    public static final String dtLong = "yyyyMMddHHmmss";

    /**
     * 年月日时分秒(无下划线) yyyyMMddHHmmssSSS
     */
    public static final String dtLongs = "yyyyMMddHHmmssSSS";

    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String simple = "yyyy-MM-dd HH:mm:ss";
    public static final String shortSimple = "yyyy-MM-dd";

    /**
     * 年月日(无下划线) yyyyMMdd
     */
    public static final String dtShort = "yyyyMMdd";

    /**
     * 年月日 yyyy年MM月dd日
     */
    public static final String dtChinaShort = "yyyy年MM月dd日";


    /**
     * 私有化构造方法，防止外部调用
     */
    private DateUtils() {

    }


    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     *
     * @return 以yyyyMMddHHmmss为格式的当前系统时间
     */
    public static String getyyyyMMddHHmmss() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dtLong);
        return df.format(date);
    }

    /**
     * 返回当前入参时间以yyyyMMddHHmmss为格式的字符串
     *
     * @return 以yyyyMMddHHmmss为格式的入参时间
     */
    public static String getyyyyMMddHHmmss(Date date) {
        DateFormat df = new SimpleDateFormat(dtLong);
        return df.format(date);
    }

    public static String getyyyyMMddHHmmssSSS() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dtLongs);
        return df.format(date);
    }

    /**
     * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowDate() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(simple);
        return df.format(date);
    }

    /**
     * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
     *
     * @return
     */
    public static String getDate() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dtShort);
        return df.format(date);
    }


    /**
     * 返回String字符串 格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date 要格式化的日期
     * @return String 格式化后的日期
     */
    public static String dataFormat(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return time.format(date);
    }

    public static Date getDateMm(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            LOGGER.error("", e);
        }
        return new Date();
    }


    /**
     * 返回String字符串 格式：yyyy-MM-dd
     *
     * @param date 要格式化的日期
     * @return String 格式化后的日期
     */
    public static String todayFormat(Date date) {
        SimpleDateFormat time = new SimpleDateFormat(DATE);
        return time.format(date);
    }

    /**
     * 返回String字符串 格式：yyyy-MM-dd
     *
     * @param date 要格式化的日期
     * @return String 格式化后的日期
     */
    public static String todayFormatMonth(Date date) {
        SimpleDateFormat time = new SimpleDateFormat(MONTH);
        return time.format(date);
    }

    /**
     * 返回String字符串 格式：yyyy年MM月dd日
     *
     * @param date 要格式化的日期
     * @return String 格式化后的日期
     */
    public static String chinaYearMonthDay(Date date) {
        SimpleDateFormat time = new SimpleDateFormat(dtChinaShort);
        return time.format(date);
    }


    /**
     * 返回String字符串 格式：yyyy-MM-dd
     *
     * @param date 要格式化的日期
     * @return String 格式化后的日期
     */
    public static String todayFormatString(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
        return time.format(date);
    }

    /**
     * 返回String字符串 格式：yyyy-MM-dd
     *
     * @param date 要格式化的日期
     * @return String 格式化后的日期
     */
    public static int todayFormatInt(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(time.format(date));
    }

    /**
     * 返回String字符串 格式：yyyy-MM-dd
     *
     * @param date 要格式化的日期
     * @return String 格式化后的日期
     */
    public static String yesterdayFormat(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    public static String yesterdayTwentyFourFormat(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 23:59:59";
    }

    /**
     * 获取系统当期时间的前一天，格式：yyyy-MM-dd
     *
     * @return
     */
    public static String getYesterDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return yesterday;
    }

    /**
     * 获取系统当期时间的前14天，格式：yyyy-MM-dd
     *
     * @return
     */
    public static String getLastTowWeeksDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -14);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return yesterday;
    }

    /**
     * 当前时间的字符串
     *
     * @param date 时间
     * @return 时间的数字字符串格式
     */
    public static String mathString(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.UK);
        return time.format(date);
    }

    /**
     * 当前时间的字符串
     *
     * @param date 时间
     * @return 时间的数字字符串格式
     */
    public static String formatYMD(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        return time.format(date);
    }


    public static String toNanosecond(Date date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.CHINA);
        return time.format(date);
    }

    /**
     * String转Date
     *
     * @param dateString 要转化的date 字符串
     * @return 转换后的日期
     */
    public static Date stringToDate(String dateString) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = null;
        try {
            time = formatDate.parse(dateString);
        } catch (ParseException e) {
            LOGGER.error("String转Date失败！", e);
        }

        return time;
    }

    /**
     * String转Date
     *
     * @param dateString 要转化的date 字符串
     * @return 转换后的日期
     */
    public static Date stringToDateYMD(String dateString) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        Date time = null;
        try {
            time = formatDate.parse(dateString);
        } catch (ParseException e) {
            LOGGER.error("String转Date失败！", e);
        }

        return time;
    }

    /**
     * String转Date
     *
     * @param dateString 要转化的date 字符串
     * @return
     */
    public static Date stringToDateMM(String dateString) {
        SimpleDateFormat formatDate = new SimpleDateFormat(DATE);
        Date time = null;
        try {
            time = formatDate.parse(dateString);
        } catch (ParseException e) {
            LOGGER.error("String转Date失败！", e);
        }

        return time;
    }

    /**
     * 当前时间加几天
     *
     * @param number 当亲日期后的第number天
     * @return String 当亲日期后的第number天的额日期
     */
    public static String nextNumberDate(int number, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date dd = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dd);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        return format.format(calendar.getTime());
    }

    /**
     * 当前时间加几天
     *
     * @param number 当亲日期后的第number天
     * @return String 当亲日期后的第number天的额日期
     */
    public static String nextNumberDate(int number) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date dd = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dd);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        return format.format(calendar.getTime());
    }

    /**
     * 日期加一天
     *
     * @param s 要增加的日期
     * @param n 要增加的天数
     * @return String 增加n填后的日期
     */
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE);

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);// 增加一天
            // cd.add(Calendar.MONTH, n);//增加一个月

            return sdf.format(cd.getTime());

        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }

    /**
     * 日期加一天
     *
     * @param d 要增加的天数
     * @return String 增加n填后的日期
     */
    public static Date addOneDay(Date d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE);

            Calendar cd = Calendar.getInstance();
            cd.setTime(d);
            cd.add(Calendar.DATE, 1);// 增加一天

            return cd.getTime();

        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }

    /**
     * 日期加N天
     *
     * @param d 要增加的天数
     * @return String 增加n填后的日期
     */
    public static Date addNumDay(Date d, int n) {
        try {

            Calendar cd = Calendar.getInstance();
            cd.setTime(d);
            cd.add(Calendar.DATE, n);// 增加一天

            return cd.getTime();

        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }


    /**
     * 日期家一个月
     *
     * @param d 要增加的天数
     * @return String 增加n填后的日期
     */
    public static Date addOneMonth(Date d) {
        try {

            Calendar cd = Calendar.getInstance();
            cd.setTime(d);
            cd.add(Calendar.MONTH, 1);//增加一个月
            return cd.getTime();

        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }


    /**
     * 日期加N个月
     *
     * @param d 要增加的天数
     * @return String 增加n填后的日期
     */
    public static Date addNumMonth(Date d, int n) {
        try {

            Calendar cd = Calendar.getInstance();
            cd.setTime(d);
            cd.add(Calendar.MONTH, n);//增加一个月
            return cd.getTime();

        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }

    /**
     * 日期加N个年
     *
     * @param d 要增加的天数
     * @return String 增加n填后的日期
     */
    public static Date addNumYEAR(Date d, int n) {
        try {

            Calendar cd = Calendar.getInstance();
            cd.setTime(d);
            cd.add(Calendar.YEAR, n);//增加一个月
            return cd.getTime();

        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }

    /**
     * 比较两个日期 大小
     *
     * @param nowDate 日期1
     * @param endDate 日期2
     * @return nowDate<endDate                               返回true               ,               否则返回false
                    */
    public static boolean compare_date(String nowDate, String endDate) {

        DateFormat df = new SimpleDateFormat(DATE);
        try {
            Date dt1 = df.parse(nowDate);
            Date dt2 = df.parse(endDate);
            if (dt1.getTime() <= dt2.getTime()) {
                return true;
            } else if (dt1.getTime() > dt2.getTime()) {
                return false;
            }
        } catch (Exception exception) {
            LOGGER.error("日期对比失败！", exception);
        }
        return false;
    }

    /**
     * 比较两个日期大小
     *
     * @param nowDate 日期1
     * @param endDate 日期2
     * @return nowDate > endDate 返回true,否则返回false
                    */
    public static boolean compare_date_pv(Date nowDate, Date endDate) {
        return nowDate.getTime() > endDate.getTime();
    }

    /**
     * 计算当前时间和参数(过去时间)之间间隔多少秒
     *
     * @param startDate 过去时间
     * @return 间隔多少秒
     */
    public static int intervalSecondToNow(Date startDate) {
        long nowTime = new Date().getTime();
        long startTime = startDate.getTime();
        return (int) (nowTime - startTime) / 1000;
    }

    // 获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    // 获取昨天的开始时间
    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    // 获取昨天的结束时间
    public static Date getEndDayOfYesterDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    // 获取明天的开始时间
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    // 获取明天的结束时间
    public static Date getEndDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    // 获取本周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    // 获取本周的结束时间
    public static Date getEndDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    // 获取本周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfWeekNew(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }


    // 获取上周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }

    // 获取上周的结束时间
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    // 获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    // 获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    // 获取上月的开始时间
    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }

    // 获取上月的结束时间
    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }

    // 获取本年的开始时间
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        return getDayStartTime(cal.getTime());
    }

    // 获取本年的结束时间
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    // 获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    // 获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }


    // 两个日期相减得到的毫秒数
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }

    // 获取两个日期中的最大日期
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    // 获取两个日期中的最小日期
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    // 返回某月该季度的第一个月
    public static Date getFirstSeasonDate(Date date) {
        final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }

    // 返回某个日期下几天的日期
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    // 返回某个日期前几天的日期
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }

    // 获取某年某月到某年某月按天的切片日期集合(间隔天数的集合)
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List getTimeList(int beginYear, int beginMonth, int endYear,
                                   int endMonth, int k) {
        List list = new ArrayList();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));
            }
        } else {
            {
                for (int j = beginMonth; j < 12; j++) {
                    list.add(getTimeList(beginYear, j, k));
                }
                for (int i = beginYear + 1; i < endYear; i++) {
                    for (int j = 0; j < 12; j++) {
                        list.add(getTimeList(i, j, k));
                    }
                }
                for (int j = 0; j <= endMonth; j++) {
                    list.add(getTimeList(endYear, j, k));
                }
            }
        }
        return list;
    }

    // 获取某年某月按天切片日期集合(某个月间隔多少天的日期集合)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List getTimeList(int beginYear, int beginMonth, int k) {
        List list = new ArrayList();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException 异常
     */
    public static int daysBetween(Date smdate, Date bdate) {
        long between_days = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (Exception e) {
            LOGGER.error("", e);
            return -1;
        }
        return Integer.parseInt(String.valueOf(between_days));
    }




    /**
     * 比较当前时间小于 返回true
     *
     * @param nowDate
     * @param endDate
     * @return
     */
    public static boolean compare_date_lt(String nowDate, String endDate) {

        DateFormat df = new SimpleDateFormat(DATE);

        try {
            Date dt1 = df.parse(nowDate);
            Date dt2 = df.parse(endDate);
            if (dt1.getTime() < dt2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            LOGGER.error("日期对比失败！", exception);
        }
        return false;
    }

    /**
     * 当前时间大于草稿投保单创建时间天的最后一秒（草稿投保单生效时间过期了） 返回true
     *
     * @param date
     * @return
     */
    public static boolean compare_date_bn(Date date) {
        return new Date().getTime() > getDayEndTime(date).getTime();
    }

    /**
     * 时间date1和date2的时间差-单位月
     *
     * @param date1
     * @param date2
     * @return 月
     */
    public static int subtractMonthForStr(String date1, String date2) {
        int result;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
            int year1 = c1.get(Calendar.YEAR);
            int month1 = c1.get(Calendar.MONTH);
            int year2 = c2.get(Calendar.YEAR);
            int month2 = c2.get(Calendar.MONTH);
            int day1 = c1.get(Calendar.DATE);
            if (year1 == year2) {
                result = month2 - month1;
            } else {
                result = 12 * (year2 - year1) + month2 - month1;
            }
            // 如果开始日期选择月初月份需要加1
            if (day1 == 1) {
                result += 1;
            }
        } catch (ParseException e) {
            LOGGER.error("", e);
            result = -1;
        }
        return result;
    }


    /**
     * 时间date1和date2的时间差-单位月
     *
     * @param date1
     * @param date2
     * @return 月
     */
    public static int subtractMonthForDate(Date date1, Date date2) {
        int result;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DATE);
        if (year1 == year2) {
            result = month2 - month1;
        } else {
            result = 12 * (year2 - year1) + month2 - month1;
        }
        // 如果开始日期选择月初月份需要加1
        if (day1 == 1) {
            result += 1;
        }
        return result;
    }


    // 获取去年
    public static Date getReprotDate(Date date, int y, int m, int d) {


        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.YEAR, y);
        cd.add(Calendar.MONTH, m);
        cd.add(Calendar.DATE, d);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        return cd.getTime();
    }


    // 获取本周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfWeek(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 当前时间的字符串
     *
     * @param date 时间
     * @return 时间的数字字符串格式
     */
    public static String formatYMD(Date date, String format) {
        SimpleDateFormat time = new SimpleDateFormat(format, Locale.UK);
        return time.format(date);
    }

    public static String getOneHourLater(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        Date afterDate = new Date(now.getTime() + 3600000);
        return sdf.format(afterDate);
    }


    public static Date getEndTimeForMonthsProduct(Date beginDate, Integer periodMonth) {
        Integer year;
        Integer month;
        Integer februaryDays;
        Date returnDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        //获取当前时间的日
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(beginDate));
        Integer nextMonthDay = day - 1;

        if (nextMonthDay == 0) {
            //获取当月的最后一天
            Integer addMonth = periodMonth - 1;
            calendar.add(Calendar.MONTH, addMonth);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else {
            calendar.add(Calendar.MONTH, periodMonth);
            //看获取的月份是否是二月
            year = calendar.get(Calendar.YEAR);
            //日期从0开始排序
            month = calendar.get(Calendar.MONTH) + 1;
            //如果是二月，则判断是否是闰年还是平年
            if (month == 2) {
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                    februaryDays = 29;
                } else februaryDays = 28;

                if (nextMonthDay > februaryDays) {
                    nextMonthDay = februaryDays;
                }
            }
            calendar.set(Calendar.DATE, nextMonthDay);

        }
        //加一天减去一秒
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        returnDate = calendar.getTime();
        LOGGER.info("获取月周期产品的止期是：{}", format.format(returnDate));
        return returnDate;
    }


    /**
     * 判断两个时间的 月数 超过1个月 算两个月
     * @param d1 开始
     * @param d2 结束
     * @return Integer
     */
    public static int monthsBetween(Date d1, Date d2) {

        Calendar c1 = Calendar.getInstance();

        Calendar c2 = Calendar.getInstance();

        c1.setTime(d1);

        c2.setTime(d2);

        int year1 = c1.get(Calendar.YEAR);

        int year2 = c2.get(Calendar.YEAR);

        int month1 = c1.get(Calendar.MONTH);

        int month2 = c2.get(Calendar.MONTH);

        int day1 = c1.get(Calendar.DAY_OF_MONTH);

        int day2 = c2.get(Calendar.DAY_OF_MONTH);

        // 获取年的差值

        int yearInterval = year1 - year2;

        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数

        if (month1 < month2 || month1 == month2 && day1 < day2)

            yearInterval--;

        // 获取月数差值

        int monthInterval = (month1 + 12) - month2;

        if (day1 < day2) {

            monthInterval--;

        }

        monthInterval %= 12;

        int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);

        if(day1==day2){
            monthsDiff++;
        }
        return monthsDiff;

    }



    /**
     * 判断两个时间的 月数 超过1个月 算一个月 向下取整
     * @param d1 开始
     * @param d2 结束
     * @return Integer
     */
    public static int monthsBetweenDown(Date d1, Date d2) {
        d2 = addNumDay(d2,1);
        DateTimeFormatter formatter = DateTimeFormat.forPattern(simple);
        DateTime start = formatter.parseDateTime(formatYMD(d1)+" 00:00:00");
        DateTime end = formatter.parseDateTime(formatYMD(d2)+" 00:00:00");
        return Months.monthsBetween(start, end).getMonths();
    }




    public static void main(String[] args) {
        Date a = DateUtils.stringToDate("2020-10-20 00:00:00");
        Date b = DateUtils.stringToDate("2020-11-18 23:59:59");
        System.out.println(DateUtils.daysBetween(a, b));
    }
}
