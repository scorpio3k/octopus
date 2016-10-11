package org.scorpio.octopus.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 中文格式化工具 ， 对日期、数字和金额的中文格式化。
 */
public class ChineseFormat {
	/**
	 * 中文星期. <br>
	 * 若给定的日期为星期一时返回"一"，为星期日时返回"日"。返回内容中不含"星期"字样。
	 * 
	 * @param d
	 *            日期
	 * @return 星期文字
	 */
	public static String getWeekDay(java.util.Date d) {
		String[] dWeek = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(d);
		int dDay = cl.get(Calendar.DAY_OF_WEEK);
		return dWeek[dDay];
	}

	/**
	 * 格式化日期 yyyy年M月d日。
	 * <p>
	 * <B>例如:</B>2000年5月4日
	 * 
	 * @param d
	 *            日期
	 * @return 日期字符串
	 */
	public static String formatDate(java.util.Date d) {
		Calendar cl = java.util.Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.YEAR) + "年" + (1 + cl.get(Calendar.MONTH)) + "月" + cl.get(Calendar.DAY_OF_MONTH) + "日";
	}

	/**
	 * 将数字串转为中文大写。 "0123456789"->"零壹贰叁肆伍陆柒捌玖"
	 * 
	 * @param Ch
	 *            长度为1,'0'-'9'的字符串
	 * @return 大写中文
	 */
	public static String toCapitalNumber(String Ch) {
		final String S = "零壹贰叁肆伍陆柒捌玖";
		if (Ch.equals("-"))
			return "负";
		int L = 0;
		L = Integer.parseInt(Ch);
		return S.substring(L, L + 1);
	}

	/**
	 * 将数字串转为中文。 "0123456789"->"〇一二三四五六七八九"
	 * 
	 * @param Ch
	 *            长度为1,'0'-'9'的字符串
	 * @return 中文
	 */
	public static String toNumber(String Ch) {
		final String S = "〇一二三四五六七八九";
		int L = 0;
		L = Integer.parseInt(Ch);
		return S.substring(L, L + 1);
	}

	/**
	 * 将数字串转为全角数字。 "0123456789"->"０１２３４５６７８９"
	 * 
	 * @param Ch
	 *            长度为1,'0'-'9'的字符串
	 * @return 全角数字
	 */
	public static String toDigit(String Ch) {
		final String S = "０１２３４５６７８９";
		int L = 0;
		L = Integer.parseInt(Ch);
		return S.substring(L, L + 1);
	}

	/**
	 * 将金额转为元角分中文大写。 1234567.89->"壹佰贰拾叁万肆仟伍佰陆拾柒元捌角玖分"
	 * 
	 * @param val
	 *            带转化的金额
	 */
	public static String formatCurrency(double val) {
		String sign = val < 0 ? "负" : "";
		// 拆成整数部分和小数部分，s[0]为整数，s[1]为小数
		String[] s = splitCurrency(Math.abs(val));
		StringTokenizer st = new StringTokenizer(s[0], ",");
		Vector v = new Vector();
		while (st.hasMoreTokens()) {
			String group = st.nextToken();
			if (Integer.parseInt(group) > 0 || !v.isEmpty()) {
				String ss = getFourNumber(group); // 四位一处理
				if (ss != null)
					v.add(ss);
			}
		}
		String ints = mergeFourNumber(v);
		String fraction = getFraction(s[1]);
		String ret = ints + fraction;
		// 去掉连续的两个'零'，执行3次
		ret = ret.replaceAll("零零", "零");
		ret = ret.replaceAll("零零", "零");
		ret = ret.replaceAll("零零", "零");
		// 去掉开始的零
		if (ret.startsWith("零"))
			ret = ret.replaceFirst("零", "");
		ret = ret.replaceAll("亿万", "亿");
		return sign + ret;
	}

	/**
	 * 把金额拆成整数和小数两部分，整数部分4位一个逗号。
	 * <p>
	 * <B>例如:</B>0000,0009,4456,4246.34
	 * 
	 * @param val
	 *            待处理的金额值
	 */
	protected static String[] splitCurrency(double val) {
		String[] ret = new String[2];
		DecimalFormat f = (DecimalFormat) NumberFormat.getCurrencyInstance();
		String p = "0000,0000,0000,0000.00";
		f.setDecimalSeparatorAlwaysShown(false);
		f.applyPattern(p);
		String s = f.format(val);
		StringTokenizer st = new StringTokenizer(s, ".");
		ret[0] = st.nextToken(); // 整数部分
		ret[1] = st.nextToken(); // 小数部分
		return ret;
	}

	protected static String mergeFourNumber(Vector v) {
		StringBuffer s = new StringBuffer();
		String[] t = { "元", "万", "亿", "万" };
		for (int i = 0; i < v.size(); i++) {
			String ss = (String) v.get(i);
			int index = v.size() - i - 1;
			if (!ss.equals("零"))
				s.append(ss).append(t[index]);
		}
		return new String(s);
	}

	/**
	 * 小数部分
	 * 
	 * @param s
	 */
	protected static String getFraction(String s) {
		if (s.length() != 2)
			return s;
		if (s.equals("00"))
			return "整";
		String j = s.substring(0, 1);
		StringBuffer ret = new StringBuffer();
		ret.append(toCapitalNumber(j));
		if (!j.equals("0"))
			ret.append("角");
		String f = s.substring(1);
		if (!f.equals("0"))
			ret.append(toCapitalNumber(f)).append("分");
		return new String(ret);
	}

	/**
	 * 处理四位一组
	 * 
	 * @param s
	 */
	protected static String getFourNumber(String s) {
		StringBuffer ret = new StringBuffer();
		String[] t = { "仟", "佰", "拾", "" };
		for (int i = 0; i < s.length(); i++) {
			String ch = s.substring(i, i + 1);
			ret.append(toCapitalNumber(ch));
			if (!ch.equals("0")) {
				ret.append(t[i]);
			}
		}
		String r = new String(ret);
		r = r.replaceAll("零零", "零");
		r = r.replaceAll("零零", "零");
		r = r.replaceAll("零零", "零");
		if (r.endsWith("零")) {
			r = r.substring(0, r.length() - 1);
		}
		return r;
	}
}
