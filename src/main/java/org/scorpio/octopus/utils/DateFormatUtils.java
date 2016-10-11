package org.scorpio.octopus.utils;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * 日期、时间格式化工具类，但不提供计算日期等方法。
 * <p>
 * 此类的部分源码参考自APACHE项目
 * </p>
 *
 * @author Apache Ant - DateUtils
 * @author Stephane Bailliez
 * @author Stefan Bodewig
 * @author Gary Gregory
 */
public class DateFormatUtils {

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * DateFormatUtils instances should NOT be constructed in standard
	 * programming.
	 * </p>
	 *
	 * <p>
	 * This constructor is public to permit tools that require a JavaBean
	 * instance to operate.
	 * </p>
	 */
	public DateFormatUtils() {
		super();
	}

	/**
	 * 根据指定的格式来格式化日期时间，使用UTC时区。
	 * <p>
	 * Formats a date/time into a specific pattern using the UTC time zone.
	 * </p>
	 * 
	 * @param millis
	 *            the date to format expressed in milliseconds
	 * @param pattern
	 *            the pattern to use to format the date
	 * @return the formatted date
	 */
	public static String formatUTC(long millis, String pattern) {
		return format(new Date(millis), pattern, DateUtil.UTC_TIME_ZONE, null);
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern using the UTC time zone.
	 * </p>
	 * 
	 * @param date
	 *            the date to format
	 * @param pattern
	 *            the pattern to use to format the date
	 * @return the formatted date
	 */
	public static String formatUTC(Date date, String pattern) {
		return format(date, pattern, DateUtil.UTC_TIME_ZONE, null);
	}

	/**
	 * 根据指定的格式来格式化日期时间，使用UTC时区。
	 * <p>
	 * Formats a date/time into a specific pattern using the UTC time zone.
	 * </p>
	 * 
	 * @param millis
	 *            the date to format expressed in milliseconds
	 * @param pattern
	 *            the pattern to use to format the date
	 * @param locale
	 *            the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String formatUTC(long millis, String pattern, Locale locale) {
		return format(new Date(millis), pattern, DateUtil.UTC_TIME_ZONE, locale);
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern using the UTC time zone.
	 * </p>
	 * 
	 * @param date
	 *            the date to format
	 * @param pattern
	 *            the pattern to use to format the date
	 * @param locale
	 *            the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String formatUTC(Date date, String pattern, Locale locale) {
		return format(date, pattern, DateUtil.UTC_TIME_ZONE, locale);
	}

	/**
	 * 根据指定的格式来格式化日期时间。
	 * <p>
	 * Formats a date/time into a specific pattern.
	 * </p>
	 * 
	 * @param millis
	 *            the date to format expressed in milliseconds
	 * @param pattern
	 *            the pattern to use to format the date
	 * @return the formatted date
	 */
	public static String format(long millis, String pattern) {
		return format(new Date(millis), pattern, null, null);
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern.
	 * </p>
	 * 
	 * @param date
	 *            the date to format
	 * @param pattern
	 *            the pattern to use to format the date
	 * @return the formatted date
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return format(date, pattern, null, null);
	}

	/**
	 * <p>
	 * Formats a calendar into a specific pattern.
	 * </p>
	 * 
	 * @param calendar
	 *            the calendar to format
	 * @param pattern
	 *            the pattern to use to format the calendar
	 * @return the formatted calendar
	 */
	public static String format(Calendar calendar, String pattern) {
		return format(calendar, pattern, null, null);
	}

	/**
	 * 根据指定的格式来格式化日期时间，可以指定时区。
	 * <p>
	 * Formats a date/time into a specific pattern in a time zone.
	 * </p>
	 * 
	 * @param millis
	 *            the time expressed in milliseconds
	 * @param pattern
	 *            the pattern to use to format the date
	 * @param timeZone
	 *            the time zone to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(long millis, String pattern, TimeZone timeZone) {
		return format(new Date(millis), pattern, timeZone, null);
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a time zone.
	 * </p>
	 * 
	 * @param date
	 *            the date to format
	 * @param pattern
	 *            the pattern to use to format the date
	 * @param timeZone
	 *            the time zone to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(Date date, String pattern, TimeZone timeZone) {
		return format(date, pattern, timeZone, null);
	}

	/**
	 * <p>
	 * Formats a calendar into a specific pattern in a time zone.
	 * </p>
	 * 
	 * @param calendar
	 *            the calendar to format
	 * @param pattern
	 *            the pattern to use to format the calendar
	 * @param timeZone
	 *            the time zone to use, may be <code>null</code>
	 * @return the formatted calendar
	 */
	public static String format(Calendar calendar, String pattern, TimeZone timeZone) {
		return format(calendar, pattern, timeZone, null);
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a locale.
	 * </p>
	 * 
	 * @param millis
	 *            the date to format expressed in milliseconds
	 * @param pattern
	 *            the pattern to use to format the date
	 * @param locale
	 *            the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(long millis, String pattern, Locale locale) {
		return format(new Date(millis), pattern, null, locale);
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a locale.
	 * </p>
	 * 
	 * @param date
	 *            the date to format
	 * @param pattern
	 *            the pattern to use to format the date
	 * @param locale
	 *            the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(Date date, String pattern, Locale locale) {
		return format(date, pattern, null, locale);
	}

	/**
	 * <p>
	 * Formats a calendar into a specific pattern in a locale.
	 * </p>
	 * 
	 * @param calendar
	 *            the calendar to format
	 * @param pattern
	 *            the pattern to use to format the calendar
	 * @param locale
	 *            the locale to use, may be <code>null</code>
	 * @return the formatted calendar
	 */
	public static String format(Calendar calendar, String pattern, Locale locale) {
		return format(calendar, pattern, null, locale);
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a time zone and locale.
	 * </p>
	 * 
	 * @param millis
	 *            the date to format expressed in milliseconds
	 * @param pattern
	 *            the pattern to use to format the date
	 * @param timeZone
	 *            the time zone to use, may be <code>null</code>
	 * @param locale
	 *            the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(long millis, String pattern, TimeZone timeZone, Locale locale) {
		return format(new Date(millis), pattern, timeZone, locale);
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a time zone and locale.
	 * </p>
	 * 
	 * @param date
	 *            the date to format
	 * @param pattern
	 *            the pattern to use to format the date
	 * @param timeZone
	 *            the time zone to use, may be <code>null</code>
	 * @param locale
	 *            the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(Date date, String pattern, TimeZone timeZone, Locale locale) {
		FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
		return df.format(date);
	}

	/**
	 * <p>
	 * Formats a calendar into a specific pattern in a time zone and locale.
	 * </p>
	 * 
	 * @param calendar
	 *            the calendar to format
	 * @param pattern
	 *            the pattern to use to format the calendar
	 * @param timeZone
	 *            the time zone to use, may be <code>null</code>
	 * @param locale
	 *            the locale to use, may be <code>null</code>
	 * @return the formatted calendar
	 */
	public static String format(Calendar calendar, String pattern, TimeZone timeZone, Locale locale) {
		FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
		return df.format(calendar);
	}

	/**
	 * 批量格式化。
	 * 
	 * @param list
	 *            形式为<code>List&lt;Map&gt;</code>
	 * @see #batchFormat(Map)
	 */
	public static void batchFormat(List list) {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			batchFormat(map);
		}
	}

	/**
	 * 批量格式化Map中的value
	 * 
	 * @param map
	 *            待格式化的Map
	 */
	public static void batchFormat(Map map) {
		Collection keys = map.keySet();
		for (Iterator i$ = keys.iterator(); i$.hasNext();) {
			Object key = i$.next();
			Object obj = map.get(key);
			map.put(key, formatObj(obj));
		}
	}

	/**
	 * 如果是日期类型则返回日期格式化字符串，如果是List&lt;Map&gt;则格式化Map。
	 * 
	 * @param obj
	 * @return
	 */
	private static Object formatObj(Object obj) {
		Object result = obj;
		if (obj instanceof Date) {
			Date date = (Date) obj;
			result = DateFormatUtils.format(date, DatePatternEnum.DATE.getPattern());
		} else if (obj instanceof List) {
			List list = (List) obj;
			for (int i = 0; i < list.size(); i++) {
				Object subObj = list.get(i);
				if (subObj instanceof Map) {
					batchFormat((Map) subObj);
				}
			}
		}
		return result;
	}
}
