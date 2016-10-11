package org.scorpio.octopus.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TimeZone;

/**
 * 日期计算工具类，提供了 对日期的加减、比较等方法，但不提供格式化的方法。
 * <p>
 * 此类的部分源码参考自APACHE项目
 * </p>
 * 
 * @see DateFormatUtils
 */
public class DateUtil {
	/**
	 * The UTC time zone (often referred to as GMT).
	 */
	static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
	/**
	 * Number of milliseconds in a standard second.
	 * 
	 * @since 2.1
	 */
	private static final long MILLIS_PER_SECOND = 1000;
	/**
	 * Number of milliseconds in a standard minute.
	 * 
	 * @since 2.1
	 */
	private static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
	/**
	 * Number of milliseconds in a standard hour.
	 * 
	 * @since 2.1
	 */
	private static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
	/**
	 * Number of milliseconds in a standard day.
	 * 
	 * @since 2.1
	 */
	private static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

	/**
	 * This is half a month, so this represents whether a date is in the top or
	 * bottom half of the month.
	 */
	private final static int SEMI_MONTH = 1001;

	private static final int[][] fields = { { Calendar.MILLISECOND }, { Calendar.SECOND }, { Calendar.MINUTE },
			{ Calendar.HOUR_OF_DAY, Calendar.HOUR }, { Calendar.DATE, Calendar.DAY_OF_MONTH, Calendar.AM_PM
			/*
			 * Calendar.DAY_OF_YEAR, Calendar.DAY_OF_WEEK,
			 * Calendar.DAY_OF_WEEK_IN_MONTH
			 */
			}, { Calendar.MONTH, SEMI_MONTH }, { Calendar.YEAR }, { Calendar.ERA } };

	/**
	 * A week range, starting on Sunday.
	 */
	private final static int RANGE_WEEK_SUNDAY = 1;

	/**
	 * A week range, starting on Monday.
	 */
	private final static int RANGE_WEEK_MONDAY = 2;

	/**
	 * A week range, starting on the day focused.
	 */
	private final static int RANGE_WEEK_RELATIVE = 3;

	/**
	 * A week range, centered around the day focused.
	 */
	private final static int RANGE_WEEK_CENTER = 4;

	/**
	 * A month range, the week starting on Sunday.
	 */
	private final static int RANGE_MONTH_SUNDAY = 5;

	/**
	 * A month range, the week starting on Monday.
	 */
	private final static int RANGE_MONTH_MONDAY = 6;

	/**
	 * Constant marker for truncating
	 */
	private final static int MODIFY_TRUNCATE = 0;

	/**
	 * Constant marker for rounding
	 */
	private final static int MODIFY_ROUND = 1;

	/**
	 * Constant marker for ceiling
	 */
	private final static int MODIFY_CEILING = 2;

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 判断两个日期是否为同一天，忽略时间。
	 * </p>
	 * <p>
	 * Checks if two date objects are on the same day ignoring time.
	 * </p>
	 *
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false.
	 * </p>
	 * 
	 * @param date1
	 *            the first date, not altered, not null
	 * @param date2
	 *            the second date, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 * @since 2.1
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * <p>
	 * Checks if two calendar objects are on the same day ignoring time.
	 * </p>
	 *
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false.
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *             if either calendar is <code>null</code>
	 * @since 2.1
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 判断两个日期对象是否是同一时刻。
	 * </p>
	 * <p>
	 * Checks if two date objects represent the same instant in time.
	 * </p>
	 *
	 * <p>
	 * This method compares the long millisecond time of the two objects.
	 * </p>
	 * 
	 * @param date1
	 *            the first date, not altered, not null
	 * @param date2
	 *            the second date, not altered, not null
	 * @return true if they represent the same millisecond instant
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 * @since 2.1
	 */
	public static boolean isSameInstant(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return date1.getTime() == date2.getTime();
	}

