package org.scorpio.octopus.utils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 获取系统信息。
 * 
 * @author 李文锴
 * 
 */
public class OSUtil {

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return "[Current.Time]=" + TextFormat.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss.SSS");
	}

	/**
	 * 获取CPU个数
	 * 
	 * @return
	 */
	public static String getAvailableProcessors() {
		Integer count = new Integer(1);
		try {
			Runtime rt = Runtime.getRuntime();
			Method m = rt.getClass().getMethod("availableProcessors", new Class[0]);
			count = (Integer) m.invoke(rt, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[CUP.Count]=" + count;
	}

	/**
	 * 获取线程个数
	 * 
	 * @return
	 */
	public static String getThreadCount() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		while (group.getParent() != null) {
			group = group.getParent();
		}
		return "[Thread.Count]=" + group.activeCount();
	}

	/**
	 * 获取线程组个数
	 * 
	 * @return
	 */
	public static String getRootThreadGroupCount() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		while (group.getParent() != null) {
			group = group.getParent();
		}
		return "[ThreadGroup.Count]=" + group.activeGroupCount();
	}

	/**
	 * 获取操作系统
	 * 
	 * @return
	 */
	public static String getSysInfo() {
		return "[System.INFO]=" + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version");
	}

	/**
	 * 获取JavaHome路径
	 * 
	 * @return
	 */
	public static String getJavaHome() {
		return "[JAVA.HOME]=" + System.getProperty("java.home");
	}

	/**
	 * 获取虚拟机的名称
	 * 
	 * @return
	 */
	public static String getJVMInfo() {
		return  "[JVM.INFO]=" + System.getProperty("java.vm.name") + "-" + System.getProperty("java.vm.version") + ":" + System.getProperty("sun.arch.data.model") + "bit" + "(" + System.getProperty("java.vm.vendor") + ")";
	}

	/**
	 * 获取JAVA的版本
	 * 
	 * @return
	 */
	public static String getJAVAInfo() {
		return "[JAVA.INFO]=" + System.getProperty("java.version") + "(" + System.getProperty("java.vendor") + ")";
	}

	/**
	 * 获取主机名称
	 * 
	 * @return
	 */
	public static String getHostName() {
		String hostName = null;
		try {
			hostName = java.net.InetAddress.getLocalHost().getHostName();
		} catch (java.net.UnknownHostException e) {
			hostName = "<unknown>";
		}
		return "[Host.Name]=" + hostName;
	}

	/**
	 * 获取主机IP地址
	 * 
	 * @return
	 */
	public static String getHostIP() {
		String hostAddress = null;
		try {
			hostAddress = java.net.InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException e) {
			hostAddress = "<unknown>";
		}
		return "[Host.IP]=" + hostAddress;
	}

	/**
	 * 获取最大可用内存
	 * 
	 * @return
	 */
	public static String getMaxMemory() {
		Long r = new Long(1);
		try {
			Runtime rt = Runtime.getRuntime();
			Method m = rt.getClass().getMethod("maxMemory", new Class[0]);
			r = (Long) m.invoke(rt, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[Memory.Max]=" + r + "M";
	}

	/**
	 * 获取当前使用内存
	 * 
	 * @return
	 */
	public static String getUsedMemory() {
		return "[Memory.Used]=" + (new Long(Runtime.getRuntime().totalMemory()).longValue() / 1048576L) + "M";
	}

	/**
	 * 获取剩余内存
	 * 
	 * @return
	 */
	public static String getRemnantMemory() {
		return "[Memory.Remnant]=" + (new Long(Runtime.getRuntime().freeMemory()).longValue() / 1048576L) + "M";
	}
}