package com.yundao.core.utils;

import com.yundao.core.constant.CommonConstant;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编解码工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class EDUtils {

    /**
     * 编码
     *
     * @param value
     * @return
     */
    public static String encode(String value) {
        return encode(value, null);
    }

    /**
     * 编码
     *
     * @param value
     * @param charset
     * @return
     */
    public static String encode(String value, String charset) {
        if (BooleanUtils.isBlank(value)) {
            return null;
        }

        if (BooleanUtils.isBlank(charset)) {
            charset = CommonConstant.UTF_8;
        }
        try {
            return URLEncoder.encode(value, charset);
        }
        catch (Exception e) {
            return value;
        }
    }

    /**
     * 解码
     *
     * @param value
     * @return
     */
    public static String decode(String value) {
        return decode(value, null);
    }

    /**
     * 解码
     *
     * @param value
     * @param charset
     * @return
     */
    public static String decode(String value, String charset) {
        if (BooleanUtils.isBlank(value)) {
            return null;
        }
        if (BooleanUtils.isBlank(charset)) {
            charset = CommonConstant.UTF_8;
        }
        try {
            return URLDecoder.decode(value, charset);
        }
        catch (Exception e) {
            return value;
        }
    }

}
