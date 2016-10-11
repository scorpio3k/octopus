package org.scorpio.octopus.utils;

import java.math.BigDecimal;

import org.scorpio.octopus.utils.jdk.JdkBugFixer;

/**
 * 精确的加减乘除运算工具类。
 * 
 */
public class MathExtend {
    // 默认除法运算精度

    private static final int DEFAULT_DIV_SCALE = 20;
    
    public static Double round(Double d, int scale) {
        if (d == null) {
          return null;
        }
        return new Double(round(d.doubleValue(), scale));
      }
    /**
     * 
     * 提供精确的加法运算。
     * 
     * @param v1 加数1
     * @param v2 加数2
     * @return 两个参数的和
     * 
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(JdkBugFixer.formatDouble(v1));
        BigDecimal b2 = new BigDecimal(JdkBugFixer.formatDouble(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 
     * 提供精确的加法运算
     * 
     * @param v1 加数1
     * @param v2 加数2
     * @return 两个参数数学加和，以字符串格式返回
     * 
     */
    public static String add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return JdkBugFixer.formatDouble(b1.add(b2).doubleValue());
    }

    /**
     * 
     * 提供精确的减法运算。
     * 
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     * 
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(JdkBugFixer.formatDouble(v1));
        BigDecimal b2 = new BigDecimal(JdkBugFixer.formatDouble(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 
     * 提供精确的减法运算
     * 
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数数学差，以字符串格式返回
     * 
     */
    public static String subtract(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return JdkBugFixer.formatDouble(b1.subtract(b2).doubleValue());
    }

    /**
     * 
     * 提供精确的乘法运算。
     * 
     * @param v1 乘数1
     * @param v2 乘数2
     * @return 两个参数的积
     * 
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(JdkBugFixer.formatDouble(v1));
        BigDecimal b2 = new BigDecimal(JdkBugFixer.formatDouble(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 
     * 提供精确的乘法运算
     * 
     * @param v1 乘数1
     * @param v2 乘数2
     * @return 两个参数的数学积，以字符串格式返回
     * 
     */
    public static String multiply(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return JdkBugFixer.formatDouble(b1.multiply(b2).doubleValue());
    }

    /**
     * 
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 
     * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
     * 
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     * 
     */
    public static double divide(double v1, double v2) {
        return divide(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 
     * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
     * 
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示需要精确到小数点以后几位。
     * 
     * @return 两个参数的商
     * 
     */
    public static double divide(double v1, double v2, int scale) {
        return divide(v1, v2, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 
     * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     * 
     * @param v1 被除数
     * @param v2 除数 
     * @param scale 表示需要精确到小数点以后几位
     * @param round_mode 表示用户指定的舍入模式 {@link BigDecimal}
     * @return 两个参数的商
     * 
     */
    public static double divide(double v1, double v2, int scale, int round_mode) {
        if (v2 == 0)
            return 0;
        if (scale < 0) {

            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(JdkBugFixer.formatDouble(v1));
        BigDecimal b2 = new BigDecimal(JdkBugFixer.formatDouble(v2));
        return b1.divide(b2, scale, round_mode).doubleValue();
    }

    /**
     * 
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 
     * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
     * @param v1 被除数
     * @param v2 除数 
     * @return 两个参数的商，以字符串格式返回
     * 
     */
    public static String divide(String v1, String v2) {
        return divide(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 
     * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
     * 
     * @param v1 被除数
     * @param v2 除数 
     * @param scale 表示需要精确到小数点以后几位
     * 
     * @return 两个参数的商，以字符串格式返回
     * 
     */
    public static String divide(String v1, String v2, int scale) {
        return divide(v1, v2, DEFAULT_DIV_SCALE, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 
     * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     * 
     * @param v1 被除数
     * @param v2 除数 
     * @param scale 表示需要精确到小数点以后几位
     * @param round_mode 表示用户指定的舍入模式
     * @return 两个参数的商，以字符串格式返回
     * 
     */
    public static String divide(String v1, String v2, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return JdkBugFixer.formatDouble(b1.divide(b2, scale, round_mode).doubleValue());
    }

    /**
     * 
     * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
     * 
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     * 
     */
    public static double round(double v, int scale) {
        return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 
     * 提供精确的小数位四舍五入处理
     * 
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @param round_mode 指定的舍入模式
     * @return 四舍五入后的结果
     * 
     */
    public static double round(double v, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(JdkBugFixer.formatDouble(v));

        return b.setScale(scale, round_mode).doubleValue();

    }

    /**
     * 
     * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
     * 
     * @param v
     *            需要四舍五入的数字
     * 
     * @param scale
     *            小数点后保留几位
     * 
     * @return 四舍五入后的结果，以字符串格式返回
     * 
     */
    public static String round(String v, int scale) {
        return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 
     * 提供精确的小数位四舍五入处理
     * 
     * @param v
     *            需要四舍五入的数字
     * 
     * @param scale
     *            小数点后保留几位
     * 
     * @param round_mode
     *            指定的舍入模式
     * 
     * @return 四舍五入后的结果，以字符串格式返回
     * 
     */
    public static String round(String v, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(v);
        return JdkBugFixer.formatDouble(b.setScale(scale, round_mode).doubleValue());

    }

    public static Double add(Double a, Double b) {
        if (a == null && b == null)
            return null;
        double x = a == null ? 0 : a.doubleValue();
        double y = b == null ? 0 : b.doubleValue();
        return new Double(add(x, y));
    }

    public static Double subtract(Double a, Double b) {
        if (a == null && b == null)
            return null;
        double x = a == null ? 0 : a.doubleValue();
        double y = b == null ? 0 : b.doubleValue();
        return new Double(subtract(x, y));
    }

    public static boolean isPlus(Double d) {
        if (d == null)
            return false;
        return d.doubleValue() > 0;
    }

    public static Double multiply(Double a, Double b) {
        if (a == null && b == null)
            return null;
        double x = a == null ? 0 : a.doubleValue();
        double y = b == null ? 0 : b.doubleValue();
        return new Double(multiply(x, y));
    }

    public static Double divide(Double a, Double b) {
        if (a == null && b == null)
            return new Double(0);
        double x = a == null ? 0 : a.doubleValue();
        double y = b == null ? 0 : b.doubleValue();
        if (x == 0 || y == 0)
            return new Double(0);
        return new Double(divide(x, y));
    }

    public static Double sum(Double[] values) {
        Double result = new Double(0);
        for (int i = 0; i < values.length; i++) {
            result = add(result, values[i]);
        }
        return result;
    }

    public static Integer add(Integer a, Integer b) {
        int x = a == null ? 0 : a.intValue();
        int y = b == null ? 0 : b.intValue();
        return new Integer(x + y);
    }

    /**
     * 对指定数取反
     * 
     * @param a 指定数 
     * @return a的反
     */
    public static Double reverse(Double a) {
        return subtract(new Double(0), a);
    }
    /**
     * 两个long值相加
     * @param v1 加数1
     * @param v2 加数2
     * @return v1,v2的和
     */
    public static Long add(Long v1, Long v2) {
        if (v1 == null && v2 == null)
            return null;
        long x = v1 == null ? 0 : v1.longValue();
        long y = v2 == null ? 0 : v2.longValue();
        return new Long(x+y);
    }

    /**
     * Returns the absolute value of a <code>Double</code> value.
     * @param   amt   the argument whose absolute value is to be determined
     * @return  the absolute value of the argument.null if argument is null
     */
    public static Double abs(Double amt) {
        if (amt == null)
            return null;
        return new Double(Math.abs(amt.doubleValue()));
    }
}
