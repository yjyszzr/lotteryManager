package com.fh.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeekDateUtil {
    public static final DateTimeFormatter date_sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter date_sdf_second_zero = DateTimeFormatter.ofPattern("yyyy-MM-dd 0:0:0");
    public static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter yyyyMM = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final DateTimeFormatter MMdd = DateTimeFormatter.ofPattern("MM-dd");
    public static final DateTimeFormatter date_sdf_ch = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    public static final DateTimeFormatter time_sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter ymd_sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter yyyymmddhhmmss = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter short_time_sdf = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter hhmmssSdf = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter hh_mm_sdf = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String[] weekDays = { "周一", "周二", "周三", "周四", "周五", "周六","周日"};

    public static String getYMD(Date date) {
        SimpleDateFormat sdf =   new SimpleDateFormat("MM月dd日 HH:mm"); 
        return sdf.format(date); 
    }
    
    public static boolean isToday(String str, String formatStr){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
//            logger.error("解析日期错误", e);
        }
        Calendar c1 = Calendar.getInstance();              
        c1.setTime(date);                                 
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);     
        Calendar c2 = Calendar.getInstance(); 
        c2.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH)+1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);   
        if(year1 == year2 && month1 == month2 && day1 == day2){
            return true;
        }
        return false;   
    }
    
    
    /**
     * 时间是否在一周之内
     * @param addtime
     * @param now
     * @return
     */
    public static boolean isLatestWeek(Date addtime,Date now){  
        Calendar calendar = Calendar.getInstance();  //得到日历  
        calendar.setTime(now);//把当前时间赋给日历  
        calendar.add(Calendar.DAY_OF_MONTH, -7);  //设置为7天前  
        Date before7days = calendar.getTime();   //得到7天前的时间  
        if(before7days.getTime() < addtime.getTime()){  
            return true;  
        }else{  
            return false;  
        }  
    } 
    
    /**
     * 获取前多少天的所有日期，不包含当天
     * @param date
     * @param beforeDays
     * @return
     */
    public static List<String> getBeforeDatePeriod(Date date, int beforeDays){  
        List<String> datePeriodList = new ArrayList<String>();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);  
        for(int i=beforeDays;i>0;i--){  
            cal.set(Calendar.DAY_OF_YEAR , inputDayOfYear-i);  
            datePeriodList.add(dateFormat.format(cal.getTime()));  
        }  
        return datePeriodList;  
    } 
    
    /**
     * 获取后多少天的所有日期，不包含当天
     * @param date
     * @param beforeDays
     * @return
     */
    public static List<String> getAfterDatePeriod(Date date, int beforeDays){  
        List<String> datePeriodList = new ArrayList<String>();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);  
        for(int i=1;i<=3;i++){  
            cal.set(Calendar.DAY_OF_YEAR , inputDayOfYear+i);  
            datePeriodList.add(dateFormat.format(cal.getTime()));  
        }  
        return datePeriodList;  
    }
    
    
    /**
     * 获取当前时间之后过多少天后零点前的时间
     * @param currentTime
     * @param dayNum
     * @return
     */
    public static Integer getTimeAfterDays(Date currentTime,Integer dayNum,int h,int m,int s) {
    	if(0 == dayNum) {
    		dayNum = 1;
    	}

    	Calendar calendar = Calendar.getInstance();  
    	calendar.setTime(currentTime);
    	calendar.add(Calendar.DAY_OF_MONTH, dayNum - 1); 
    	calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), h, m, s);
        Long millions = calendar.getTimeInMillis()/1000;

		return millions.intValue();
    }
    
    /**
     * 获取指定时间的时间，单位s
     * @param currentTime
     * @param dayNum
     * @return
     */
    public static Integer getTimeSomeDate(Date currentTime) {
    	Calendar calendar = Calendar.getInstance();  
    	calendar.setTime(currentTime);
    	calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), 
    			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        Long millions = calendar.getTimeInMillis()/1000;
		return millions.intValue();
    }
    

    
    /**
     * 获取到截止时间为止的秒数，1970-01-01开始
     *
     * @param date
     * @param hour
     * @param min
     * @param sec
     * @return
     */
    public static long getTimeLong(LocalDate date, int hour, int min, int sec) {
        LocalDateTime time = LocalDateTime.of(date, LocalTime.of(hour, min, sec));
        Instant instant = time.atZone(ZoneId.systemDefault()).toInstant();
        return instant.getEpochSecond();
    }

    /**
     * 获取到截止时间为止的秒数，1970-01-01开始
     *
     * @return
     */
    public static Integer getCurrentTimeLong() {
        LocalDateTime time = LocalDateTime.now();
        Instant instant = time.atZone(ZoneId.systemDefault()).toInstant();
        return Math.toIntExact(instant.getEpochSecond());
    }
    
    /**
     * 获取某个时间字符串对应的时间戳
     * @param dateStr
     * @param df
     * @return
     */
    public static Date strToDate(String strDate) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	ParsePosition pos = new ParsePosition(0);
    	Date strtodate = formatter.parse(strDate, pos);
    	return strtodate;
   }
    
   public static String toStringDateByFormat(String strDate,String dateFormat) {
    	SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
    	Date date = null;
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	String transformDate = formatter.format(date);
    	return transformDate;
   }

    public static String toStringDateByFormat(Date strDate,String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String dateString = formatter.format(strDate);
        return dateString;
    }

    
    public static Integer getCurrentTimeLong(Long lepochSecond) {
    	LocalDateTime time = LocalDateTime.ofEpochSecond(lepochSecond, 0, ZoneOffset.of("+08:00"));
        Instant instant = time.atZone(ZoneId.systemDefault()).toInstant();
        return Math.toIntExact(instant.getEpochSecond());
    }
    
    /**
     * 根据格式获取，秒数，获取日期
     *
     * @param lepochSecond
     * @param formatter
     * @return
     */
    public static String getCurrentTimeString(Long lepochSecond, DateTimeFormatter formatter) {
        LocalDateTime time = LocalDateTime.ofEpochSecond(lepochSecond, 0, ZoneOffset.of("+08:00"));
        return time.format(formatter);
    }

    /**
     * 根据格式获取，秒数，获取日期
     *
     * @param lepochSecond
     * @param formatter
     * @return
     */
    public static String getTimeString(Integer lepochSecond, DateTimeFormatter formatter) {
        LocalDateTime time = LocalDateTime.ofEpochSecond(lepochSecond, 0, ZoneOffset.of("+08:00"));
        return time.format(formatter);
    }

    /**
     * 获取当前时间
     *
     * @param pattern
     * @return
     */
    public static String getCurrentTime(DateTimeFormatter pattern) {
        return getTime(LocalTime.now(), pattern);
    }

    /**
     * @param now
     * @param pattern
     * @return
     */
    public static String getTime(LocalTime now, DateTimeFormatter pattern) {
        return now.format(pattern);
    }

    /**
     * 默认时间格式的当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return getCurrentDateTime(LocalDateTime.now(), datetimeFormat);
    }
    
    /**
     * 默认时间格式的当前时间 yyyy-MM-dd HH:mm
     *
     * @return
     */
    public static String getCurrentTimeUtilMinute() {
        return getCurrentDateTime(LocalDateTime.now(), time_sdf);
    }

    public static String getCurrentDateTime(LocalDateTime now, DateTimeFormatter pattern) {
        return now.format(pattern);
    }

    public static String getCurrentDate(DateTimeFormatter pattern) {
        return getDate(LocalDate.now(), pattern);
    }

    public static String getDate(LocalDate localDate, DateTimeFormatter pattern) {
        return localDate.format(pattern);
    }

    public static String getDate(LocalDate localDate, String pattern) {
        return getDate(localDate, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 计算日期时间差,使用默认格式"yyyy-MM-dd HH:mm:ss"
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 时间差，格式为 X.xx/X小时h分
     * @throws Exception
     */
    public static String dateSubtractionHours(String start, String end) {
        return dateSubtractionHours(start, end, datetimeFormat);
    }

    /**
     * 计算日期时间差
     *
     * @param start  开始时间
     * @param end    结束时间
     * @param format 日期格式
     * @return
     * @throws Exception
     */
    public static String dateSubtractionHours(String start, String end, DateTimeFormatter format) {
        LocalDateTime start_time = LocalDateTime.parse(start, format);
        LocalDateTime end_time = LocalDateTime.parse(end, format);
        return dateSubtractionHours(start_time, end_time);
    }

    /**
     * 计算日期时间差
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     * @throws Exception
     */
    public static String dateSubtractionHours(LocalDateTime start, LocalDateTime end) {
        Duration d = Duration.between(start, end);
        StringBuilder subTime = new StringBuilder();
        subTime.append(Float.valueOf(d.toMinutes()) / 60).append("/");
        subTime.append(d.toHours() + "小时" + (d.toMinutes() % 60) + "分");
        return subTime.toString();
    }

    /**
     * 计算与当前日期的时间差 单位天
     * @param lepochSecond
     * @return
     */
    public static long diffBetweenCurrentDay(long lepochSecond) {
        return diffBetween(LocalDateTime.now(),lepochSecond).toDays();
    }

    /**
     * 计算与当前日期的时间差 单位分
     * @param lepochSecond
     * @return
     */
    public static long diffBetweenCurrentMin(long lepochSecond) {
        return diffBetween(LocalDateTime.now(),lepochSecond).toMinutes();
    }
    /**
     * 计算与当前日期的时间差 单位小时
     * @param lepochSecond
     * @return
     */
    public static long diffBetweenCurrentHours(long lepochSecond) {
        return diffBetween(LocalDateTime.now(),lepochSecond).toHours();
    }
    /**
     * 计算与当前日期的时间差
     * @param lepochSecond
     * @return Duration
     */
    public static Duration diffBetween(LocalDateTime dateTime,long lepochSecond) {
        LocalDateTime start = LocalDateTime.ofEpochSecond(lepochSecond, 0, ZoneOffset.of("+08:00"));
        return Duration.between(start, dateTime);
    }
    
	/**
	 * <pre>
	 * 根据指定的日期字符串获取星期几
	 * </pre>
	 * 
	 * @param strDate 指定的日期字符串(yyyy-MM-dd 或 yyyy/MM/dd)
	 * @return week
	 *         星期几(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY)
	 */
	public static String getWeekByDateStr(String strDate)
	{
		int year = Integer.parseInt(strDate.substring(0, 4));
		int month = Integer.parseInt(strDate.substring(5, 7));
		int day = Integer.parseInt(strDate.substring(8, 10));
 
		Calendar c = Calendar.getInstance();
 
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
 
		String week = "";
		int weekIndex = c.get(Calendar.DAY_OF_WEEK);
		switch (weekIndex)
		{
		case 1:
			week = "周日";
			break;
		case 2:
			week = "周一";
			break;
		case 3:
			week = "周二";
			break;
		case 4:
			week = "周三";
			break;
		case 5:
			week = "周四";
			break;
		case 6:
			week = "周五";
			break;
		case 7:
			week = "周六";
			break;
		}
		return week;
	}
    
    
    /**
     * 获取日期是星期几<br>
     * @param dateTime
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(LocalDateTime dateTime) {
        int weekDay = dateTime.getDayOfWeek().getValue();
        return weekDays[weekDay-1];
    }
    
    
    
    /**
     * 获取localDateTime
     * @param dateTimeStr
     * @param pattern
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, DateTimeFormatter pattern) {
        return LocalDateTime.parse(dateTimeStr, pattern);
    }
    
    /**
     * 获取localDate
     * @param dateTimeStr
     * @param pattern
     * @return
     */
    public static LocalDate parseLocalDate(String dateStr, DateTimeFormatter pattern) {
        return LocalDate.parse(dateStr, pattern);
    }
    
    /**
     * 获取当前日期是星期几<br>
     * @return 当前日期是星期几
     */
    public static String getCurrentWeekOfDate() {
        return getWeekOfDate(LocalDateTime.now());
    }
}
