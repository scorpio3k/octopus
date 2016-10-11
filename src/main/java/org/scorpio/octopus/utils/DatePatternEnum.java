package org.scorpio.octopus.utils;

/**
 * <p>
 * 日期格式枚举类。
 * </p>
 * <p>
 * 提供几种常见的日期格式
 * </p>
 * 
 * <ul>
 * <li>yyyy-MM-dd</li>
 * <li>yyyy-MM-dd HH:mm:ss</li>
 * <li>yyyy-MM-dd HH:mm:ss.S</li>
 * <li>yyyy-MM-dd HH:mm:ss.SS</li>
 * <li>yyyy-MM-dd HH:mm:ss.SSS</li>
 * </ul>
 *
 */
public enum DatePatternEnum {

	DATE("yyyy-MM-dd"), 
	DATE_TIME("yyyy-MM-dd HH:mm:ss"), 
	DATE_TIME_WITH_ONE_MILL("yyyy-MM-dd HH:mm:ss.S"), 
	DATE_TIME_WITH_TWO_MILL("yyyy-MM-dd HH:mm:ss.SS"), 
	DATE_TIME_WITH_THREE_MILL("yyyy-MM-dd HH:mm:ss.SSS");

	private String pattern;

	private DatePatternEnum(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * 获取日期格式字符串
	 */
	public final String getPattern() {
		return pattern;
	}
}
