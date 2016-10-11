package org.scorpio.octopus.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类型转换工具类。
 * </p>
 *
 */
public class CastUtil {

	/**
	 *
	 * 将一个Object对象转换为确定的类型 例如Object为String的2007-01-01,c为Date.class 返回为Date类型
	 *
	 * @param val
	 *            待确定类型的对象
	 * @param clazz
	 *            目标类型
	 * @return 转换后的类型为clazz的对象
	 */
	public static Object castValue(Object val, Class clazz) {
		return cast(val, clazz);
	}

	/**
	 *
	 * 将一个Object对象转换为确定的类型 例如Object为String的2007-01-01 Class
	 * c为Date返回的Object实际为Date类型返回后可强制类型转换,或用于其他不需要明确类型的地方,此方法多用于反射的情况
	 *
	 * @param val
	 *            待确定类型的对象
	 * @param c
	 *            目标类型的class
	 * @return
	 */
	private static Object cast(Object val, Class clazz) {
		if (val == null)
			return null;
		if ((java.lang.String.class).equals(clazz))
			return CastUtil.toString(val, null);
		if ((java.lang.Integer.class).equals(clazz) || Integer.TYPE.equals(clazz))
			return CastUtil.toInteger(val, null);
		if ((java.lang.Double.class).equals(clazz) || Double.TYPE.equals(clazz))
			return CastUtil.toDouble(val, null);
		if ((java.lang.Short.class).equals(clazz) || Short.TYPE.equals(clazz))
			return CastUtil.toShort(val, null);
		if ((java.lang.Long.class).equals(clazz) || Long.TYPE.equals(clazz))
			return CastUtil.toLong(val, null);
		if ((java.lang.Float.class).equals(clazz) || Float.TYPE.equals(clazz))
			return CastUtil.toFloat(val, null);
		if ((java.lang.Byte.class).equals(clazz) || Byte.TYPE.equals(clazz))
			return CastUtil.toByte(val, null);
		if ((java.lang.Boolean.class).equals(clazz) || Boolean.TYPE.equals(clazz))
			return CastUtil.toBoolean(val, null);
		if ((java.util.Date.class).equals(clazz) || (java.sql.Date.class).equals(clazz)) {
			return CastUtil.toDateTime(val, null);
		}
		return null;
	}

