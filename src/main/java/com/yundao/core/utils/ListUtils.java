package com.yundao.core.utils;

import java.util.List;

/**
 * list集合工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class ListUtils {

    /**
     * 获取集合的长度，若为空则返回0
     *
     * @param list 集合
     * @return 集合的长度
     */
    public static int getSize(List<?> list) {
        return list != null ? list.size() : 0;
    }

}