	/**
	 * <p>
	 * Checks if two calendar objects represent the same instant in time.
	 * </p>
	 *
	 * <p>
	 * This method compares the long millisecond time of the two objects.
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same millisecond instant
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 * @since 2.1
	 */
	public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return cal1.getTime().getTime() == cal2.getTime().getTime();
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 判断两个calendar对象是否为同一本地时间
	 * <p>
	 * Checks if two calendar objects represent the same local time.
	 * </p>
	 *
	 * <p>
	 * This method compares the values of the fields of the two objects. In
	 * addition, both calendars must be the same of the same type.
	 * </p>
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same millisecond instant
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 * @since 2.1
	 */
	public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return (cal1.get(Calendar.MILLISECOND) == cal2.get(Calendar.MILLISECOND)
				&& cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND)
				&& cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE)
				&& cal1.get(Calendar.HOUR) == cal2.get(Calendar.HOUR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.getClass() == cal2.getClass());
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 不严格地将字符串解析为日期类型，通过尝试多个解析规则。
	 * </p>
	 * <p>
	 * Parses a string representing a date by trying a variety of different
	 * parsers.
	 * </p>
	 * 
	 * <p>
	 * The parse will try each parse pattern in turn. A parse is only deemed
	 * successful if it parses the whole of the input string. If no parse
	 * patterns match, a ParseException is thrown.
	 * </p>
	 * The parser will be lenient toward the parsed date.
	 * 
	 * @param str
	 *            the date to parse, not null
	 * @param parsePatterns
	 *            the date format patterns to use, see SimpleDateFormat, not
	 *            null
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *             if the date string or pattern array is null
	 * @throws ParseException
	 *             if none of the date patterns were suitable (or there were
	 *             none)
	 */
	public static Date parseDate(String str, String[] parsePatterns) throws ParseException {
		return parseDateWithLeniency(str, parsePatterns, true);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 严格地将字符串解析为日期类型，通过尝试多个解析规则。
	 * </p>
	 * <p>
	 * Parses a string representing a date by trying a variety of different
	 * parsers.
	 * </p>
	 * 
	 * <p>
	 * The parse will try each parse pattern in turn. A parse is only deemed
	 * successful if it parses the whole of the input string. If no parse
	 * patterns match, a ParseException is thrown.
	 * </p>
	 * The parser parses strictly - it does not allow for dates such as
	 * "February 942, 1996".
	 * 
	 * @param str
	 *            the date to parse, not null
	 * @param parsePatterns
	 *            the date format patterns to use, see SimpleDateFormat, not
	 *            null
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *             if the date string or pattern array is null
	 * @throws ParseException
	 *             if none of the date patterns were suitable
	 * @since 2.5
	 */
	public static Date parseDateStrictly(String str, String[] parsePatterns) throws ParseException {
		return parseDateWithLeniency(str, parsePatterns, false);
	}

	/**
	 * <p>
	 * Parses a string representing a date by trying a variety of different
	 * parsers.
	 * </p>
	 * 
	 * <p>
	 * The parse will try each parse pattern in turn. A parse is only deemed
	 * successful if it parses the whole of the input string. If no parse
	 * patterns match, a ParseException is thrown.
	 * </p>
	 * 
	 * @param str
	 *            the date to parse, not null
	 * @param parsePatterns
	 *            the date format patterns to use, see SimpleDateFormat, not
	 *            null
	 * @param lenient
	 *            Specify whether or not date/time parsing is to be lenient.
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *             if the date string or pattern array is null
	 * @throws ParseException
	 *             if none of the date patterns were suitable
	 * @see java.util.Calender#isLenient()
	 */
	private static Date parseDateWithLeniency(String str, String[] parsePatterns, boolean lenient)
			throws ParseException {
		if (str == null || parsePatterns == null) {
			throw new IllegalArgumentException("Date and Patterns must not be null");
		}

		SimpleDateFormat parser = new SimpleDateFormat();
		parser.setLenient(lenient);
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < parsePatterns.length; i++) {

			String pattern = parsePatterns[i];

			// LANG-530 - need to make sure 'ZZ' output doesn't get passed to
			// SimpleDateFormat
			if (parsePatterns[i].endsWith("ZZ")) {
				pattern = pattern.substring(0, pattern.length() - 1);
			}

			parser.applyPattern(pattern);
			pos.setIndex(0);

			String str2 = str;
			// LANG-530 - need to make sure 'ZZ' output doesn't hit
			// SimpleDateFormat as it will ParseException
			if (parsePatterns[i].endsWith("ZZ")) {
				int signIdx = indexOfSignChars(str2, 0);
				while (signIdx >= 0) {
					str2 = reformatTimezone(str2, signIdx);
					signIdx = indexOfSignChars(str2, ++signIdx);
				}
			}

			Date date = parser.parse(str2, pos);
			if (date != null && pos.getIndex() == str2.length()) {
				return date;
			}
		}
		throw new ParseException("Unable to parse the date: " + str, -1);
	}

	/**
	 * Index of sign charaters (i.e. '+' or '-').
	 * 
	 * @param str
	 *            The string to search
	 * @param startPos
	 *            The start position
	 * @return the index of the first sign character or -1 if not found
	 */
	private static int indexOfSignChars(String str, int startPos) {
		int idx = indexOf(str, '+', startPos);
		if (idx < 0) {
			idx = indexOf(str, '-', startPos);
		}
		return idx;
	}

	private static int indexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar, startPos);
	}

	private static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * Reformat the timezone in a date string.
	 *
	 * @param str
	 *            The input string
	 * @param signIdx
	 *            The index position of the sign characters
	 * @return The reformatted string
	 */
	private static String reformatTimezone(String str, int signIdx) {
		String str2 = str;
		if (signIdx >= 0 && signIdx + 5 < str.length() && Character.isDigit(str.charAt(signIdx + 1))
				&& Character.isDigit(str.charAt(signIdx + 2)) && str.charAt(signIdx + 3) == ':'
				&& Character.isDigit(str.charAt(signIdx + 4)) && Character.isDigit(str.charAt(signIdx + 5))) {
			str2 = str.substring(0, signIdx + 3) + str.substring(signIdx + 4);
		}
		return str2;
	}

	// -----------------------------------------------------------------------
	/**
	 * Adds a number of years to a date returning a new object. The original
	 * date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	// public static Date addYears(Date date, int amount) {
	// return add(date, Calendar.YEAR, amount);
	// }

	// -----------------------------------------------------------------------
	/**
	 * Adds a number of months to a date returning a new object. The original
	 * date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	// public static Date addMonths(Date date, int amount) {
	// return add(date, Calendar.MONTH, amount);
	// }

	// -----------------------------------------------------------------------
	/**
	 * 对日起增加指定的星期数并返回新的对象。 Adds a number of weeks to a date returning a new
	 * object. The original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addWeek(Date date, int amount) {
		return add(date, Calendar.WEEK_OF_YEAR, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * Adds a number of days to a date returning a new object. The original date
	 * object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	// public static Date addDays(Date date, int amount) {
	// return add(date, Calendar.DAY_OF_MONTH, amount);
	// }

	// -----------------------------------------------------------------------
	/**
	 * 对日期增加指定的小时数并返回一个新的对象。 Adds a number of hours to a date returning a new
	 * object. The original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 对日期增加指定的分钟数并返回新的对象。 Adds a number of minutes to a date returning a new
	 * object. The original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMinute(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 对日起增加指定的秒数并返回新的对象。 Adds a number of seconds to a date returning a new
	 * object. The original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 对日期增加指定的毫秒数并返回新的对象。 Adds a number of milliseconds to a date returning a
	 * new object. The original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMillisecond(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 增加指定的日期字段。 Adds to a date returning a new object. The original date
	 * object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param calendarField
	 *            the calendar field to add to
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	// -----------------------------------------------------------------------
	/**
	 * Sets the years field to a date returning a new object. The original date
	 * object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setYears(Date date, int amount) {
		return set(date, Calendar.YEAR, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置月份数并返回新的对象 The original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setMonths(Date date, int amount) {
		return set(date, Calendar.MONTH, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置天数并返回新的对象。 The original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setDays(Date date, int amount) {
		return set(date, Calendar.DAY_OF_MONTH, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置小时数并返回新的对象。 Hours range from 0-23. The original date object is
	 * unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setHours(Date date, int amount) {
		return set(date, Calendar.HOUR_OF_DAY, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置分钟数并返回新的对象。 The original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setMinutes(Date date, int amount) {
		return set(date, Calendar.MINUTE, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * Sets the seconds field to a date returning a new object. The original
	 * date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setSeconds(Date date, int amount) {
		return set(date, Calendar.SECOND, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置毫秒数并返回新的对象。 The original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	public static Date setMilliseconds(Date date, int amount) {
		return set(date, Calendar.MILLISECOND, amount);
	}

	// -----------------------------------------------------------------------
	/**
	 * Sets the specified field to a date returning a new object. This does not
	 * use a lenient calendar. The original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param calendarField
	 *            the calendar field to set the amount to
	 * @param amount
	 *            the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @since 2.4
	 */
	private static Date set(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		// getInstance() returns a new object, so this method is thread safe.
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.setTime(date);
		c.set(calendarField, amount);
		return c.getTime();
	}

	// -----------------------------------------------------------------------
	/**
	 * 将Date类型转换为Calendar类型。
	 * 
	 * @param date
	 *            the date to convert to a Calendar
	 * @return the created Calendar
	 * @throws NullPointerException
	 *             if null is passed in
	 * @since 2.6
	 */
	public static Calendar toCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 根据指定的字段对日期进行取整。
	 * </p>
	 * <p>
	 * <b>例如：</b>2002年3月28日13:45:01.231 <code>ceiling(date,Calendar.HOUR)</code>
	 * 将会返回2002年3月28日13:00:00.000。
	 * </p>
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if this
	 * was passed with HOUR, it would return 28 Mar 2002 14:00:00.000. If this
	 * was passed with MONTH, it would return 1 April 2002 0:00:00.000.
	 * </p>
	 * 
	 * <p>
	 * For a date in a timezone that handles the change to daylight saving time,
	 * rounding to Calendar.HOUR_OF_DAY will behave as follows. Suppose daylight
	 * saving time begins at 02:00 on March 30. Rounding a date that crosses
	 * this time would produce the following values:
	 * <ul>
	 * <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00</li>
	 * <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with
	 * @param field
	 *            the field from <code>Calendar</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date round(Date date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, MODIFY_ROUND);
		return gval.getTime();
	}

	/**
	 * <p>
	 * Round this date, leaving the field specified as the most significant
	 * field.
	 * </p>
	 *
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if this
	 * was passed with HOUR, it would return 28 Mar 2002 14:00:00.000. If this
	 * was passed with MONTH, it would return 1 April 2002 0:00:00.000.
	 * </p>
	 * 
	 * <p>
	 * For a date in a timezone that handles the change to daylight saving time,
	 * rounding to Calendar.HOUR_OF_DAY will behave as follows. Suppose daylight
	 * saving time begins at 02:00 on March 30. Rounding a date that crosses
	 * this time would produce the following values:
	 * <ul>
	 * <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00</li>
	 * <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with
	 * @param field
	 *            the field from <code>Calendar</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date (a different object)
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Calendar round(Calendar date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar rounded = (Calendar) date.clone();
		modify(rounded, field, MODIFY_ROUND);
		return rounded;
	}

	/**
	 * <p>
	 * Round this date, leaving the field specified as the most significant
	 * field.
	 * </p>
	 *
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if this
	 * was passed with HOUR, it would return 28 Mar 2002 14:00:00.000. If this
	 * was passed with MONTH, it would return 1 April 2002 0:00:00.000.
	 * </p>
	 * 
	 * <p>
	 * For a date in a timezone that handles the change to daylight saving time,
	 * rounding to Calendar.HOUR_OF_DAY will behave as follows. Suppose daylight
	 * saving time begins at 02:00 on March 30. Rounding a date that crosses
	 * this time would produce the following values:
	 * <ul>
	 * <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00</li>
	 * <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00</li>
	 * <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, either Date or Calendar
	 * @param field
	 *            the field from <code>Calendar</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ClassCastException
	 *             if the object type is not a <code>Date</code> or
	 *             <code>Calendar</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date round(Object date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (date instanceof Date) {
			return round((Date) date, field);
		} else if (date instanceof Calendar) {
			return round((Calendar) date, field).getTime();
		} else {
			throw new ClassCastException("Could not round " + date);
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 根据指定的字段对日期进行取整。
	 * </p>
	 * <p>
	 * <b>例如：</b>2002年3月28日13:45:01.231 <code>ceiling(date,Calendar.HOUR)</code>
	 * 将会返回2002年3月28日13:00:00.000。
	 * </p>
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with
	 * @param field
	 *            the field from <code>{@link Calendar}</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date truncate(Date date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, MODIFY_TRUNCATE);
		return gval.getTime();
	}

	/**
	 * <p>
	 * Truncate this date, leaving the field specified as the most significant
	 * field.
	 * </p>
	 *
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with
	 * @param field
	 *            the field from <code>Calendar</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date (a different object)
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Calendar truncate(Calendar date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar truncated = (Calendar) date.clone();
		modify(truncated, field, MODIFY_TRUNCATE);
		return truncated;
	}

	/**
	 * <p>
	 * Truncate this date, leaving the field specified as the most significant
	 * field.
	 * </p>
	 *
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, either <code>Date</code> or
	 *            <code>Calendar</code>
	 * @param field
	 *            the field from <code>Calendar</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ClassCastException
	 *             if the object type is not a <code>Date</code> or
	 *             <code>Calendar</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	public static Date truncate(Object date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (date instanceof Date) {
			return truncate((Date) date, field);
		} else if (date instanceof Calendar) {
			return truncate((Calendar) date, field).getTime();
		} else {
			throw new ClassCastException("Could not truncate " + date);
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * 根据指定的字段对日期进行取整。
	 * </p>
	 * <p>
	 * Ceil this date, leaving the field specified as the most significant
	 * field.
	 * </p>
	 *
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with
	 * @param field
	 *            the field from <code>Calendar</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 * @since 2.5
	 */
	public static Date ceiling(Date date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar gval = Calendar.getInstance();
		gval.setTime(date);
		modify(gval, field, MODIFY_CEILING);
		return gval.getTime();
	}

	/**
	 * <p>
	 * 根据指定的字段对日期进行取整。
	 * </p>
	 * <p>
	 * <b>例如：</b>2002年3月28日13:45:01.231 <code>ceiling(date,Calendar.HOUR)</code>
	 * 将会返回2002年3月28日13:00:00.000。
	 * </p>
	 * 
	 * @param date
	 *            the date to work with
	 * @param field
	 *            the field from <code>Calendar</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date (a different object)
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 * @since 2.5
	 */
	public static Calendar ceiling(Calendar date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar ceiled = (Calendar) date.clone();
		modify(ceiled, field, MODIFY_CEILING);
		return ceiled;
	}

	/**
	 * <p>
	 * 根据指定的字段对日期进行取整。
	 * </p>
	 * <p>
	 * Ceil this date, leaving the field specified as the most significant
	 * field.
	 * </p>
	 *
	 * <p>
	 * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you
	 * passed with HOUR, it would return 28 Mar 2002 13:00:00.000. If this was
	 * passed with MONTH, it would return 1 Mar 2002 0:00:00.000.
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, either <code>Date</code> or
	 *            <code>Calendar</code>
	 * @param field
	 *            the field from <code>Calendar</code> or
	 *            <code>SEMI_MONTH</code>
	 * @return the rounded date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code>
	 * @throws ClassCastException
	 *             if the object type is not a <code>Date</code> or
	 *             <code>Calendar</code>
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 * @since 2.5
	 */
	public static Date ceiling(Object date, int field) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (date instanceof Date) {
			return ceiling((Date) date, field);
		} else if (date instanceof Calendar) {
			return ceiling((Calendar) date, field).getTime();
		} else {
			throw new ClassCastException("Could not find ceiling of for type: " + date.getClass());
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Internal calculation method.
	 * </p>
	 * 
	 * @param val
	 *            the calendar
	 * @param field
	 *            the field constant
	 * @param modType
	 *            type to truncate, round or ceiling
	 * @throws ArithmeticException
	 *             if the year is over 280 million
	 */
	private static void modify(Calendar val, int field, int modType) {
		if (val.get(Calendar.YEAR) > 280000000) {
			throw new ArithmeticException("Calendar value too large for accurate calculations");
		}

		if (field == Calendar.MILLISECOND) {
			return;
		}

		// ----------------- Fix for LANG-59 ---------------------- START
		// ---------------
		// see http://issues.apache.org/jira/browse/LANG-59
		//
		// Manually truncate milliseconds, seconds and minutes, rather than
		// using
		// Calendar methods.

		Date date = val.getTime();
		long time = date.getTime();
		boolean done = false;

		// truncate milliseconds
		int millisecs = val.get(Calendar.MILLISECOND);
		if (MODIFY_TRUNCATE == modType || millisecs < 500) {
			time = time - millisecs;
		}
		if (field == Calendar.SECOND) {
			done = true;
		}

		// truncate seconds
		int seconds = val.get(Calendar.SECOND);
		if (!done && (MODIFY_TRUNCATE == modType || seconds < 30)) {
			time = time - (seconds * 1000L);
		}
		if (field == Calendar.MINUTE) {
			done = true;
		}

		// truncate minutes
		int minutes = val.get(Calendar.MINUTE);
		if (!done && (MODIFY_TRUNCATE == modType || minutes < 30)) {
			time = time - (minutes * 60000L);
		}

		// reset time
		if (date.getTime() != time) {
			date.setTime(time);
			val.setTime(date);
		}
		// ----------------- Fix for LANG-59 ----------------------- END
		// ----------------

		boolean roundUp = false;
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if (fields[i][j] == field) {
					// This is our field... we stop looping
					if (modType == MODIFY_CEILING || (modType == MODIFY_ROUND && roundUp)) {
						if (field == SEMI_MONTH) {
							// This is a special case that's hard to generalize
							// If the date is 1, we round up to 16, otherwise
							// we subtract 15 days and add 1 month
							if (val.get(Calendar.DATE) == 1) {
								val.add(Calendar.DATE, 15);
							} else {
								val.add(Calendar.DATE, -15);
								val.add(Calendar.MONTH, 1);
							}
							// ----------------- Fix for LANG-440
							// ---------------------- START ---------------
						} else if (field == Calendar.AM_PM) {
							// This is a special case
							// If the time is 0, we round up to 12, otherwise
							// we subtract 12 hours and add 1 day
							if (val.get(Calendar.HOUR_OF_DAY) == 0) {
								val.add(Calendar.HOUR_OF_DAY, 12);
							} else {
								val.add(Calendar.HOUR_OF_DAY, -12);
								val.add(Calendar.DATE, 1);
							}
							// ----------------- Fix for LANG-440
							// ---------------------- END ---------------
						} else {
							// We need at add one to this field since the
							// last number causes us to round up
							val.add(fields[i][0], 1);
						}
					}
					return;
				}
			}
			// We have various fields that are not easy roundings
			int offset = 0;
			boolean offsetSet = false;
			// These are special types of fields that require different rounding
			// rules
			switch (field) {
			case SEMI_MONTH:
				if (fields[i][0] == Calendar.DATE) {
					// If we're going to drop the DATE field's value,
					// we want to do this our own way.
					// We need to subtrace 1 since the date has a minimum of 1
					offset = val.get(Calendar.DATE) - 1;
					// If we're above 15 days adjustment, that means we're in
					// the
					// bottom half of the month and should stay accordingly.
					if (offset >= 15) {
						offset -= 15;
					}
					// Record whether we're in the top or bottom half of that
					// range
					roundUp = offset > 7;
					offsetSet = true;
				}
				break;
			case Calendar.AM_PM:
				if (fields[i][0] == Calendar.HOUR_OF_DAY) {
					// If we're going to drop the HOUR field's value,
					// we want to do this our own way.
					offset = val.get(Calendar.HOUR_OF_DAY);
					if (offset >= 12) {
						offset -= 12;
					}
					roundUp = offset >= 6;
					offsetSet = true;
				}
				break;
			}
			if (!offsetSet) {
				int min = val.getActualMinimum(fields[i][0]);
				int max = val.getActualMaximum(fields[i][0]);
				// Calculate the offset from the minimum allowed value
				offset = val.get(fields[i][0]) - min;
				// Set roundUp if this is more than half way between the minimum
				// and maximum
				roundUp = offset > ((max - min) / 2);
			}
			// We need to remove this field
			if (offset != 0) {
				val.set(fields[i][0], val.get(fields[i][0]) - offset);
			}
		}
		throw new IllegalArgumentException("The field " + field + " is not supported");

	}

	/**
	 * <p>
	 * Returns the number of milliseconds within the fragment. All datefields
	 * greater than the fragment will be ignored.
	 * </p>
	 * 
	 * <p>
	 * Asking the milliseconds of any date will only return the number of
	 * milliseconds of the current second (resulting in a number between 0 and
	 * 999). This method will retrieve the number of milliseconds for any
	 * fragment. For example, if you want to calculate the number of
	 * milliseconds past today, your fragment is Calendar.DATE or
	 * Calendar.DAY_OF_YEAR. The result will be all milliseconds of the past
	 * hour(s), minutes(s) and second(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a SECOND field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.SECOND as fragment will
	 * return 538</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.SECOND as fragment will
	 * return 538</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will
	 * return 10538 (10*1000 + 538)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in milliseconds)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the Calendar field part of date to calculate
	 * @return number of milliseconds within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 */
	public static long getFragmentInMilliseconds(Date date, int fragment) {
		return getFragment(date, fragment, Calendar.MILLISECOND);
	}

	/**
	 * <p>
	 * Returns the number of seconds within the fragment. All datefields greater
	 * than the fragment will be ignored.
	 * </p>
	 * 
	 * <p>
	 * Asking the seconds of any date will only return the number of seconds of
	 * the current minute (resulting in a number between 0 and 59). This method
	 * will retrieve the number of seconds for any fragment. For example, if you
	 * want to calculate the number of seconds past today, your fragment is
	 * Calendar.DATE or Calendar.DAY_OF_YEAR. The result will be all seconds of
	 * the past hour(s) and minutes(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a SECOND field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.MINUTE as fragment will
	 * return 10 (equivalent to deprecated date.getSeconds())</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will
	 * return 10 (equivalent to deprecated date.getSeconds())</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment
	 * will return 26110 (7*3600 + 15*60 + 10)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in seconds)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the Calendar field part of date to calculate
	 * @return number of seconds within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	public static long getFragmentInSeconds(Date date, int fragment) {
		return getFragment(date, fragment, Calendar.SECOND);
	}

	/**
	 * <p>
	 * Returns the number of minutes within the fragment. All datefields greater
	 * than the fragment will be ignored.
	 * </p>
	 * 
	 * <p>
	 * Asking the minutes of any date will only return the number of minutes of
	 * the current hour (resulting in a number between 0 and 59). This method
	 * will retrieve the number of minutes for any fragment. For example, if you
	 * want to calculate the number of minutes past this month, your fragment is
	 * Calendar.MONTH. The result will be all minutes of the past day(s) and
	 * hour(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a MINUTE field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment
	 * will return 15 (equivalent to deprecated date.getMinutes())</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment
	 * will return 15 (equivalent to deprecated date.getMinutes())</li>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 15</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 435 (7*60 + 15)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in minutes)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the Calendar field part of date to calculate
	 * @return number of minutes within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	public static long getFragmentInMinutes(Date date, int fragment) {
		return getFragment(date, fragment, Calendar.MINUTE);
	}

	/**
	 * <p>
	 * Returns the number of hours within the fragment. All datefields greater
	 * than the fragment will be ignored.
	 * </p>
	 * 
	 * <p>
	 * Asking the hours of any date will only return the number of hours of the
	 * current day (resulting in a number between 0 and 23). This method will
	 * retrieve the number of hours for any fragment. For example, if you want
	 * to calculate the number of hours past this month, your fragment is
	 * Calendar.MONTH. The result will be all hours of the past day(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a HOUR field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment
	 * will return 7 (equivalent to deprecated date.getHours())</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment
	 * will return 7 (equivalent to deprecated date.getHours())</li>
	 * <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 7</li>
	 * <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will
	 * return 127 (5*24 + 7)</li>
	 * <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment
	 * will return 0 (a millisecond cannot be split in hours)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the Calendar field part of date to calculate
	 * @return number of hours within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	public static long getFragmentInHours(Date date, int fragment) {
		return getFragment(date, fragment, Calendar.HOUR_OF_DAY);
	}

	/**
	 * <p>
	 * 根据指定的fragment返回相应的天数。
	 * </p>
	 * <p>
	 * 如果要计算日期为这一年的第多少天，fragment应设置为Calendar.YEAR
	 * </p>
	 * <p>
	 * <b>例如：</b>2008年2月28日 getFragmentInDays(date,Calendar.YEAR) 会返回59
	 * </p>
	 * <p>
	 * Returns the number of days within the fragment. All datefields greater
	 * than the fragment will be ignored.
	 * 
	 * <p>
	 * Asking the days of any date will only return the number of days of the
	 * current month (resulting in a number between 1 and 31). This method will
	 * retrieve the number of days for any fragment. For example, if you want to
	 * calculate the number of days past this year, your fragment is
	 * Calendar.YEAR. The result will be all days of the past month(s).
	 * </p>
	 * 
	 * <p>
	 * Valid fragments are: Calendar.YEAR, Calendar.MONTH, both
	 * Calendar.DAY_OF_YEAR and Calendar.DATE, Calendar.HOUR_OF_DAY,
	 * Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND A fragment less
	 * than or equal to a DAY field will return 0.
	 * </p>
	 * 
	 * <p>
	 * <ul>
	 * <li>January 28, 2008 with Calendar.MONTH as fragment will return 28
	 * (equivalent to deprecated date.getDay())</li>
	 * <li>February 28, 2008 with Calendar.MONTH as fragment will return 28
	 * (equivalent to deprecated date.getDay())</li>
	 * <li>January 28, 2008 with Calendar.YEAR as fragment will return 28</li>
	 * <li>February 28, 2008 with Calendar.YEAR as fragment will return 59</li>
	 * <li>January 28, 2008 with Calendar.MILLISECOND as fragment will return 0
	 * (a millisecond cannot be split in days)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the Calendar field part of date to calculate
	 * @return number of days within the fragment of date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	public static long getFragmentInDays(Date date, int fragment) {
		return getFragment(date, fragment, Calendar.DAY_OF_YEAR);
	}

	/**
	 * Date-version for fragment-calculation in any unit
	 * 
	 * @param date
	 *            the date to work with, not null
	 * @param fragment
	 *            the Calendar field part of date to calculate
	 * @param unit
	 *            Calendar field defining the unit
	 * @return number of units within the fragment of the date
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	private static long getFragment(Date date, int fragment, int unit) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getFragment(calendar, fragment, unit);
	}

	/**
	 * Calendar-version for fragment-calculation in any unit
	 * 
	 * @param calendar
	 *            the calendar to work with, not null
	 * @param fragment
	 *            the Calendar field part of calendar to calculate
	 * @param unit
	 *            Calendar field defining the unit
	 * @return number of units within the fragment of the calendar
	 * @throws IllegalArgumentException
	 *             if the date is <code>null</code> or fragment is not supported
	 * @since 2.4
	 */
	private static long getFragment(Calendar calendar, int fragment, int unit) {
		if (calendar == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		long millisPerUnit = getMillisPerUnit(unit);
		long result = 0;

		// Fragments bigger than a day require a breakdown to days
		switch (fragment) {
		case Calendar.YEAR:
			result += (calendar.get(Calendar.DAY_OF_YEAR) * MILLIS_PER_DAY) / millisPerUnit;
			break;
		case Calendar.MONTH:
			result += (calendar.get(Calendar.DAY_OF_MONTH) * MILLIS_PER_DAY) / millisPerUnit;
			break;
		}

		switch (fragment) {
		// Number of days already calculated for these cases
		case Calendar.YEAR:
		case Calendar.MONTH:

			// The rest of the valid cases
		case Calendar.DAY_OF_YEAR:
		case Calendar.DATE:
			result += (calendar.get(Calendar.HOUR_OF_DAY) * MILLIS_PER_HOUR) / millisPerUnit;
			//$FALL-THROUGH$
		case Calendar.HOUR_OF_DAY:
			result += (calendar.get(Calendar.MINUTE) * MILLIS_PER_MINUTE) / millisPerUnit;
			//$FALL-THROUGH$
		case Calendar.MINUTE:
			result += (calendar.get(Calendar.SECOND) * MILLIS_PER_SECOND) / millisPerUnit;
			//$FALL-THROUGH$
		case Calendar.SECOND:
			result += (calendar.get(Calendar.MILLISECOND) * 1) / millisPerUnit;
			break;
		case Calendar.MILLISECOND:
			break;// never useful
		default:
			throw new IllegalArgumentException("The fragment " + fragment + " is not supported");
		}
		return result;
	}

	/**
	 * Determines if two calendars are equal up to no more than the specified
	 * most significant field.
	 * 
	 * @param cal1
	 *            the first calendar, not <code>null</code>
	 * @param cal2
	 *            the second calendar, not <code>null</code>
	 * @param field
	 *            the field from <code>Calendar</code>
	 * @return <code>true</code> if equal; otherwise <code>false</code>
	 * @throws IllegalArgumentException
	 *             if any argument is <code>null</code>
	 * @see #truncate(Calendar, int)
	 * @see #truncatedEquals(Date, Date, int)
	 * @since 2.6
	 */
	public static boolean truncatedEquals(Calendar cal1, Calendar cal2, int field) {
		return truncatedCompareTo(cal1, cal2, field) == 0;
	}

	/**
	 * 判断两个日期是否一致，判断之前先对日期取整。 Determines if two dates are equal up to no more
	 * than the specified most significant field.
	 * 
	 * @param date1
	 *            the first date, not <code>null</code>
	 * @param date2
	 *            the second date, not <code>null</code>
	 * @param field
	 *            the field from <code>Calendar</code>
	 * @return <code>true</code> if equal; otherwise <code>false</code>
	 * @throws IllegalArgumentException
	 *             if any argument is <code>null</code>
	 * @see #truncate(Date, int)
	 * @see #truncatedEquals(Calendar, Calendar, int)
	 * @since 2.6
	 */
	public static boolean truncatedEquals(Date date1, Date date2, int field) {
		return truncatedCompareTo(date1, date2, field) == 0;
	}

	/**
	 * Determines how two calendars compare up to no more than the specified
	 * most significant field.
	 * 
	 * @param cal1
	 *            the first calendar, not <code>null</code>
	 * @param cal2
	 *            the second calendar, not <code>null</code>
	 * @param field
	 *            the field from <code>Calendar</code>
	 * @return a negative integer, zero, or a positive integer as the first
	 *         calendar is less than, equal to, or greater than the second.
	 * @throws IllegalArgumentException
	 *             if any argument is <code>null</code>
	 * @see #truncate(Calendar, int)
	 * @see #truncatedCompareTo(Date, Date, int)
	 * @since 2.6
	 */
	public static int truncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
		Calendar truncatedCal1 = truncate(cal1, field);
		Calendar truncatedCal2 = truncate(cal2, field);
		return truncatedCal1.getTime().compareTo(truncatedCal2.getTime());
	}

	/**
	 * 比较两个日期的大小,在比较之前先对日期进行取整操作。 Determines how two dates compare up to no more
	 * than the specified most significant field.
	 * 
	 * @param date1
	 *            the first date, not <code>null</code>
	 * @param date2
	 *            the second date, not <code>null</code>
	 * @param field
	 *            the field from <code>Calendar</code>
	 * @return a negative integer, zero, or a positive integer as the first date
	 *         is less than, equal to, or greater than the second.
	 * @throws IllegalArgumentException
	 *             if any argument is <code>null</code>
	 * @see #truncate(Calendar, int)
	 * @see #truncatedCompareTo(Date, Date, int)
	 * @since 2.6
	 */
	public static int truncatedCompareTo(Date date1, Date date2, int field) {
		Date truncatedDate1 = truncate(date1, field);
		Date truncatedDate2 = truncate(date2, field);
		return truncatedDate1.compareTo(truncatedDate2);
	}

	/**
	 * Returns the number of millis of a datefield, if this is a constant value
	 * 
	 * @param unit
	 *            A Calendar field which is a valid unit for a fragment
	 * @return number of millis
	 * @throws IllegalArgumentException
	 *             if date can't be represented in millisenconds
	 * @since 2.4
	 */
	private static long getMillisPerUnit(int unit) {
		long result = Long.MAX_VALUE;
		switch (unit) {
		case Calendar.DAY_OF_YEAR:
		case Calendar.DATE:
			result = MILLIS_PER_DAY;
			break;
		case Calendar.HOUR_OF_DAY:
			result = MILLIS_PER_HOUR;
			break;
		case Calendar.MINUTE:
			result = MILLIS_PER_MINUTE;
			break;
		case Calendar.SECOND:
			result = MILLIS_PER_SECOND;
			break;
		case Calendar.MILLISECOND:
			result = 1;
			break;
		default:
			throw new IllegalArgumentException("The unit " + unit + " cannot be represented is milleseconds");
		}
		return result;
	}

	/**
	 * <p>
	 * Date iterator.
	 * </p>
	 */
	static class DateIterator implements Iterator {
		private final Calendar endFinal;
		private final Calendar spot;

		/**
		 * Constructs a DateIterator that ranges from one date to another.
		 *
		 * @param startFinal
		 *            start date (inclusive)
		 * @param endFinal
		 *            end date (not inclusive)
		 */
		DateIterator(Calendar startFinal, Calendar endFinal) {
			super();
			this.endFinal = endFinal;
			spot = startFinal;
			spot.add(Calendar.DATE, -1);
		}

		/**
		 * Has the iterator not reached the end date yet?
		 *
		 * @return <code>true</code> if the iterator has yet to reach the end
		 *         date
		 */
		public boolean hasNext() {
			return spot.before(endFinal);
		}

		/**
		 * Return the next calendar in the iteration
		 *
		 * @return Object calendar for the next date
		 */
		public Object next() {
			if (spot.equals(endFinal)) {
				throw new NoSuchElementException();
			}
			spot.add(Calendar.DATE, 1);
			return spot.clone();
		}

		/**
		 * Always throws UnsupportedOperationException.
		 * 
		 * @throws UnsupportedOperationException
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/********************************************************************************************************/
	/**
	 * 对日期增加指定的天数。
	 * 
	 * @param d
	 *            日期
	 * @param n
	 *            增加的天数
	 */
	public static java.util.Date addDay(java.util.Date d, int n) {
		return dateAdd(d, Calendar.DATE, n);
	}

	/**
	 * 对日期增加指定的月数。
	 * 
	 * @param d
	 *            日期
	 * @param n
	 *            指定的月数
	 */
	public static java.util.Date addMonth(java.util.Date d, int n) {
		return dateAdd(d, Calendar.MONTH, n);
	}

	/**
	 * 对日期增加指定的年数。
	 * 
	 * @param d
	 *            日期
	 * @param n
	 *            年数
	 */
	public static Date addYear(java.util.Date d, int n) {
		return dateAdd(d, Calendar.YEAR, n);
	}

	/**
	 * 对日期增加指定的年、月、日。
	 * 
	 * @param d
	 *            日期
	 * @param mode
	 *            <ul>
	 *            <Li>年：{@link Calendar}.YEAR</li>
	 *            <Li>月：{@link Calendar}.MONTH</li>
	 *            <Li>日：{@link Calendar}.DATE</li>
	 *            </ul>
	 * @param n
	 *            增加的数量
	 */
	public static Date dateAdd(java.util.Date d, int mode, int n) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(d);
		cl.add(mode, n);
		return cl.getTime();
	}

	/**
	 * 求开始截至日期之间的天数差.
	 * 
	 * @param d1
	 *            开始日期
	 * @param d2
	 *            截至日期
	 */
	public static int dateDiff(Date d1, Date d2) {
		if (d1.after(d2)) {
			return 0 - dateDiff(d2, d1);
		}
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		int d = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		int y1 = getYear(d1);
		int y2 = getYear(d2);
		while (y1 < y2) {
			Date d0 = encodeDate(y1, 12, 31);
			c1.setTime(d0);
			d += c1.get(Calendar.DAY_OF_YEAR);
			c1.add(Calendar.YEAR, 1);
			y1 = c1.get(Calendar.YEAR);
		}
		return d;
	}

	/**
	 * 根据指定的年月日返回日期
	 * 
	 * @param y
	 *            年
	 * @param m
	 *            月
	 * @param d
	 *            日
	 */
	public static Date encodeDate(int y, int m, int d) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.set(Calendar.YEAR, y);
		cl.set(Calendar.MONTH, m - 1);
		cl.set(Calendar.DAY_OF_MONTH, d);
		cl.set(Calendar.HOUR, 0);
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);
		return cl.getTime();
	}

	/**
	 * <p>
	 * 获取日期中的天数。
	 * </p>
	 * <b>例如:</b>2013年5月13日，将返回13
	 */
	public static int getDay(java.util.Date d) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * <p>
	 * 获取日期中的月份数。
	 * </p>
	 * <b>例如:</b>2013年5月13日，将返回5
	 */
	public static int getMonth(java.util.Date d) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.MONTH) + 1;
	}

	/**
	 * <p>
	 * 获取日期中当月的最后一天的日期。
	 * </p>
	 * <b>例如:</b>2013年5月13日，将返回日期为2013年5月31日的Date类型
	 */
	public static Date getMonthEnd(java.util.Date date) {
		Date d = getMonthFirst(date);
		d = addMonth(d, 1);
		d = addDay(d, -1);
		return d;
	}

	/**
	 * <p>
	 * 获取日期中当月第一天的日期。
	 * </p>
	 * <b>例如:</b>2013年5月13日，将返回日期为2013年5月1日的Date类型
	 */
	public static Date getMonthFirst(java.util.Date date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		int y = cl.get(Calendar.YEAR);
		int m = cl.get(Calendar.MONTH) + 1;
		return encodeDate(y, m, 1);
	}

	/**
	 * <p>
	 * 获取日期中当年最后一天的日期。
	 * </p>
	 * <b>例如:</b>2013年5月13日，将返回日期为2013年12月31日的Date类型
	 */
	public static Date getYearEnd(java.util.Date date) {
		return encodeDate(getYear(date), 12, 31);
	}

	/**
	 * <p>
	 * 获取日期中当年第一天的日期。
	 * </p>
	 * <b>例如:</b>2013年5月13日，将返回日期为2013年1月1日的Date类型
	 */
	public static Date getYearFirst(java.util.Date date) {
		return encodeDate(getYear(date), 1, 1);
	}

	/**
	 * 返回指定日期之前的第一个星期？，若该日已经符合星期要求，返回该日期。
	 * 
	 * @param date
	 *            指定的日期
	 * @param weekDay
	 *            <ul>
	 *            <li>星期日:0</li>
	 *            <li>星期一:1</li>
	 *            <li>...</li>
	 *            <li>星期六:6</li>
	 *            </ul>
	 */
	public static Date getLastWeekDay(java.util.Date date, int weekDay) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(date);
		int dw = cl.get(Calendar.DAY_OF_WEEK);
		int n = toDayOfWeek(weekDay) - dw;
		if (n > 0)
			n = n - 7;
		return addDay(date, n);
	}

	/**
	 * 返回指定日期之后的第一个星期？，若该日已经符合星期要求，返回该日期。
	 * 
	 * @param date
	 *            指定的日期
	 * @param weekDay
	 *            <ul>
	 *            <li>星期日:0</li>
	 *            <li>星期一:1</li>
	 *            <li>...</li>
	 *            <li>星期六:6</li>
	 *            </ul>
	 */
	public static Date getNextWeekDay(java.util.Date date, int weekDay) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(date);
		int dw = cl.get(Calendar.DAY_OF_WEEK);
		int n = toDayOfWeek(weekDay) - dw;
		if (n < 0)
			n = n + 7;
		return addDay(date, n);
	}

	/**
	 * <p>
	 * 返回指定的星期mode。
	 * </p>
	 * 
	 * @param weekDay
	 *            <ul>
	 *            <li>星期日:0</li>
	 *            <li>星期一:1</li>
	 *            <li>...</li>
	 *            <li>星期六:6</li>
	 *            </ul>
	 */
	public static int toDayOfWeek(int weekDay) {
		switch (weekDay) {
		case 1:
			return Calendar.MONDAY;
		case 2:
			return Calendar.TUESDAY;
		case 3:
			return Calendar.WEDNESDAY;
		case 4:
			return Calendar.THURSDAY;
		case 5:
			return Calendar.FRIDAY;
		case 6:
			return Calendar.SATURDAY;
		case 7:
			return Calendar.SUNDAY;
		}
		return 0;
	}

	/**
	 * 获取星期数。 如果是星期日则返回1,星期六则返回7
	 */
	public static int getDayOfWeek(java.util.Date date) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(date);
		int dw = cl.get(Calendar.DAY_OF_WEEK);
		return dw;
	}

	/**
	 * 返回d日期之前的第一个周日.
	 */
	public static Date getLastSunday(java.util.Date date) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(date);
		int dw = cl.get(Calendar.DAY_OF_WEEK);
		int n = Calendar.SUNDAY - dw;
		if (n > 0)
			n = n - 7;
		return addDay(date, n);
	}

	/**
	 * 取年份.
	 */
	public static int getYear(java.util.Date d) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.YEAR);
	}

	/**
	 *
	 * 判断两个日期是否为同一时刻
	 * 
	 */
	public static boolean equalsDate(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			return date1.getTime() == date2.getTime();
		} else {
			return false;
		}
	}

	/**
	 * 取得当前日期，不包含时分秒
	 */
	public static Date getDate() {
		return getDate(new Date());
	}

	/**
	 * 去掉dt中的时分秒
	 */
	public static Date getDate(Date dt) {
		if (dt == null)
			return null;
		return encodeDate(getYear(dt), getMonth(dt), getDay(dt));
	}

	/**
	 * <p>
	 * 返回d日期星期几（英文）
	 * <p>
	 * 参数: Date
	 * <p>
	 * 返回数据类型: String
	 * <p>
	 * 返回数据格式: "monday"
	 * <p>
	 * 例:
	 */
	public static String getENWeekDay(java.util.Date d) {
		String[] dWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thurs", "Friday", "Saturday" };
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(d);
		int dDay = cl.get(Calendar.DAY_OF_WEEK);
		return dWeek[dDay];
	}

	/**
	 * <p>
	 * 取d日期本世纪秒数,如果d为空则返回当前时间本世纪秒数
	 * <p>
	 * 参数: Date d
	 * <p>
	 * 返回数据类型: long
	 * <p>
	 * 返回数据格式: 123456789
	 * <p>
	 * 例:
	 */
	public static long getIdentityByTime(java.util.Date d) {
		if (d == null) {
			d = new Date();
		}
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(d);
		int year = cl.get(Calendar.YEAR) - 2000;
		int h = cl.get(Calendar.HOUR_OF_DAY);
		int m = cl.get(Calendar.MINUTE);
		int s = cl.get(Calendar.SECOND);
		int day = cl.get(Calendar.DAY_OF_YEAR) + year * 366;
		int sec = s + m * 60 + h * 3600;
		return day * 86400 + sec;
	}

	/**
	 * 返回d1到d2的时间差（秒数）
	 * 
	 */
	public static int secondDiff(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		int d = c2.get(Calendar.SECOND) - c1.get(Calendar.SECOND);
		return d;
	}
}
