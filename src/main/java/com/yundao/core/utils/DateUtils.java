package com.yundao.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class DateUtils {

	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String HHMMSS = "HHmmss";

	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	
	public static final String YYMMDDHHMMSSSSS = "yyMMddHHmmssSSS";

	public static final String YY_MM_DD_HH_MM_SS = "yy-MM-dd HH:mm:ss";

	public static final String DD_MM_YY = "MM/dd/yy";

	public static final String DAY_BEGIN = "00:00:00";

	public static final String DAY_END = "23:59:59";

	/**
	 * 获取指定日期的开始日期，如yyyy-MM-dd 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateBegin(Date date) {
		if (date == null) {
			return null;
		}
		return format(date, YYYY_MM_DD) + " " + DAY_BEGIN;
	}

	/**
	 * 获取指定日期的开始日期，如yyyy-MM-dd 23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateEnd(Date date) {
		if (date == null) {
			return null;
		}
		return format(date, YYYY_MM_DD) + " " + DAY_END;
	}

	/**
	 * 格式化指定的日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null || BooleanUtils.isBlank(pattern)) {
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}
	/**
	 * 格式化指定的日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(String pattern) {
		if (BooleanUtils.isBlank(pattern)) {
			return null;
		}
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * 获取当前的日期并格式化
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentTime(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * 获取所给时间按指定格式的毫秒数
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static long getMillisecond(String time, String pattern) throws Exception {
		return new SimpleDateFormat(pattern).parse(time).getTime();
	}

	/**
	 * 转化为日期对象，参数单位毫秒
	 * 
	 * @param time
	 * @return
	 */
	public static Date toDate(long time) {
		Date result = new Date();
		result.setTime(time);
		return result;
	}

	/**
	 * 检验所给的日期是否匹配所给的格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static boolean isValidDate(String date, String pattern) {
		boolean result = true;
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			format.setLenient(false);
			format.parse(date);
		}
		catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 转化字符串为日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static Date parse(String date, String pattern) throws Exception {
		if (BooleanUtils.isBlank(date) || BooleanUtils.isBlank(pattern)) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(date);
	}

	/**
	 * 获取时间相差多少秒
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDistanceTimes(Date date1, Date date2) {
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long sec = 0;
		diff = date1.getTime() - date2.getTime();
		sec = diff / ns;// 计算差多少秒
		return (int) sec;
	}
}
