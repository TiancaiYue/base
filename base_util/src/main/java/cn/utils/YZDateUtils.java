package cn.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.BaseSetting;
import cn.base.base_util.R;

/**
 * 时间换算
 * Created by base on 2020-02-26.
 */
public class YZDateUtils {
    public static final long MinuteInterval = 60 * 1000;
    public static final long HourInterval = 60 * MinuteInterval;
    public static final long DayInterval = 24 * HourInterval;

    @SuppressLint("SimpleDateFormat")
    public static String[] getThisMonthStartAndEndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        int startYear = calendar.get(Calendar.YEAR);
        int startMonth = calendar.get(Calendar.MONTH) - 1;
        if (startMonth < 1) {
            startYear = startYear - 1;
            startMonth = 12;
        }
        int endMonth = calendar.get(Calendar.MONTH) + 1;
        int endYear = calendar.get(Calendar.YEAR);
        if (endMonth > 12) {
            endYear = endYear + 1;
            endMonth = 1;
        }
        String[] startAndEndDate = new String[2];
        calendar.set(startYear, startMonth, 1, 0, 0, 0);
        startAndEndDate[0] = formatter.format(calendar.getTime());
        calendar.set(endYear, endMonth, calendar.get(Calendar.DATE), 23, 59, 59);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        startAndEndDate[1] = formatter.format(calendar.getTime());
        Log.v("monthStartAndEndTime", startAndEndDate[0] + "," + startAndEndDate[1]);
        return startAndEndDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String[] getThisMonthStartAndEndTime(int year, int month) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        int startYear = year;
        int startMonth = month - 1;
        if (startMonth < 1) {
            startYear = startYear - 1;
            startMonth = 12;
        }
        int endMonth = month + 1;
        int endYear = year;
        if (endMonth > 12) {
            endYear = endYear + 1;
            endMonth = 1;
        }
        String[] startAndEndDate = new String[2];
        calendar.set(startYear, startMonth - 1, 1, 0, 0, 0);
        startAndEndDate[0] = formatter.format(calendar.getTime());
        calendar.set(endYear, endMonth - 1, calendar.get(Calendar.DATE), 23, 59, 59);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        startAndEndDate[1] = formatter.format(calendar.getTime());
        Log.v("monthStartAndEndTime", startAndEndDate[0] + "," + startAndEndDate[1]);
        return startAndEndDate;
    }

    /**
     * 获取当前时间,返回date类型
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getCurrentSystemTime(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        String steDate = format.format(date);
        Date currentTime = null;
        try {
            currentTime = format.parse(steDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }

    /**
     * 获取当前时间，返回String类型
     */

    @SuppressLint("SimpleDateFormat")
    public static String getStrCurrentSystemTime(String dataType) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(dataType);
        String steDate = format.format(date);
        return steDate;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static long getLongCurrentSystemTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取延时时间
     *
     * @param time
     * @return
     */
    public static long getDelayMillis(long time) {
        return System.currentTimeMillis() - time < 1000 ? 1000 : 0;
    }

    /**
     * 获取日期的毫秒数，传入2017-05-26 11:36:00类型（字符串日期型），返货毫秒数getTime;
     */
    @SuppressLint("SimpleDateFormat")
    public static long getTimeByDate(String strDate, String dataType) {
        SimpleDateFormat format1 = new SimpleDateFormat(dataType);
        Date date = null;
        try {
            date = format1.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 根据毫秒，返回 分和秒 48:56
     */
    public static String getMMSSByMS(Long ms) {
        long mmm = ms / 1000; // 总秒数
        long mm = mmm / 60; // 分钟
        long ss = mmm % 60; // 秒

        String strMM = mm < 10 ? "0" + mm : mm + "";
        String strSS = ss < 10 ? "0" + ss : ss + "";

        return strMM + ":" + strSS;
    }

    /**
     * 给时间加上几个小时
     *
     * @param day  当前时间 格式：yyyy-MM-dd HH:mm:ss
     * @param hour 需要加的时间
     * @return
     */
    public static String addDateMinut(String day, int hour) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        System.out.println("front:" + format.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
        System.out.println("after:" + format.format(date));  //显示更新后的日期
        cal = null;
        return format.format(date);

    }

    /**
     * @param lo 毫秒数
     * @return String yyyy-MM-dd HH:mm:ss
     * @Description: long类型转换成日期
     */
    public static Date longToDate(long lo) {
        Date date = new Date(lo);
        return date;
    }

    /**
     * date转Calendar
     *
     * @param date
     * @return
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 计算两个日期之间差值，传入传入2017-05-26 11:36:00类型（字符串日期型），返回差值 分秒
     */
    public static String getMMSSByDateDifferenceValues(String startDate, String endDate, String dataType) {
        if (startDate == null || startDate.equals("") || endDate == null || endDate.equals("")) {
            return "";
        }
        long startDateMS = 0;
        long endDateMS = 0;
        long differenceValues = 0;

        startDateMS = getTimeByDate(startDate, dataType);
        endDateMS = getTimeByDate(endDate, dataType);
        differenceValues = Math.abs(endDateMS - startDateMS);
        return getMMSSByMS(differenceValues);
    }

    /**
     * 计算两个日期之间的差值，传入传入2017-05-26 11:36:00类型（字符串日期型），返回（long）毫秒值
     */

    public static long getTimeByDateDifferenceValues(String startDate, String endDate, String dataType) {
        if (startDate == null || startDate.equals("") || endDate == null || endDate.equals("")) {
            return 0;
        }
        long startDateMS = 0;
        long endDateMS = 0;
        long differenceValues = 0;

        startDateMS = getTimeByDate(startDate, dataType);
        endDateMS = getTimeByDate(endDate, dataType);
        differenceValues = endDateMS - startDateMS;
        // differenceValues = Math.abs(endDateMS - startDateMS);
        Log.v("时间差", differenceValues + "");
        return differenceValues;
    }

    /**
     * 计算两个日期之间的差值，传入传入2017-05-26 11:36:00类型（字符串日期型），时间差。
     */

    public static int getDistanceTimeByDateDifferenceValues(String startDate, String endDate, String dataType) {
        if (startDate == null || startDate.equals("") || endDate == null || endDate.equals("")) {
            return 0;
        }
        long startDateMS = 0;
        long endDateMS = 0;
        long differenceValues = 0;

        startDateMS = getTimeByDate(startDate, dataType);
        endDateMS = getTimeByDate(endDate, dataType);
        if (startDateMS - endDateMS < 0) {
            differenceValues = -Math.abs(endDateMS - startDateMS);
        } else {
            differenceValues = Math.abs(endDateMS - startDateMS);
        }
        return getDistanceDay(differenceValues);
    }

    /**
     * 根据差值返回天数
     */
    public static int getDistanceDay(long diff) {
        long day = 0;
        day = diff / (24 * 60 * 60 * 1000);
        return Integer.parseInt(day + "");
    }

    /**
     * 根据差值返回多长之间前或多长时间后
     */
    public static String getDistanceTime(long diff) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) {
            return day + "天前";
        }
        if (hour != 0) return hour + "小时前";
        if (min != 0) return min + "分钟前";
        return "刚刚";
    }

    /**
     * 根据差值返回多长算年月日天小时
     */
    public static String getDistanceTimeName(Date dateStart, Date dateEnd) {
        if (dateStart == null || dateEnd == null) {
            return "0分钟";
        }
        long diff = (dateEnd.getTime() / (1000 * 60) - dateStart.getTime() / (1000 * 60)) * 60 * 1000;
        if (diff < 0) {
            return "0分钟";
        }
        long year = 0;
        long day = 0;
        long hour = 0;
        long min = 0;

        year = diff / (365L * 24L * 60L * 60L * 1000L);
        day = diff / (24L * 60L * 60L * 1000L) - year * 365L;
        hour = diff / (60L * 60L * 1000L) - year * 365L * 24L - day * 24L;
        min = (diff / (60L * 1000L)) - year * 365L * 24L * 60L - day * 24L * 60L - hour * 60L;

        String time = "";
        if (year != 0) {
            time = year + "年";
           /* if (day != 0) {
                time += day + "天";
            }*/
            return time;
        }
        if (day != 0) {
            time = day + "天";
            /*if (hour != 0) {
                time += hour + "小时";
            }*/
            return time;
        }
        if (hour != 0) {
            time = hour + "小时";
            /*if (min != 0) {
                time += min + "分钟";
            }*/
            return time;
        }
        if (min != 0) {
            time = min + "分钟";
            return time;
        }
        return "0分钟";
    }

    /**
     * 根据差值返回多长算年月日天小时
     */
    public static String getDistanceTimeName(long dateStart, long dateEnd) {
        long diff = dateEnd - dateStart;
        if (diff < 0) {
            return "0分钟";
        }
        long year = 0;
        long day = 0;
        long hour = 0;
        long min = 0;

        year = diff / (365L * 24L * 60L * 60L * 1000L);
        day = diff / (24L * 60L * 60L * 1000L) - year * 365L;
        hour = diff / (60L * 60L * 1000L) - year * 365L * 24L - day * 24L;
        min = (diff / (60L * 1000L)) - year * 365L * 24L * 60L - day * 24L * 60L - hour * 60L;

        String time = "";
        if (year != 0) {
            time = year + "年";
            if (day != 0) {
                time += day + "天";
            }
            return time;
        }
        if (day != 0) {
            time = day + "天";
            if (hour != 0) {
                time += hour + "小时";
            }
            return time;
        }
        if (hour != 0) {
            time = hour + "小时";
            if (min != 0) {
                time += min + "分钟";
            }
            return time;
        }
        if (min != 0) {
            time = min + "分钟";
            return time;
        }
        return "0分钟";
    }

    public static String dateToString(Date date) {
        long timeSinceNow = new Date().getTime() - date.getTime();
        if (timeSinceNow < 10 * 1000) {
            return BaseSetting.getInstance().getContext().getString(R.string.A0749);
        } else if (timeSinceNow < MinuteInterval) {
            return BaseSetting.getInstance().getContext().getString(R.string.A0750);
        } else if (timeSinceNow < HourInterval) {
            long minutes = timeSinceNow / MinuteInterval;
            return String.format(BaseSetting.getInstance().getContext().getString(R.string.A0751), minutes);
        } else if (timeSinceNow < DayInterval) {
            long hours = timeSinceNow / HourInterval;
            return String.format(BaseSetting.getInstance().getContext().getString(R.string.A0752), hours);
        } else {
            DateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.US);
            String thisYear = yearFormat.format(new Date());
            String dateYear = yearFormat.format(date);
            DateFormat dateFormat;
            if (thisYear.equals(dateYear)) {
                //今年
                dateFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.US);
            } else {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            }
            return dateFormat.format(date);
        }
    }

    /**
     * long转string
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec * 1000);
        return sdf.format(date);
    }

    /**
     * 时间格式转换 Data转换成string
     */
    public static String dateToString(String dateFormat, Date date) {//可根据需要自行截取数据显示
        if (YZStringUtil.isEmpty(dateFormat)) {
            return getStrCurrentSystemTime(dateFormat);
        }
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    /**
     * 传入String格式化
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringToFormatDate(String dateFormat, String strDate) {
        // 先转换成Date类型
        if (strDate == null || strDate.equals("")) {
            return "";
        }
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format1.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat format2 = new SimpleDateFormat(dateFormat);
        return format2.format(date);
    }

    /**
     * 传入String格式化
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringToFormatDate1(String dateFormat, String strDate) {
        // 先转换成Date类型
        if (strDate == null || strDate.equals("")) {
            return "";
        }
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = format1.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format2 = new SimpleDateFormat(dateFormat);
        return format2.format(date);
    }

    /**
     * long转换成时分秒
     */
    public static String secToTime(long time) {
        String timeStr;
        long _hour = 1000 * 60 * 60;
        long _minute = 1000 * 60;
        long _second = 1000;

        int hour = (int) (time / _hour);
        int minute = (int) (time % _hour / _minute);
        int second = (int) (time % _hour % _minute / _second);

        String hourString = String.format("%02d", hour);
        String minuteString = String.format("%02d", minute);
        String secondString = String.format("%02d", second);
        timeStr = hourString + "：" + minuteString + "：" + secondString;
        return timeStr;
    }

    /**
     * 传入String格式化
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringToFormatDate2(String dateFormat, String strDate) {
        // 先转换成Date类型
        if (strDate == null || strDate.equals("")) {
            return "";
        }
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = format1.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format2 = new SimpleDateFormat(dateFormat);
        return format2.format(date);
    }

    /**
     * 传入long格式化
     */
    @SuppressLint("SimpleDateFormat")
    public static String getLongToFormatDate3(long dateFormat, String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(strDate);
        return formatter.format(dateFormat);
    }


    /**
     * 传入Date格式化
     * yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringToFormatDate4(Date dateFormat, String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(strDate);
        return formatter.format(dateFormat);
    }

    /**
     * 获取星期
     *
     * @return
     */
    public static String getWeek() {
        String week = "";
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            week = "周日";
        } else if (weekday == 2) {
            week = "周一";
        } else if (weekday == 3) {
            week = "周二";
        } else if (weekday == 4) {
            week = "周三";
        } else if (weekday == 5) {
            week = "周四";
        } else if (weekday == 6) {
            week = "周五";
        } else if (weekday == 7) {
            week = "周六";
        }
        return week;
    }

    /**
     * 根据年月日算年龄
     */
    public static int getAgeFromBirthTime(String birthTimeString) {
        // 先截取到字符串中的年、月、日
        String strs[] = birthTimeString.contains("-") ? birthTimeString.trim().split("-") : birthTimeString.trim().split("/");
        int selectYear = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs[1]);
        int selectDay = Integer.parseInt(strs[2]);
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int years = yearNow - selectYear;
        int age = years <= 0 ? 0 : years;
        if (age > 0) {
            double selectPoint = Double.parseDouble(selectMonth + "." + selectDay);
            double nowPoint = Double.parseDouble(monthNow + "." + dayNow);
            if (nowPoint - selectPoint < 0) {
                age -= 1;
            }
        }
        return age;
    }

    /**
     * 将短时间格式字符串转换为时间
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * long转Calendar
     *
     * @param time
     * @return
     */
    public static Calendar longToCalendar(long time) {
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(time);
        return endCalendar;
    }

    /**
     * long转Calendar
     *
     * @param time
     * @return
     */
    public static Calendar longToCalendar(long time, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(time);
        return stringToCalendar(sdf.format(endCalendar.getTimeInMillis()), dateFormat);
    }

    /***
     * string转Calendar
     * @param string
     * @return
     */
    public static Calendar stringToCalendar(String string, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 为了传值需要新建实例年月日
     */
    public static class YearMonthDay {
        private int year;
        private int month;
        private int day;

        public YearMonthDay(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        @Override
        public String toString() {
            return year + "" + (month < 10 ? "0" + month : month) + "" + (day < 10 ? "0" + day : day);
        }
    }

    /***
     * long 转成 string
     * @param time
     * @param dateFormat
     * @return
     */
    public static String longToString(long time, String dateFormat) {
        String result = new SimpleDateFormat(dateFormat).format(new Date(time));
        return result;
    }

    public static long stringToLong(String time, String dateFormat) {
        Long result = null;
        try {
            result = Long.valueOf(new SimpleDateFormat(dateFormat).parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date dateToDate(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String dateStr = sdf.format(date);
        return strToDate(dateStr, dateFormat);
    }

    public static String jsonstringTostring(String time) {
        time = time.replace("Z", " UTC");//UTC是本地时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = null;
        try {
            d = format.parse(time);
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
//此处是将date类型装换为字符串类型，比如：Sat Nov 18 15:12:06 CST 2017转换为2017-11-18 15:12:06
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sf.format(d);
        return date;
    }

    public static Date stringToDate(String string, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);//注意月份是MM
        Date date = null;
        try {
            date = simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String calendarToString(Calendar calendar, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String dateStr = sdf.format(calendar.getTime());
        return dateStr;
    }
}