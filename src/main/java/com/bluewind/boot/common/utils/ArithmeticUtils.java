package com.bluewind.boot.common.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;


/**
 * @author liuxingyu01
 * @date 2021-02-21-14:43
 * @description 提供精确的浮点数运算
 **/
public class ArithmeticUtils extends org.apache.commons.lang3.math.NumberUtils {
    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 这个类不能实例化
     */
    private ArithmeticUtils() {
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.add(b2).doubleValue();
    }


    /**
     * BigDecimal 加法 num1 + num2
     * 未做非空校验
     *
     * @param num1
     * @param num2
     * @return BigDecimal
     */
    public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
        return num1.add(num2);
    }


    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.subtract(b2).doubleValue();
    }


    /**
     * BigDecimal 减法 num1 - num2
     * 未做非空校验
     *
     * @param num1
     * @param num2
     * @return BigDecimal
     */
    public static BigDecimal sub(BigDecimal num1, BigDecimal num2) {
        return num1.subtract(num2);
    }


    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.multiply(b2).doubleValue();
    }


    /**
     * BigDecimal 乘法 num1 * num2
     * 未做非空校验
     *
     * @param num1
     * @param num2
     * @return BigDecimal
     */
    public static BigDecimal mul(BigDecimal num1, BigDecimal num2) {
        return num1.multiply(num2);
    }


    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        if (b1.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.doubleValue();
        }
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * BigDecimal 除法 num1/num2
     * 未做非空校验
     *
     * @param num1
     * @param num2
     * @return BigDecimal
     */
    public static BigDecimal div(BigDecimal num1, BigDecimal num2, int point) {
        return num1.divide(num2, point, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(String.valueOf(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 设置小数点类型为 四舍五入
     *
     * @param num
     * @param point
     * @return BigDecimal
     */
    public static BigDecimal setScale(BigDecimal num, int point) {
        return num.setScale(point, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 格式化双精度，保留两个小数
     *
     * @return
     */
    public static String formatDouble(Double b) {
        BigDecimal bg = new BigDecimal(String.valueOf(b));
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }


    /**
     * Object 转成指定位数的 BigDecimal（四舍五入）
     *
     * @return
     */
    public static BigDecimal toBigDecimal(Object obj, int i) {
        BigDecimal zero = new BigDecimal("0.00");
        BigDecimal tempBigDecimal = zero;
        String str = (obj == null || "".equals(obj)) ? "0.00" : obj.toString();
        tempBigDecimal = new BigDecimal(str);
        tempBigDecimal = tempBigDecimal.setScale(i, BigDecimal.ROUND_HALF_UP);
        return tempBigDecimal;
    }



    /**
     * 判断num是否为空 或者 零
     *
     * @param num
     * @return String
     */
    public static Boolean isEmpty(BigDecimal num) {
        return null == num || equals(BigDecimal.ZERO, num);
    }


    /**
     * 判断num是否 不等于null 并且不等于零
     *
     * @param num
     * @return String
     */
    public static Boolean isNotEmpty(BigDecimal num) {
        return !isEmpty(num);
    }


    /**
     * 比较 num1，num2 返回最大的值
     *
     * @param num1
     * @param num2
     * @return BigDecimal
     */
    public static BigDecimal max(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) > 0 ? num1 : num2;
    }


    /**
     * 比较 num1，num2 返回最小的值
     *
     * @param num1
     * @param num2
     * @return BigDecimal
     */
    public static BigDecimal min(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) < 0 ? num1 : num2;
    }

    /**
     * 转换为万
     *
     * @param num
     * @param point
     * @return
     */
    public static BigDecimal convertTenThousand(BigDecimal num, int point) {
        return num.divide(new BigDecimal(10000), point, RoundingMode.HALF_UP);
    }

    /**
     * 转换为负数
     *
     * @param num
     * @return
     */
    public static BigDecimal convertToMinusNumber(BigDecimal num) {
        if (isLessOrEqual(num, BigDecimal.ZERO)) {
            return num;
        }
        return BigDecimal.ZERO.subtract(num);
    }

    /**
     * 比较 num1 是否大于 num2
     *
     * @param num1
     * @param num2
     * @return boolean
     */
    public static boolean isGreaterThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 1;
    }

    /**
     * 比较 num1 是否大于等于 num2
     *
     * @param num1
     * @param num2
     * @return boolean
     */
    public static boolean isGreaterOrEqual(BigDecimal num1, BigDecimal num2) {
        return isGreaterThan(num1, num2) || equals(num1, num2);
    }

    /**
     * 比较 num1 是否小于 num2
     *
     * @param num1
     * @param num2
     * @return boolean
     */
    public static boolean isLessThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == -1;
    }

    /**
     * 比较 num1 是否小于等于 num2
     *
     * @param num1
     * @param num2
     * @return boolean
     */
    public static boolean isLessOrEqual(BigDecimal num1, BigDecimal num2) {
        return isLessThan(num1, num2) || equals(num1, num2);
    }

    /**
     * 比较 num1 是否等于 num2
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean equals(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 0;
    }


    /**
     * 计算 num1 / num2 的百分比，保存两位小数，并且自带百分号
     *
     * @param num1
     * @param num2
     * @return String  5.26%
     */
    public static String getPercentage(BigDecimal num1, BigDecimal num2) {
        BigDecimal result = num1.divide(num2, 4, RoundingMode.HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        return percent.format(result.doubleValue());
    }


    /**
     * 计算 num1 / num2 的百分比
     *
     * @param num1
     * @param num2
     * @param point 保留几位小数
     * @return String
     */
    public static BigDecimal bigDecimalPercent(BigDecimal num1, BigDecimal num2, int point) {
        if (num1 == null || num2 == null) {
            return BigDecimal.ZERO;
        }
        if (equals(BigDecimal.ZERO, num2)) {
            return BigDecimal.ZERO;
        }
        BigDecimal percent = num1.divide(num2, point + 2, RoundingMode.HALF_UP);
        BigDecimal percent100 = percent.multiply(new BigDecimal(100)).setScale(point);
        return percent100;
    }


    /**
     * 计算 num1 / num2 的百分比
     *
     * @param num1 整数
     * @param num2 整数
     * @param point 保留几位小数
     * @return String
     */
    public static BigDecimal bigDecimalPercent(Integer num1, Integer num2, int point) {
        if (num1 == null || num2 == null) {
            return BigDecimal.ZERO;
        }
        if (num2.equals(0)) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimalNum1 = new BigDecimal(num1);
        BigDecimal bigDecimalNum2 = new BigDecimal(num2);
        return bigDecimalPercent(bigDecimalNum1, bigDecimalNum2, point);
    }


    public static void main(String[] args) {

        BigDecimal b1 = new BigDecimal("2.33");
        BigDecimal b2 = new BigDecimal("44.33");
        System.out.println(getPercentage(b1, b2).toString());


        System.out.println(round(23.828282, 2));
    }


}