	/**
	 * 强制类型转化Object为byte型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return Byte
	 */
	public static Byte toByte(Object obj, Byte def) {
		if (obj == null)
			return def;
		try {
			if (obj instanceof Number) {
				Number n = (Number) obj;
				return Byte.valueOf(n.byteValue() + "");
			}
			String s = toNotEmptyString(obj);
			if (s == null)
				return def;
			else
				return Byte.valueOf(s);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 将boolean类型转换为Boolean包装类 true为1 false为0
	 *
	 * @param be
	 *            待转化的boolean基本类型
	 * @return be对应的Boolean类型
	 */
	public static Byte toByte(boolean be) {
		if (be == false)
			return Byte.valueOf("0");
		else
			return Byte.valueOf("1");
	}

	/**
	 * 强制类型转化Object为String型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return String
	 */
	public static String toString(Object obj, String def) {
		if (obj == null)
			return def;
		try {
			return String.valueOf(obj).trim();
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 *
	 * 转换字符串返回值没有空
	 * 
	 * @param obj
	 *            待确定类型的对象
	 * @return String，如果是null或者""则返回null。
	 */
	public static String toNotEmptyString(Object obj) {
		String rs = toString(obj, "");
		if (rs.equals(""))
			return null;
		else
			return rs;
	}

	/**
	 *
	 * 转换字符串返回值没有null
	 * 
	 * @param obj
	 *            待确定类型的对象
	 * @return String，如果是null或者""则返回""。
	 */
	public static String toNotNullString(Object obj) {
		String rs = toString(obj, "");
		if (rs == null)
			return "";
		else
			return rs;
	}

	/**
	 * 强制类型转化Object为Short型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return Short
	 */
	public static Short toShort(Object obj, Short def) {
		if (obj == null)
			return def;
		try {
			if (obj instanceof Number) {
				Number n = (Number) obj;
				return Short.valueOf(n.shortValue() + "");
			}
			String s = toNotEmptyString(obj);
			if (s == null)
				return def;
			else
				return Short.valueOf(s);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为Integer型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return Integer
	 */
	public static Integer toInteger(Object obj, Integer def) {
		if (obj == null)
			return def;
		try {
			if (obj instanceof Number) {
				Number n = (Number) obj;
				return Integer.valueOf(n.intValue() + "");
			} else if (obj instanceof Boolean) {
				Boolean b = (Boolean) obj;
				return b.booleanValue() ? new Integer(1) : new Integer(0);
			}

			String s = toNotEmptyString(obj);
			if (s == null)
				return def;
			else
				return Integer.valueOf(s);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为Integer型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @return Integer,转换不成功时返回null
	 */
	public static Integer toInteger(Object obj) {
		return toInteger(obj, null);
	}

	/**
	 * 强制类型转化Object为int型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return int
	 */
	public static int toInt(Object obj, int def) {
		return toInteger(obj, Integer.valueOf(def + "")).intValue();
	}

	/**
	 * 强制类型转化Object为Long型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return Long
	 */
	public static Long toLong(Object obj, Long def) {
		if (obj == null)
			return def;
		try {
			if (obj instanceof Number) {
				Number n = (Number) obj;
				return Long.valueOf(n.longValue() + "");
			}
			String s = toNotEmptyString(obj);
			if (s == null)
				return def;
			else
				return Long.valueOf(s);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为long型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return long
	 */
	public static long toLong(Object obj, long def) {
		return toLong(obj, Long.valueOf(def + "")).longValue();
	}

	/**
	 * 强制类型转化Object为Long型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @return Long，转换不成功时返回null
	 */
	public static Long toLong(Object obj) {
		return toLong(obj, null);
	}

	/**
	 * 强制类型转化Object为Float型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return Float
	 */
	public static Float toFloat(Object obj, Float def) {
		if (obj == null)
			return def;
		try {
			if (obj instanceof Number) {
				Number n = (Number) obj;
				return Float.valueOf(n.floatValue() + "");
			}
			String s = toNotEmptyString(obj);
			if (s == null)
				return def;
			else
				return Float.valueOf(s);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为Float型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @return Float 转换不成功时返回null
	 */
	public static Float toFloat(Object obj) {
		return toFloat(obj, null);
	}

	/**
	 * 强制类型转化Object为Double型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return Double
	 */
	public static Double toDouble(Object obj, Double def) {
		if (obj == null)
			return def;
		try {
			if (obj instanceof Number) {
				Number n = (Number) obj;
				return Double.valueOf(n.doubleValue() + "");
			}
			if (obj instanceof String) {
				obj = ((String) obj).trim().replaceAll(",", "");
			}
			String s = toNotEmptyString(obj);
			if (s == null)
				return def;
			else
				return Double.valueOf(s);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为double型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return double
	 */
	public static double toDouble(Object obj, double def) {
		return toDouble(obj, Double.valueOf(def + "")).doubleValue();
	}

	/**
	 * 强制类型转化Object为Double型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @return Double 转换不成功时返回null
	 */
	public static Double toDouble(Object obj) {
		return toDouble(obj, null);
	}

	/**
	 * 将Byte转化成boolean类型
	 *
	 * @param be
	 *            待转换的Byte
	 * @return Byte非零既真 null为假
	 */
	public static boolean toBoolean(Byte be) {
		if (be == null || be.intValue() == 0)
			return false;
		return true;
	}

	/**
	 * 将Integer转化成boolean类型
	 * 
	 * @param inte
	 *            待转换的Integer
	 * @return Integer非零既真 null为假
	 */
	public static boolean toBoolean(Integer inte) {
		return !(inte == null || inte.intValue() == 0);
	}

	/**
	 * 强制类型转化Object为Boolean型
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            出现异常返回def
	 * @return Boolean
	 */
	public static Boolean toBoolean(Object obj, Boolean def) {
		if (obj == null)
			return def;
		try {
			if (obj instanceof Boolean) {
				Boolean n = (Boolean) obj;
				return n;
			} else if (obj instanceof Integer) {
				return Boolean.valueOf(!Integer.valueOf("0").equals(obj));
			}
			return Boolean.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为Date型(带时间)
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @param def
	 *            obj为null或"" 时返回def.
	 * @return Date ,如果obj不是符合规则的字符串时返回null
	 */
	public static Date toDateTime(Object obj, Date def) {
		if (obj == null) {
			return def;
		} else if (obj instanceof Date) {
			return (Date) obj;
		} else if (obj instanceof Calendar) {
			return ((Calendar) obj).getTime();
		} else {
			try {
				String s = toNotEmptyString(obj);
				if (s == null)
					return def;
				else {
					String[] datePatterns = new String[DatePatternEnum.values().length];
					for (int i = 0; i < datePatterns.length; i++) {
						datePatterns[i] = DatePatternEnum.values()[i].getPattern();
					}
					if (s == null || datePatterns == null) {
						throw new IllegalArgumentException("Date and Patterns must not be null");
					}

					SimpleDateFormat parser = null;
					ParsePosition pos = new ParsePosition(0);
					for (int i = 0; i < datePatterns.length; i++) {
						if (i == 0) {
							parser = new SimpleDateFormat(datePatterns[0]);
						} else {
							parser.applyPattern(datePatterns[i]);
						}
						pos.setIndex(0);
						Date date = parser.parse(s, pos);
						if (date != null && pos.getIndex() == s.length()) {
							return date;
						}
					}
					throw new ParseException("Unable to parse the date: " + s, -1);
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 强制类型转化Object为Date型(带时间)
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @return Date 转换不成功时返回null
	 */
	public static Date toDateTime(Object obj) {
		return toDateTime(obj, null);
	}

	/**
	 * 强制类型转化Object为Date型(不带时间)
	 *
	 * @param key
	 *            待确定类型的对象
	 * @param def
	 *            转换不成功时返回def
	 * @return Date
	 */
	public static Date toDate(Object key, Date def) {
		Date date = toDateTime(key, def);
		if (date == null)
			return null;
		return DateUtil.truncate(date, Calendar.DATE);
	}

	/**
	 * 强制类型转化Object为Date型(不带时间)
	 *
	 * @param obj
	 *            待确定类型的对象
	 * @return Date 转换不成功时返回null
	 */
	public static Date toDate(Object obj) {
		return toDate(obj, null);
	}

	/**
	 *
	 * 将数组连接成字符串，使用指定的分隔符。
	 *
	 * @param objs
	 *            数组
	 * @param separator
	 *            分隔符
	 */
	public static String join(Object[] objs, String separator) {
		if (objs == null) {
			throw new NullPointerException("objs is null");
		}

		if (separator == null) {
			throw new NullPointerException("separator is null");
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objs.length; i++) {
			Object object = objs[i];
			if (sb.length() > 0) {
				sb.append(separator);
			}
			sb.append(object.toString());
		}

		return sb.toString();
	}

	/**
	 * 将数组连成字符串，默认用","分隔
	 * 
	 * @param objs
	 *            数组
	 * @return 拼接好的字符串。如传入new String[]{"a","b","c","d"}则返回"a,b,c,d"
	 */
	public static String join(Object[] objs) {
		return join(objs, ",");
	}

	/**
	 *
	 * 批量转换Map中key的名字和value的类型
	 * 
	 * @param list
	 *            待处理的List
	 * @param hms
	 *            转换映射对象数组
	 * @return 已处理的List&lt;HashMapExt&gt;
	 */
	public static List castKeyValue(List list, HM[] hms) {
		List r = new ArrayList();
		for (Iterator iterator = r.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			HashMapExt hashMap = HashMapExt.valueOf(map);
			r.add(hashMap.castKeyValue(hms));
		}
		return r;
	}

	public static Map castKeyValue(Map map, List hms) {
		return castKeyValue(map, ((HM[]) hms.toArray(new HM[0])));
	}

	public static Map castKeyValue(Map map, HM[] hms) {
		Map rs = new HashMap();
		List keys = new ArrayList();
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			Object key = (Object) iterator.next();
			keys.add(key);
		}
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			Object key = (Object) iterator.next();
			Object value = map.get(key);
			if (value == null)
				continue;
			for (int i = 0; i < hms.length; i++) {
				HM hm = hms[i];
				if (key.equals(hm.getOldKey())) {
					if (hm.getClazz() != null) {
						value = CastUtil.castValue(value, hm.getClazz());
					}
					if (hm.getNewKey() != null && !keys.contains(hm.getNewKey())) {
						rs.remove(key);
						key = hm.getNewKey();
					} else {
						key = hm.getOldKey();
					}
				}
				rs.put(key, value);
			}
		}
		return rs;
	}

	/**
	 *
	 * 重命名List中map的keys
	 * 
	 * @param maps
	 *            包含待处理Map的集合
	 * @param oldKeys
	 *            需要修改的key值名称
	 * @param newKeys
	 *            新的key值名称
	 */
	public static void rename(List maps, String[] oldKeys, String[] newKeys) {
		for (Iterator iterator = maps.iterator(); iterator.hasNext();) {
			Map m = (Map) iterator.next();
			rename(m, oldKeys, newKeys);
		}
	}

	/**
	 *
	 * 重命名List中map的keys
	 * 
	 * @param maps
	 *            包含待处理Map的集合
	 * @param oldKeys
	 *            需要修改的key值名称
	 * @param newKeys
	 *            新的key值名称
	 */
	public static void rename(List maps, String oldKeys, String newKeys) {
		for (Iterator iterator = maps.iterator(); iterator.hasNext();) {
			Map m = (Map) iterator.next();
			rename(m, oldKeys.split(","), newKeys.split(","));
		}
	}

	/**
	 *
	 * 重命名map中的keys
	 * 
	 * @param map
	 *            指定map
	 * @param oldKeys
	 *            需要修改的key值名称
	 * @param newKeys
	 *            新的key值名称
	 */
	public static void rename(Map map, String[] oldKeys, String[] newKeys) {
		if (oldKeys.length != newKeys.length) {
			throw new RuntimeException("新旧key数组长度不等");
		}
		for (int i = 0; i < oldKeys.length; i++) {
			String oldKey = oldKeys[i].trim();
			String newKey = newKeys[i].trim();
			if (oldKey.equals(""))
				continue;
			if (!oldKey.equals(newKey) && !map.containsKey(newKey)) {
				rename(map, oldKey, newKey);
			}
		}
	}

	/**
	 *
	 * 修改map中指定的Key值
	 *
	 * @param map
	 *            指定map
	 * @param oldKey
	 *            需要修改的key值名称
	 * @param newKey
	 *            新的key值名称
	 */
	public static void rename(Map map, String oldKey, String newKey) {
		Object obj = map.get(oldKey);
		map.remove(oldKey);
		map.put(newKey, obj);
	}

	/**
	 * 批量将List中数组作为value,ns作为name，存入Map,再将Map封装到List返回
	 * 
	 * @param list
	 *            list中的元素为对象数组,即<code>List&lt;Object[]&gt;</code>,
	 * @param ns
	 *            name数组
	 * @return List&lt;Map&gt;
	 */
	public static List arrayToMap(List list, String[] ns) {
		List retList = new ArrayList();
		List names = Arrays.asList(ns);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] objs = (Object[]) iterator.next();
			retList.add(arrayToMap(objs, names));
		}
		return retList;
	}

	/**
	 * names作为key,objs作为value存入Map中。
	 * 
	 * @param objs
	 *            value数组
	 * @param names
	 *            key列表
	 */
	public static Map arrayToMap(Object[] objs, List names) {
		Map map = new HashMap();
		int i = 0;
		for (Iterator iterator = names.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			map.put(name, objs[i++]);
		}
		return map;
	}

	/**
	 * names作为key,objs作为value存入Map中。
	 * 
	 * @param objs
	 *            value数组
	 * @param names
	 *            key数组
	 */
	public static Map arrayToMap(Object[] objs, String[] names) {
		return arrayToMap(objs, Arrays.asList(names));
	}

}
