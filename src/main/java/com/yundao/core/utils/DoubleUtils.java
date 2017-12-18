package com.yundao.core.utils;

import java.math.BigDecimal;

/**
 * 浮点数工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class DoubleUtils {

    /**
     * 相加
     *
     * @param value1
     * @param value2
     * @return
     */
    public static Double add(Double value1, Double value2) {
        if (value1 == null || value2 == null) {
            return null;
        }
        BigDecimal bd = new BigDecimal(value1).add(new BigDecimal(value2));
        return BigDecimalUtils.upFormat(bd).doubleValue();
    }


    /**
     * 相乘
     *
     * @param value1 参数1
     * @param value2 参数2
     * @return 相乘结果并四舍五入保存两位小数
     */
    public static Double multiply(Double value1, Double value2) {
        if (value1 == null || value2 == null) {
            return null;
        }
        BigDecimal bd = new BigDecimal(value1).multiply(new BigDecimal(value2));
        return BigDecimalUtils.upFormat(bd).doubleValue();
    }

    /**
     * 格式化
     *
     * @param value
     * @return
     */
    public static Double formatDouble(Double value) {
        if (value == null) {
            return null;
        }
        return BigDecimalUtils.upFormat(new BigDecimal(value)).doubleValue();
    }

}