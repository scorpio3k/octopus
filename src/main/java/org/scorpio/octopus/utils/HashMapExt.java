package org.scorpio.octopus.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HashMap类的扩展，支持获取Value时的自动类型转换。
 *
 */
public class HashMapExt extends HashMap {
	private static final long serialVersionUID = -5682995161433843135L;

	/**
	 * 返回数组
	 */
	public Object[] getArray(String key) {
		Object o = get(key);
		if (o instanceof Object[]) {
			return (Object[]) o;
		} else {
			return null;
		}
	}

	public Double getDouble(String key) {
		return CastUtil.toDouble(get(key));
	}

	/**
	 * 返回Double类型数据,如果出现异常则返回def
	 * 
	 * @return Double
	 */
	public Double getDouble(String key, Double def) {
		return CastUtil.toDouble(get(key), def);
	}

	/**
	 * 返回Double数组
	 * 
	 * @param def
	 *            出现异常时返回的值
	 * @see #getDouble(String, Double)
	 */
	public Double[] getDoubleArray(String key, Double def) {
		Object[] array = getArray(key);
		Double[] ret = new Double[array.length];
		for (int i = 0; i < array.length; i++) {
			ret[i] = CastUtil.toDouble(array[i], def);
		}
		return ret;
	}

	/**
	 * @see #getDoubleArray(String, Double)
	 */
	public Double[] getDoubleArray(String key) {
		return getDoubleArray(key, null);
	}

	public Integer getInteger(String key) {
		return CastUtil.toInteger(get(key));
	}

	/**
	 * 返回Integer类型数据。
	 * 
	 * @param def
	 *            出现异常时返回的数
	 */
	public Integer getInteger(String key, Integer def) {
		return CastUtil.toInteger(get(key), def);
	}

