package org.scorpio.octopus.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * 文本格式化，将日期，金额，数字等对象格式化为特定的文本格式。
 * <p>
 * 日期格式:
 * </p>
 * <ul>
 * <li>y:年</li>
 * <li>M:月</li>
 * <li>d:日</li>
 * <li>h:小时</li>
 * <li>m:分钟</li>
 * <li>s:秒</li>
 * <li>S:毫秒</li>
 * </ul>
 * <b>例如:</b>yyyy-MM-dd hh:mm:ss.SSS "2013-05-16 11:24:23.225"
 * 
 * <p>
 * 数字格式：
 * </p>
 * <ul>
 * <li>#:在整数位时表示一位或多位数字，在小数位时表示一位小数,如果位数不够则不显示</li>
 * <li>0:在整数位时表示一位数字，在小数位时表示一位小数，如果位数不够则补零</li>
 * </ul>
 * <b>例如：</b>#,##0.00 "12,233,344.34" "0.34"
 */
public class TextFormat {
	/**
	 * 将Calendar格式化为字符串。
	 * 
	 * @param d
	 *            java.util.Calendar对象
	 * @return 返回文本格式:<B><code> 'yyyy-mm-dd'</code></B>
	 */
	public static String enclose(java.util.Calendar d) {
		if (d == null || d.getTime() == null) {
			return null;
		}
		return enclose(d.getTime());
	}

	/**
	 * 将Date格式化为字符串。
	 * 
	 * @param d
	 *            java.util.Date对象
	 * @return 返回文本格式:<B><code> 'yyyy-mm-dd'</code></B>
	 */
	public static String enclose(java.util.Date d) {
		if (d == null) {
			return null;
		} else {
			StringBuffer s = new StringBuffer("'");
			s.append(formatDate(d)).append("'");
			return new String(s);
		}
	}

	/**
	 * 将String格式化为字符串。
	 * 
	 * @param s
	 *            String对象
	 * @return 返回文本格式：<B><code>'xxxxxxx'</code></B>
	 */
	public static String enclose(String s) {
		if (s == null) {
			return null;
		} else {
			return "'" + s + "'";
		}
	}

	/**
	 * 将Calendar格式化为字符串。
	 * 
	 * @param d
	 *            Calendar对象
	 * @return 返回文本格式: <B><code>"to_date('2013-04-19','YYYY-MM-DD')"</code></B>
	 */
	public static String enclose_todate(java.util.Calendar d) {
		if (d == null || d.getTime() == null) {
			return null;
		}
		return enclose_todate(d.getTime());
	}

	/**
	 * 将Date格式化为字符串。
	 * 
	 * @param d
	 *            Date对象
	 * @return 返回文本格式: <B><code>"to_date('2013-04-19','YYYY-MM-DD')"</code></B>
	 */
	public static String enclose_todate(java.util.Date d) {
		if (d == null) {
			return null;
		} else {
			return "to_date('" + formatDate(d) + "','YYYY-MM-DD')";
		}
	}

	/**
	 * 格式化输出金额，0.00返回"0.00"。
	 */
	public static String formatCurrency0(BigDecimal amount) {
		return amount == null ? "" : formatCurrency0(amount.doubleValue());
	}

	/**
	 * 格式化输出金额，0.00返回空串。
	 */
	public static String formatCurrency(Double amount) {
		return amount == null ? "" : formatCurrency(amount.doubleValue());
	}

	/**
	 * 格式化输出金额，0.00返回0.00。
	 * <p>
	 * <B>例如:</B> <code>调用 formatCurrency0(new Double(0.0) ) 返回 0.00</code>
	 * <p>
	 * <B>例如:</B>
	 * <code>调用 formatCurrency0(new Double(1111.0) ) 返回 1,111.00</code>
	 * 
	 * @param amount
	 *            Double对象
	 * @return 返回的文本保留两位小数点：1.00
	 */
	public static String formatCurrency0(Double amount) {
		return amount == null ? "" : formatCurrency0(amount.doubleValue());
	}

	/**
	 * 格式化输出金额，0.00返回"0.00"。
	 */
	public static String formatCurrency0(double amount) {
		String pattern = "#,###,###,###,##0.00";
		String ret = formatNumber(amount, pattern);
		return ret;
	}

	/**
	 * 格式化输出日期，分隔符为"-"。
	 * 
	 * @param date
	 *            Calendar对象
	 * @return 返回文本格式:<B><code>yyyy-mm-dd</code></B>
	 */
	public static String formatDate(java.util.Calendar date) {
		if (date == null || date.getTime() == null) {
			return null;
		}
		return formatDate(date.getTime());
	}

