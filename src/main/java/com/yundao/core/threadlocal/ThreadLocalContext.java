package com.yundao.core.threadlocal;

import com.yundao.core.threadlocal.filter.RequestAttribute;
import com.yundao.core.threadlocal.filter.RequestCommonParams;

import java.util.List;

/**
 * 本地线程属性值接口
 *
 * @author wupengfei wupf86@126.com
 */
public interface ThreadLocalContext {

    /**
     * 获取请求时的共用参数
     *
     * @return
     */
    RequestCommonParams getRequestCommonParams();

    /**
     * 清除map中的属性
     */
    void clearMap();

    /**
     * 往map里设置属性
     *
     * @param key
     * @param value
     */
    void setMapAttribute(String key, Object value);

    /**
     * 设置属性对象
     *
     * @param requestAttribute
     */
    void setRequestAttribute(RequestAttribute requestAttribute);

    /**
     * 往request里设置属性
     *
     * @param key
     * @param value
     */
    void setRequestAttribute(String key, Object value);

    /**
     * 往session中设置属性
     *
     * @param key
     * @param value
     */
    void setSessionAttribute(String key, Object value);

    /**
     * 往缓存中设置属性
     *
     * @param key
     * @param value
     */
    void setCacheAttribute(String key, int expireSeconds, Object value);

    /**
     * 往redis的列表集合添加数据
     *
     * @param key
     * @param value
     */
    void setListCacheAttribute(String key, String... value);

    /**
     * 删除map中的属性
     *
     * @param key
     */
    void removeMapAttribute(String key);

    /**
     * 删除request中的属性
     *
     * @param key
     */
    void removeRequestAttribute(String key);

    /**
     * 删除session中的属性
     *
     * @param key
     */
    void removeSessionAttribute(String key);


    /**
     * 删除缓存中的属性
     *
     * @param key
     */
    void removeCacheAttribute(String key);

    /**
     * 移除redis中的指定key
     *
     * @param key
     */
    void removeListCacheAttribute(String key);

    /**
     * 依次从map、request、session中获取属性值
     *
     * @param key
     * @return
     */
    Object getAttribute(String key);

    /**
     * 从map中获取属性值
     *
     * @param key
     * @return
     */
    Object getMapAttribute(String key);

    /**
     * 从request中获取属性值
     *
     * @param key
     * @return
     */
    Object getRequestAttribute(String key);


    /**
     * 从session中获取属性值
     *
     * @param key
     * @return
     */
    Object getSessionAttribute(String key);

    /**
     * 从缓存中获取属性值
     *
     * @param key
     * @return
     */
    Object getCacheAttribute(String key);

    /**
     * 从redis中的列表集合取数据
     *
     * @param key
     * @return
     */
    List<String> getListCacheAttribute(String key);

    /**
     * 使session失效
     */
    void invalidateSession();


}