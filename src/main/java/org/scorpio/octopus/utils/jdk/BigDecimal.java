package org.scorpio.octopus.utils.jdk;

import java.math.BigInteger;


/**
 *<p>对java.math.BigDecimal中的bug修复后的BigDecimal类。</p>
 *<pre>
 	public static void main(String[] args) {
		double amt=0.06;
		System.out.println("OK:"+amt);
		System.out.println("OK:"+new BigDecimal("0.06"));
		System.out.println("BUG:"+new BigDecimal(amt));
		System.out.println("FIX:"+new com.nstc.util.BigDecimal(amt));
	}
	输出结果：
	OK:0.06
	OK:0.06
	BUG:0.059999999999999997779553950749686919152736663818359375
	FIX:0.06
 *</pre>
 *
 */
public class BigDecimal extends java.math.BigDecimal{

	public BigDecimal(String val) {
		super(val);
	}

	public BigDecimal(double val) {
		super(JdkBugFixer.formatDouble(val));
	}

	public BigDecimal(BigInteger unscaledVal, int scale) {
		super(unscaledVal, scale);
	}

	public BigDecimal(BigInteger val) {
		super(val);
	}
}
