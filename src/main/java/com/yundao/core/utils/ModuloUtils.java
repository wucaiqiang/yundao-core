package com.yundao.core.utils;

import com.yundao.core.ed.Md5Utils;

/**
 * 取模工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class ModuloUtils {

    /**
     * 分表取模
     *
     * @param key
     * @param tableNumber
     * @return
     */
    public static int getModulo(String key, int tableNumber) {
        int result = 0;
        if (BooleanUtils.isBlank(key) || tableNumber <= 0) {
            return result;
        }
        key = Md5Utils.md5(key);
        result = Math.abs(key.hashCode()) % tableNumber;
        return result;
    }

}