	/**
	 * 格式化输出日期， 分隔符为"-"。
	 * 
	 * @param date
	 *            日期对象
	 * @return 返回文本格式:<B><code>yyyy-mm-dd</code></B>
	 */
	public static String formatDate(java.util.Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 格式化输出日期。
	 * 
	 * @param pattern
	 *            日期格式
	 */
	public static String formatDate(java.util.Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 格式化输出日期。
	 * 
	 * @return 返回文本格式:<B><code>20130424</code></B>
	 */
	public static String formatDate8(java.util.Date d) {
		return formatDate(d, "yyyyMMdd");
	}

	/**
	 * 格式化输出日期。
	 * 
	 * @return 返回文本格式:<B><code>2013-04-24 01:37:47</code></B>
	 */
	public static String formatDateTime(java.util.Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化整形数。 <B>例如:</B> <code>调用 formatInteger(1) 返回 00000001</code>
	 */
	public static String formatInteger(int n) {
		return formatNumber(n, "00000000");
	}

	/**
	 * 使用指定格式格式化整形数。
	 * 
	 * @param pattern
	 *            整数格式
	 */
	public static String formatInteger(int n, String pattern) {
		return formatNumber(n, pattern);
	}

	/**
	 * 格式化日期。
	 * 
	 * @see #formatLongDate(Date)
	 */
	public static String formatLongDate(java.util.Calendar date) {
		if (date == null || date.getTime() == null) {
			return null;
		}
		return formatLongDate(date.getTime());
	}

	/**
	 * 格式化日期。 <B>例如：</B><code>调用 formatLongDate(new Date) 返回 2013年04月24日</code>
	 */
	public static String formatLongDate(java.util.Date date) {
		return formatDate(date, "yyyy年MM月dd日");
	}

	/**
	 * 格式化数字，使用指定格式。
	 * <p>
	 * 
	 * @see java.text.NumberFormat
	 * @param pattern
	 *            格式
	 */
	public static String formatNumber(double amount, String pattern) {
		java.text.NumberFormat nf = NumberFormat.getCurrencyInstance();
		DecimalFormat df = (DecimalFormat) nf;
		df.setDecimalSeparatorAlwaysShown(true);
		df.applyPattern(pattern);
		return df.format(amount);
	}

	/**
	 * @see #formatNumber(double, String)
	 */
	public static String formatNumber(Double amount, String pattern) {
		return amount == null ? "" : formatNumber(amount.doubleValue(), pattern);
	}

	public static String formatNumber(BigDecimal amount) {
		return formatNumber(amount, "##########.######");
	}

	public static String formatNumber(Number amount) {
		return formatNumber(amount, "##########.######");
	}

	public static String formatNumber(Double amount) {
		return formatNumber(amount, "##########.######");
	}

	public static String formatNumber(double amount) {
		return formatNumber(amount, "##########.######");
	}

	public static String formatNumber(Object amount) {
		if (amount == null) {
			return "";
		} else if (amount instanceof Number) {
			return formatNumber(((Number) amount), "##########.######");
		} else if (amount instanceof BigDecimal) {
			return formatNumber(((BigDecimal) amount), "##########.######");
		} else if (amount instanceof Double) {
			return formatNumber(((Double) amount), "##########.######");
		} else {
			return amount.toString();
		}
	}

	public static String formatCurrency(Object amount) {
		return formatCurrency(CastUtil.toDouble(amount));
	}

	public static String formatCurrency(String amount) {
		return formatCurrency(CastUtil.toDouble(amount));
	}

	public static String formatCurrency0(String amount) {
		return formatCurrency0(CastUtil.toDouble(amount));
	}

	public static String formatCurrency0(Object amount) {
		return formatCurrency0(CastUtil.toDouble(amount));
	}

	/**
	 * @see #formatNumber(double, String)
	 */
	public static String formatNumber(BigDecimal amount, String pattern) {
		return amount == null ? "" : formatNumber(amount.doubleValue(), pattern);
	}

	/**
	 * @see #formatNumber(double, String)
	 */
	public static String formatNumber(Number amount, String pattern) {
		return amount == null ? "" : formatNumber(amount.doubleValue(), pattern);
	}

	/**
	 * @see #formatNumber(double, String)
	 */
	public static String formatLong(long amount, String pattern) {
		java.text.NumberFormat nf = NumberFormat.getCurrencyInstance();
		DecimalFormat df = (DecimalFormat) nf;
		df.setDecimalSeparatorAlwaysShown(true);
		df.applyPattern(pattern);
		return df.format(amount);
	}

	/**
	 * 不加修饰输出金额，防止java缺省字符串转换中将100000000转换为"1E8"。
	 * <p>
	 * <B>例如：</B>
	 * <code>调用 formatPlainCurrency(1000000000) 返回 1000000000.00</code>
	 */
	public static String formatPlainCurrency(double amount) {
		String pattern = "############0.00";
		String ret = formatNumber(amount, pattern);
		return ret;
	}

	/**
	 * @see #formatPlainCurrency(double)
	 */
	public static String formatPlainCurrency(Double amount) {
		return amount == null ? "" : formatPlainCurrency(amount.doubleValue());
	}

	/**
	 * 获取指定日期的时钟分钟和秒钟。
	 * <p>
	 * 
	 * @return 返回文本格式:<B><code>HHmmss</code></B>
	 */
	public static String formatTime6(java.util.Date d) {
		return new SimpleDateFormat("HHmmss").format(d);
	}

	/**
	 * 获取当天开始时间，即时钟、分钟、秒钟都为零。
	 * <p>
	 */
	public static java.util.Calendar getCalendar() {
		Calendar ret = Calendar.getInstance();
		ret.setTime(getDate());
		return ret;
	}

	public static Calendar getCalendar(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		return cl;
	}

	/**
	 * 获取当天的开始时间，即时钟、分钟、秒钟都为零。
	 * <p>
	 * 
	 * @return 返回Date的打印为Wed Apr 24 00:00:00 GMT 2013
	 */
	public static java.util.Date getDate() {
		return getDate(new java.util.Date());
	}

	/**
	 * 获取指定日期中当天的开始时间，即时钟、分钟、秒钟都为零。
	 * <p>
	 * <B>例如：</B>
	 * <code>调用 getDate(new Date() ) 返回Date的打印为Wed Apr 24 00:00:00 GMT 2013</code>
	 */
	public static java.util.Date getDate(java.util.Date date) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(date);
		cl.set(Calendar.HOUR, 0);
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);
		return cl.getTime();
	}

	/**
	 * 获取当前日期。
	 * 
	 * @return 格式：<B>2013-04-22</B>
	 */
	public static String getDateText() {
		java.util.Date d = new java.util.Date();
		return formatDate(d);
	}

	/**
	 * 通过索引获取某位金额数字。
	 * <p>
	 * 
	 * @param val
	 *            金额
	 * @param i
	 *            <ul>
	 *            <li>1代表个位</li>
	 *            <li>2代表十位</li>
	 *            <li>3代表百位</li>
	 *            </ul>
	 * @return 指定位置的数字
	 */
	public static String getDigitByIndex(double val, int i) {
		return getDigitByIndex(val, i, "￥");
	}

	/**
	 * 通过索引获取某位金额数字。
	 * <p>
	 * 当指定的索引比该金额最高位高出一位，则返回指定前缀
	 * 
	 * @param val
	 *            金额
	 *            <ul>
	 *            <li>1代表个位</li>
	 *            <li>2代表十位</li>
	 *            <li>3代表百位</li>
	 *            </ul>
	 * @param prefix
	 *            指定前缀
	 */
	public static String getDigitByIndex(double val, int i, String prefix) {
		java.text.NumberFormat nf = NumberFormat.getCurrencyInstance();// 返回当前默认语言环境的货币格式。
		DecimalFormat df = (DecimalFormat) nf;
		df.setDecimalSeparatorAlwaysShown(true);
		df.applyPattern("##############0.00");
		String text = df.format(val);
		int point = text.indexOf(".");
		int index = point - i;
		String ret = "";
		if (index == -1) {
			ret = prefix;
		} else if (index >= 0 && index < text.length()) {
			ret = text.substring(index, index + 1);
		}
		return ret;
	}

	/**
	 * 将String类型转换为Calendar类型。
	 * <p>
	 * 要求String格式：<B>y-m-d</B>
	 */
	public static java.util.Calendar parseCalendar(String s) {
		return getCalendar(parseDate(s));
	}

	/**
	 * 将String类型转换为Calendar类型。
	 * <p>
	 * 要求String格式：<B>y-m-d h:m:s</B>
	 */
	public static java.util.Calendar parseCalendarTime(String s) {
		return getCalendar(parseDateTime(s));
	}

	/**
	 * 将第一个字母转化为大写：如果第一个字符不是字母则不转化。
	 */
	public static String toFirstCapString(String s) {
		if (s == null)
			return null;
		if (s.length() < 1)
			return "";
		String ch = s.substring(0, 1);
		String ss = s.substring(1);
		return ch.toUpperCase() + ss;
	}

	/**
	 * 将第一个字母转化为小写：如果第一个字符不是字母则不转化。
	 */
	public static String toFirstLowerString(String s) {
		if (s == null)
			return null;
		if (s.length() < 1)
			return "";
		String ch = s.substring(0, 1);
		String ss = s.substring(1);
		return ch.toLowerCase() + ss;
	}

	/**
	 * 将String类型转换为Date类型。
	 * <p>
	 * 要求String格式：<B>y-M-d</B>
	 */
	public static java.util.Date parseDate(String s) {
		if (s == null) {
			return null;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("y-M-d");
			return df.parse(s);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用指定的格式将object类型转换为Date类型
	 * 
	 * @param o
	 *            对象
	 * @param pattern
	 *            格式
	 */
	public static java.util.Date parseDate(Object o, String pattern) {
		if (o == null)
			return null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.parse(o.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将String类型转换为Date类型，要求String格式：<B>y-m-d h:m:s</B>
	 */
	public static java.util.Date parseDateTime(String s) {
		if (s == null) {
			return null;
		}
		if (s.length() < 8) {
			return null;
		}
		String datestr = new String(s);
		try {
			String stime = "";
			int loc3 = s.indexOf(" ");
			if (loc3 > 0) {
				stime = s.substring(loc3 + 1);
				s = s.substring(0, loc3);
			}
			// 年、月、日
			int loc1 = 0;
			int loc2 = s.indexOf("-");
			int y = Integer.parseInt(s.substring(loc1, loc2));
			loc1 = s.indexOf("-", loc2);
			loc2 = s.indexOf("-", loc1 + 1);
			int m = Integer.parseInt(s.substring(loc1 + 1, loc2));
			int d = Integer.parseInt(s.substring(loc2 + 1));
			int h = 0;
			int min = 0;
			int sec = 0;
			// 时、分、秒
			loc1 = stime.indexOf(":");
			if (loc1 > 0) {
				h = Integer.parseInt(stime.substring(0, loc1));
				loc2 = stime.indexOf(":", loc1 + 1);
				min = Integer.parseInt(stime.substring(loc1 + 1, loc2));
				loc1 = stime.indexOf(":", loc2);
				loc2 = stime.indexOf(".");
				String secStr = loc2 > 0 ? stime.substring(loc1 + 1, loc2) : stime.substring(loc1 + 1);
				sec = Integer.parseInt(secStr);
			}
			Calendar cl = java.util.Calendar.getInstance();
			cl.set(y, m - 1, d, h, min, sec);
			return cl.getTime();
		} catch (Exception e) {
			System.out.println("日期时间格式错误：" + datestr + " -- " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转换为double类型。
	 * <p>
	 * 如果包含非数字字符则去除。如果未包含任何数字字符，返回0.00。
	 */
	public static double parseDouble(String numberString) {
		try {
			double amount = Double.parseDouble(getDigit(numberString));
			return amount;
		} catch (Exception e) {
			return 0.00;
		}
	}

	/**
	 * 将String对象转换成int对象。
	 * <p>
	 * 
	 * @param s
	 *            被转换的String对象
	 * @return 返回转换后的int对象
	 */
	public static int parseInt(String s) {
		if (s == null) {
			return 0;
		}
		String v = "";
		String num = "0123456789-";
		for (int i = 0; i < s.length(); i++) {
			String ch = s.charAt(i) + "";
			if (num.indexOf(ch) >= 0) {
				v = v + ch;
			}
		}
		if (v.length() < 1) {
			return 0;
		} else {
			return Integer.parseInt(v);
		}
	}

	/**
	 * 将String对象转换成Integer对象。
	 * <p>
	 * 
	 * @param s
	 *            被转换的String对象
	 * @return 返回转换后的Integer对象
	 */
	public static Integer parseInteger(String s) {
		return new Integer(parseInt(s));
	}

	/**
	 * 将字节数组转换以16进制表示的字符串。0-9,A,B,C,D,E,F
	 * 
	 * @param b
	 *            byte[]对象
	 * @return 返回转换后的字符串
	 */
	public static String toHEXString(byte[] b) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0XFF;
			if (v < 16) {
				ret.append('0');
			}
			ret.append(Integer.toString(v, 16).toUpperCase());
		}
		return new String(ret);
	}

	/**
	 * <p>
	 * 去掉修饰字符，返回数字. 将字符串中所有不是0123456789-.的字符均忽略，返回其余字符。 <br>
	 * 例如：
	 * <ul>
	 * <li>"123,456,789.00" -> "123456789.00"
	 * <li>"￥123,456,789.00" -> "123456789.00"
	 * <li>"&nbsp;￥123,456,789.00" -> "123456789.00"
	 * <li>" &nbsp;￥123,456 ,789.00 " -> "123456789.00"
	 * <li>" &nbsp;￥-123,456 ,789.00 " -> "-123456789.00"
	 * </ul>
	 * <p>
	 * 
	 * @param s
	 *            带有非数字字符的字符串
	 * @return 仅包含数字、小数点和'-'的字符串。
	 */
	public static String getDigit(String s) {
		String num = "0123456789-.";
		String ret = "";
		if (s != null) {
			for (int i = 0; i < s.length(); i++) {
				String ch = s.charAt(i) + "";
				if (num.indexOf(ch) >= 0) {
					ret = ret + ch;
				}
			}
		}
		return ret;
	}

	/**
	 * 普通文本替换：替换所有。
	 * <p>
	 * 
	 * @param s
	 *            数据源
	 * @param oldStr
	 *            需要被替换的文本
	 * @param newStr
	 *            替换者
	 */
	public static String replace(String s, String oldStr, String newStr) {
		int si = 0, ei = 0;
		if (newStr != null) {
			do {
				si = s.indexOf(oldStr, ei);
				if (si >= 0) {
					s = s.substring(0, si) + newStr + s.substring(si + oldStr.length());
					ei = si + newStr.length();
				}
			} while (si >= 0);
		}
		return s;
	}

	/**
	 * 普通文本替换：替换第一个。
	 * <p>
	 * 
	 * @param s
	 *            数据源
	 * @param oldStr
	 *            需要被替换的文本
	 * @param newStr
	 *            替换者
	 */
	public static String replaceFirst(String s, String oldStr, String newStr) {
		int si = 0, ei = 0;
		if (newStr != null) {
			do {
				si = s.indexOf(oldStr, ei);
				if (si >= 0) {
					s = s.substring(0, si) + newStr + s.substring(si + oldStr.length());
					ei = si + newStr.length();
					break;
				}
			} while (si >= 0);
		}
		return s;
	}

	/**
	 * 解析XML文本。
	 * <p>
	 * 指定XML文本，获取指定标签mark中的值，如果不存在则返回为空，如果存在多对标签，则获取第一个值。
	 * 
	 * @param s
	 *            解析XML文本
	 * @param mark
	 *            需要解析的标签
	 * @return 返回解析标签中的值
	 */
	public static String getMarkedText(String s, String mark) {
		String begin = "<" + mark + ">";
		String end = "</" + mark + ">";
		int loc1 = s.indexOf(begin);
		int loc2 = s.indexOf(end);
		if (loc1 >= 0 && loc2 >= loc1) {
			return s.substring(loc1 + begin.length(), loc2);
		}
		return null;
	}

	/**
	 *
	 * 获取补全字符串。
	 * <p>
	 * 将数据来源origin转换为字符串，如果其长度小于补全长度resultlength，则使用补全字符complementChar进行补全。
	 * 
	 * @param origin
	 *            数据源
	 * @param complementChar
	 *            用于填充的字符
	 * @param resultlength
	 *            补全长度，如果源字符串小于此长度则进行补全。
	 * @return 返回补全后的字符串
	 */
	public static String getComplementString(Object origin, char complementChar, Integer resultlength) {
		String originString = origin.toString();
		StringBuffer sb = new StringBuffer();

		for (int i = 0, len = resultlength.intValue() - originString.length(); i < len; i++) {
			sb.append(complementChar);
		}

		sb.append(originString);

		return sb.toString();
	}

	/**
	 * 按照指定长度分割字符串
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String[] splitByLength(String str, int len) {

		if (str == null) {
			return null;
		} else if (str.length() <= len) {
			return new String[] { str };
		} else {
			int count = (int) (new Double(str.length()).doubleValue() / len + 0.5);
			String[] result = new String[count];
			for (int i = 0; i < count; i++) {
				int start = i * len;
				int end = (i + 1) * len < str.length() ? (i + 1) * len : str.length();
				result[i] = str.substring(start, end);
			}
			return result;
		}
	}

	/**
	 * @deprecated 由{@link StringUtils#split(String, String)}取代
	 */
	public static String[] split(String s, String tag) {
		int index = 0;
		int offset = tag.length();
		Vector v = new Vector();
		while (true) {
			String ss = "";
			int loc = s.indexOf(tag, index);
			if (loc < 0) {
				ss = s.substring(index);
				v.add(ss);
				break;
			} else {
				ss = s.substring(index, loc);
				v.add(ss);
				index = loc + offset;
			}
		}
		String[] ret = new String[v.size()];
		v.copyInto(ret);
		return ret;
	}

}