	/**
	 * 返回Integer数组
	 * 
	 * @param def
	 *            出现异常时返回的值
	 */
	public Integer[] getIntegerArray(String key, Integer def) {
		Object[] array = getArray(key);
		Integer[] ret = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			ret[i] = CastUtil.toInteger(array[i], def);
		}
		return ret;
	}

	/**
	 * 返回Integer数组
	 * 
	 * @see #getInteger(String, Integer)
	 */
	public Integer[] getIntegerArray(String key) {
		return getIntegerArray(key, null);
	}

	public Boolean getBoolean(String key) {
		return CastUtil.toBoolean(get(key), Boolean.FALSE);
	}

	/**
	 * 返回Boolean类型数据。
	 * 
	 * @param def
	 *            出现异常时返回的数
	 */
	public Boolean getBoolean(String key, Boolean def) {
		return CastUtil.toBoolean(get(key), def);
	}

	/**
	 * 返回Boolean数组
	 * 
	 * @param def
	 *            出现异常时返回的值
	 */
	public Boolean[] getBooleanArray(String key, Boolean def) {
		Object[] array = getArray(key);
		Boolean[] ret = new Boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			ret[i] = CastUtil.toBoolean(array[i], def);
		}
		return ret;
	}

	/**
	 * @see #getBoolean(String, Boolean)
	 */
	public Boolean[] getBooleanArray(String key) {
		return getBooleanArray(key, null);
	}

	/**
	 * @see #getDate(String, Date)
	 */
	public Date getDate(String key) {
		return CastUtil.toDate(get(key));
	}

	/**
	 * 返回Date类型数据(去除时间),如果出现异常则返回def
	 */
	public Date getDate(String key, Date def) {
		return CastUtil.toDate(get(key), def);
	}

	/**
	 * 返回Date数组
	 * 
	 * @see #getDate(String, Date)
	 */
	public Date[] getDateArray(String key, Date def) {
		Object[] array = getArray(key);
		Date[] ret = new Date[array.length];
		for (int i = 0; i < array.length; i++) {
			ret[i] = CastUtil.toDate(array[i], def);
		}
		return ret;
	}

	/**
	 * @see #getDateArray(String, Date)
	 */
	public Date[] getDateArray(String key) {
		return getDateArray(key, null);
	}

	/**
	 * @see #getDateTime(String, Date)
	 */
	public Date getDateTime(String key) {
		return CastUtil.toDateTime(get(key));
	}

	/**
	 * 返回Date类型数据(包含时间),如果出现异常则返回def
	 */
	public Date getDateTime(String key, Date def) {
		return CastUtil.toDateTime(get(key), def);
	}

	/**
	 * 返回Date类型数组
	 * 
	 * @see #getDateTime(String, Date)
	 */
	public Date[] getDateTimeArray(String key, Date def) {
		Object[] array = getArray(key);
		Date[] ret = new Date[array.length];
		for (int i = 0; i < array.length; i++) {
			ret[i] = CastUtil.toDateTime(array[i], def);
		}
		return ret;
	}

	/**
	 * @see #getDateTimeArray(String, Date)
	 */
	public Date[] getDateTimeArray(String key) {
		return getDateTimeArray(key, null);
	}

	/**
	 * 返回一个不为<code>null</code>的字符串。 如果结果值为<code>null</code>,则返回空串""
	 */
	public String getNotNullString(String key) {
		return CastUtil.toNotNullString(get(key));
	}

	/**
	 * 返回一个不为空串<code>""</code>的字符串。 如果结果值为"",则返回<code>null</code>
	 */
	public String getNotEmptyString(String key) {
		return CastUtil.toNotEmptyString(get(key));
	}

	/**
	 * 返回字符串。 如果出现异常会返回<code>null</code>
	 * 
	 * @return String
	 */
	public String getString(String key) {
		return CastUtil.toString(get(key), null);
	}

	/**
	 * 返回字符串数组
	 * 
	 * @see #getString(String)
	 */
	public String[] getStringArray(String key) {
		Object[] array = getArray(key);
		String[] ret = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			ret[i] = CastUtil.toString(array[i], null);
		}
		return ret;
	}

	/**
	 * 返回不为空串""的字符串数组
	 * 
	 * @see #getNotEmptyString(String)
	 */
	public String[] getNotEmptyStringArray(String key) {
		Object[] array = getArray(key);
		String[] ret = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			ret[i] = CastUtil.toNotEmptyString(array[i]);
		}
		return ret;
	}

	/**
	 * 返回不为<code>null</code>的字符串数组
	 * 
	 * @see #getNotNullString(String)
	 */
	public String[] getNotNullStringArray(String key) {
		Object[] array = getArray(key);
		String[] ret = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			ret[i] = CastUtil.toNotNullString(array[i]);
		}
		return ret;
	}

	public Object put(Object key, Object value) {
		return super.put(key, value);
	}

	/**
	 * 返回List类型数据。 如果结果值不是<code>List</code>则返回<code>null</code>
	 */
	public List getList(String key) {
		Object o = get(key);
		if (o instanceof List) {
			return (List) o;
		} else {
			return null;
		}
	}

	/**
	 * 将map的value转换为数组返回
	 *
	 * @param keys
	 *            map的key,返回的value由此key来决定,按照key的顺序组成数组
	 * @return List
	 */
	public List toList(Object[] keys) {
		List list = new ArrayList(keys.length);
		for (int i = 0; i < keys.length; i++) {
			Object key = keys[i];
			list.add(get(key));
		}
		return list;
	}

	/**
	 *
	 * 批量转换Map中key的名字和value的类型
	 * 
	 * @param hms
	 *            转换映射对象数组
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM[] hms) {
		HashMapExt hme = new HashMapExt();
		hme.putAll(this);
		for (int i = 0; i < hms.length; i++) {
			HM m = hms[i];
			Object o = CastUtil.castValue(get(m.getOldKey()), m.getClazz());
			hme.remove(m.getOldKey());
			hme.put(m.getNewKey(), o);
		}
		return hme;
	}

	/**
	 * 转换Map中key的名字和value的类型
	 * 
	 * @param pns0
	 *            转换映射对象
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM pns0) {
		HM[] temp = { pns0 };
		return castKeyValue(temp);
	}

	/**
	 * 转换Map中key的名字和value的类型
	 * 
	 * @param pns0
	 *            转换映射对象
	 * @param pns1
	 *            转换映射对象
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM pns0, HM pns1) {
		HM[] temp = { pns0, pns1 };
		return castKeyValue(temp);
	}

	/**
	 * 转换Map中key的名字和value的类型
	 * 
	 * @param pns0
	 *            转换映射对象
	 * @param pns1
	 *            转换映射对象
	 * @param pns2
	 *            转换映射对象
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM pns0, HM pns1, HM pns2) {
		HM[] temp = { pns0, pns1, pns2 };
		return castKeyValue(temp);
	}

	/**
	 * 转换Map中key的名字和value的类型
	 * 
	 * @param pns0
	 *            转换映射对象
	 * @param pns1
	 *            转换映射对象
	 * @param pns2
	 *            转换映射对象
	 * @param pns3
	 *            转换映射对象
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM pns0, HM pns1, HM pns2, HM pns3) {
		HM[] temp = { pns0, pns1, pns2, pns3 };
		return castKeyValue(temp);
	}

	/**
	 * 转换Map中key的名字和value的类型
	 * 
	 * @param pns0
	 *            转换映射对象
	 * @param pns1
	 *            转换映射对象
	 * @param pns2
	 *            转换映射对象
	 * @param pns3
	 *            转换映射对象
	 * @param pns4
	 *            转换映射对象
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM pns0, HM pns1, HM pns2, HM pns3, HM pns4) {
		HM[] temp = { pns0, pns1, pns2, pns3, pns4 };
		return castKeyValue(temp);
	}

	/**
	 * 转换Map中key的名字和value的类型
	 * 
	 * @param pns0
	 *            转换映射对象
	 * @param pns1
	 *            转换映射对象
	 * @param pns2
	 *            转换映射对象
	 * @param pns3
	 *            转换映射对象
	 * @param pns4
	 *            转换映射对象
	 * @param pns5
	 *            转换映射对象
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM pns0, HM pns1, HM pns2, HM pns3, HM pns4, HM pns5) {
		HM[] temp = { pns0, pns1, pns2, pns3, pns4, pns5 };
		return castKeyValue(temp);
	}

	/**
	 * 转换Map中key的名字和value的类型
	 * 
	 * @param pns0
	 *            转换映射对象
	 * @param pns1
	 *            转换映射对象
	 * @param pns2
	 *            转换映射对象
	 * @param pns3
	 *            转换映射对象
	 * @param pns4
	 *            转换映射对象
	 * @param pns5
	 *            转换映射对象
	 * @param pns6
	 *            转换映射对象
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM pns0, HM pns1, HM pns2, HM pns3, HM pns4, HM pns5, HM pns6) {
		HM[] temp = { pns0, pns1, pns2, pns3, pns4, pns5, pns6 };
		return castKeyValue(temp);
	}

	/**
	 * 转换Map中key的名字和value的类型
	 * 
	 * @param pns0
	 *            转换映射对象
	 * @param pns1
	 *            转换映射对象
	 * @param pns2
	 *            转换映射对象
	 * @param pns3
	 *            转换映射对象
	 * @param pns4
	 *            转换映射对象
	 * @param pns5
	 *            转换映射对象
	 * @param pns6
	 *            转换映射对象
	 * @param pns7
	 *            转换映射对象
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM pns0, HM pns1, HM pns2, HM pns3, HM pns4, HM pns5, HM pns6, HM pns7) {
		HM[] temp = { pns0, pns1, pns2, pns3, pns4, pns5, pns6, pns7 };
		return castKeyValue(temp);
	}

	/**
	 * 转换Map中key的名字和value的类型
	 * 
	 * @param pns0
	 *            转换映射对象
	 * @param pns1
	 *            转换映射对象
	 * @param pns2
	 *            转换映射对象
	 * @param pns3
	 *            转换映射对象
	 * @param pns4
	 *            转换映射对象
	 * @param pns5
	 *            转换映射对象
	 * @param pns6
	 *            转换映射对象
	 * @param pns7
	 *            转换映射对象
	 * @param pns8
	 *            转换映射对象
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM pns0, HM pns1, HM pns2, HM pns3, HM pns4, HM pns5, HM pns6, HM pns7, HM pns8) {
		HM[] temp = { pns0, pns1, pns2, pns3, pns4, pns5, pns6, pns7, pns8 };
		return castKeyValue(temp);
	}

	/**
	 * 转换Map中key的名字和value的类型
	 * 
	 * @param pns0
	 *            转换映射对象
	 * @param pns1
	 *            转换映射对象
	 * @param pns2
	 *            转换映射对象
	 * @param pns3
	 *            转换映射对象
	 * @param pns4
	 *            转换映射对象
	 * @param pns5
	 *            转换映射对象
	 * @param pns6
	 *            转换映射对象
	 * @param pns7
	 *            转换映射对象
	 * @param pns8
	 *            转换映射对象
	 * @param pns9
	 *            转换映射对象
	 * @return 转换后的Map
	 */
	public HashMapExt castKeyValue(HM pns0, HM pns1, HM pns2, HM pns3, HM pns4, HM pns5, HM pns6, HM pns7, HM pns8,
			HM pns9) {
		HM[] temp = { pns0, pns1, pns2, pns3, pns4, pns5, pns6, pns7, pns8, pns9 };
		return castKeyValue(temp);
	}

	/**
	 * 批量删除键值对
	 * 
	 * @param keys
	 *            要删除的key数组
	 */
	public void removeByKey(Object[] keys) {
		for (int i = 0; i < keys.length; i++) {
			Object key = keys[i];
			if (key != null) {
				remove(key);
			}
		}
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param pns0
	 *            要删除的key
	 */
	public void removeByKey(Object pns0) {
		Object[] temp = { pns0 };
		removeByKey(temp);
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param pns0
	 *            要删除的key
	 * @param pns1
	 *            要删除的key
	 */
	public void removeByKey(Object pns0, Object pns1) {
		Object[] temp = { pns0, pns1 };
		removeByKey(temp);
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param pns0
	 *            要删除的key
	 * @param pns1
	 *            要删除的key
	 * @param pns2
	 *            要删除的key
	 */
	public void removeByKey(Object pns0, Object pns1, Object pns2) {
		Object[] temp = { pns0, pns1, pns2 };
		removeByKey(temp);
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param pns0
	 *            要删除的key
	 * @param pns1
	 *            要删除的key
	 * @param pns2
	 *            要删除的key
	 * @param pns3
	 *            要删除的key
	 */
	public void removeByKey(Object pns0, Object pns1, Object pns2, Object pns3) {
		Object[] temp = { pns0, pns1, pns2, pns3 };
		removeByKey(temp);
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param pns0
	 *            要删除的key
	 * @param pns1
	 *            要删除的key
	 * @param pns2
	 *            要删除的key
	 * @param pns3
	 *            要删除的key
	 * @param pns4
	 *            要删除的key
	 */
	public void removeByKey(Object pns0, Object pns1, Object pns2, Object pns3, Object pns4) {
		Object[] temp = { pns0, pns1, pns2, pns3, pns4 };
		removeByKey(temp);
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param pns0
	 *            要删除的key
	 * @param pns1
	 *            要删除的key
	 * @param pns2
	 *            要删除的key
	 * @param pns3
	 *            要删除的key
	 * @param pns4
	 *            要删除的key
	 * @param pns5
	 *            要删除的key
	 */
	public void removeByKey(Object pns0, Object pns1, Object pns2, Object pns3, Object pns4, Object pns5) {
		Object[] temp = { pns0, pns1, pns2, pns3, pns4, pns5 };
		removeByKey(temp);
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param pns0
	 *            要删除的key
	 * @param pns1
	 *            要删除的key
	 * @param pns2
	 *            要删除的key
	 * @param pns3
	 *            要删除的key
	 * @param pns4
	 *            要删除的key
	 * @param pns5
	 *            要删除的key
	 * @param pns6
	 *            要删除的key
	 */
	public void removeByKey(Object pns0, Object pns1, Object pns2, Object pns3, Object pns4, Object pns5, Object pns6) {
		Object[] temp = { pns0, pns1, pns2, pns3, pns4, pns5, pns6 };
		removeByKey(temp);
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param pns0
	 *            要删除的key
	 * @param pns1
	 *            要删除的key
	 * @param pns2
	 *            要删除的key
	 * @param pns3
	 *            要删除的key
	 * @param pns4
	 *            要删除的key
	 * @param pns5
	 *            要删除的key
	 * @param pns6
	 *            要删除的key
	 * @param pns7
	 *            要删除的key
	 */
	public void removeByKey(Object pns0, Object pns1, Object pns2, Object pns3, Object pns4, Object pns5, Object pns6,
			Object pns7) {
		Object[] temp = { pns0, pns1, pns2, pns3, pns4, pns5, pns6, pns7 };
		removeByKey(temp);
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param pns0
	 *            要删除的key
	 * @param pns1
	 *            要删除的key
	 * @param pns2
	 *            要删除的key
	 * @param pns3
	 *            要删除的key
	 * @param pns4
	 *            要删除的key
	 * @param pns5
	 *            要删除的key
	 * @param pns6
	 *            要删除的key
	 * @param pns7
	 *            要删除的key
	 * @param pns8
	 *            要删除的key
	 */
	public void removeByKey(Object pns0, Object pns1, Object pns2, Object pns3, Object pns4, Object pns5, Object pns6,
			Object pns7, Object pns8) {
		Object[] temp = { pns0, pns1, pns2, pns3, pns4, pns5, pns6, pns7, pns8 };
		removeByKey(temp);
	}

	/**
	 * 根据key删除键值对
	 * 
	 * @param pns0
	 *            要删除的key
	 * @param pns1
	 *            要删除的key
	 * @param pns2
	 *            要删除的key
	 * @param pns3
	 *            要删除的key
	 * @param pns4
	 *            要删除的key
	 * @param pns5
	 *            要删除的key
	 * @param pns6
	 *            要删除的key
	 * @param pns7
	 *            要删除的key
	 * @param pns8
	 *            要删除的key
	 * @param pns9
	 *            要删除的key
	 */
	public void removeByKey(Object pns0, Object pns1, Object pns2, Object pns3, Object pns4, Object pns5, Object pns6,
			Object pns7, Object pns8, Object pns9) {
		Object[] temp = { pns0, pns1, pns2, pns3, pns4, pns5, pns6, pns7, pns8, pns9 };
		removeByKey(temp);
	}

	/**
	 * 将Map转换为HashMapExt
	 * 
	 * @param map
	 *            map需要转换的Map
	 * @return HashMapExt
	 */
	public static HashMapExt valueOf(Map map) {
		if (map == null) {
			return null;
		}
		if (map instanceof HashMapExt) {
			return (HashMapExt) map;
		} else {
			HashMapExt hashMapExt = new HashMapExt();
			hashMapExt.putAll(map);
			return hashMapExt;
		}
	}
}
