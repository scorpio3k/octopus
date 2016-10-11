package org.scorpio.octopus.utils;

/**
 * 用于批量类型转换的映射值对象，配合CastUtils使用。
 * <p>
 * 批量转换HashMap中value类型的辅助工具类,配合CastUtil使用,因为转换的代码会比较长,类名只有两个字母方便使用.
 * </p>
 * 
 * @see CastUtil
 *
 */
public class HM {
	private String oldKey;
	private String newKey;
	private Class clazz;

	public HM(String oldKey) {
		super();
		this.oldKey = oldKey;
	}

	public HM(String oldKey, Class clazz) {
		super();
		this.oldKey = oldKey;
		this.clazz = clazz;
	}

	public HM(String oldKey, String newKey) {
		super();
		this.oldKey = oldKey;
		this.newKey = newKey;
	}

	/**
	 * @param oldKey
	 *            要替换的key
	 * @param newKey
	 *            替换后的key
	 * @param clazz
	 *            value转换后的类型
	 */
	public HM(String oldKey, String newKey, Class clazz) {
		super();
		this.oldKey = oldKey;
		this.newKey = newKey;
		this.clazz = clazz;
	}

	public String getOldKey() {
		return oldKey;
	}

	public void setOldKey(String oldKey) {
		this.oldKey = oldKey.trim();
	}

	public String getNewKey() {
		return newKey;
	}

	public void setNewKey(String newKey) {
		this.newKey = newKey.trim();
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
}
