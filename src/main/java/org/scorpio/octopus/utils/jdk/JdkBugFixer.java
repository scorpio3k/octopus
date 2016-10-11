package org.scorpio.octopus.utils.jdk;

import java.math.BigDecimal;
import java.text.DecimalFormat;
/**
 * 严重且常用jdk bug修复类。
 *
 */
public class JdkBugFixer {
	private static DecimalFormat decimalFormat = new DecimalFormat("################0.########");
	
	/**
	 * double转换为BigDecimal 通过String变量为中间媒介
	 * @param d
	 * @return
	 */
	public static BigDecimal toBigDecimal(double d){
		return new BigDecimal(formatDouble(d));
	}
	/**
	 * 不使用科学计数法
	 * @param d
	 * @return
	 */
	public static String formatDouble(double d){
		return decimalFormat.format(d);
	}
	public static String formatDouble(Double d){
		return decimalFormat.format(d);
	}
	public static void main(String[] args) {
		double amt=0.06;
		System.out.println("OK:"+amt);
		System.out.println("OK:"+new BigDecimal("0.06"));
		System.out.println("BUG:"+new BigDecimal(amt));
		System.out.println("FIX:"+new org.scorpio.octopus.utils.jdk.BigDecimal(amt));
	}
}
