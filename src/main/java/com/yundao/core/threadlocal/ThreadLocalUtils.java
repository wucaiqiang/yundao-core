package com.yundao.core.threadlocal;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.threadlocal.filter.RequestCommonParams;

import java.util.List;

/**
 * 本地线程工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class ThreadLocalUtils {

    /**
     * 获取请求时的共用参数
     *
     * @return
     */
    public static RequestCommonParams getRequestCommonParams() {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        return context.getRequestCommonParams();
    }

    /**
     * 添加属性到map
     *
     * @param key
     * @param value
     */
    public static void addToMap(String key, Object value) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.setMapAttribute(key, value);
    }

    /**
     * 添加属性到request
     *
     * @param key
     * @param value
     */
    public static void addToRequest(String key, Object value) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.setRequestAttribute(key, value);
    }

    /**
     * 添加属性到session
     *
     * @param key
     * @param value
     */
    public static void addToSession(String key, Object value) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.setSessionAttribute(key, value);
    }

    /**
     * 添加属性到缓存中
     *
     * @param key
     * @param value
     */
    public static void addToCache(String key, Object value) {
        addToCache(key, CommonConstant.NEGATIVE_ONE, value);
    }

    /**
     * 添加属性到缓存中
     *
     * @param key
     * @param expireSeconds
     * @param value
     */
    public static void addToCache(String key, int expireSeconds, Object value) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.setCacheAttribute(key, expireSeconds, value);
    }

    /**
     * 添加属性到缓存中
     *
     * @param key
     * @param value
     */
    public static void addToListCache(String key, String... value) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.setListCacheAttribute(key, value);
    }


    /**
     * 从map中获取属性值
     *
     * @param key
     * @return
     */
    public static Object getFromMap(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        return context.getMapAttribute(key);
    }

    /**
     * 从request中获取属性值
     *
     * @param key
     * @return
     */
    public static Object getFromRequest(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        return context.getRequestAttribute(key);
    }

    /**
     * 从session中获取属性值
     *
     * @param key
     * @return
     */
    public static Object getFromSession(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        return context.getSessionAttribute(key);
    }

    /**
     * 从缓存中获取属性值
     *
     * @param key
     * @return
     */
    public static Object getFromCache(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        return context.getCacheAttribute(key);
    }

    /**
     * 从redis中的列表集合取数据
     *
     * @param key
     * @return
     */
    public static List<String> getFromListCache(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        return context.getListCacheAttribute(key);
    }

    /**
     * 依次从map、request、session中获取属性值
     *
     * @param key
     * @return
     */
    public static Object getAttribute(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        return context.getAttribute(key);
    }

    /**
     * 从map中删除属性
     *
     * @param key
     */
    public static void removeFromMap(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.removeMapAttribute(key);
    }

    /**
     * 从request中删除属性
     *
     * @param key
     */
    public static void removeFromRequest(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.removeRequestAttribute(key);
    }

    /**
     * 从session中删除属性
     *
     * @param key
     */
    public static void removeFromSession(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.removeSessionAttribute(key);
    }

    /**
     * 从缓存中删除属性
     *
     * @param key
     */
    public static void removeFromCache(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.removeCacheAttribute(key);
    }

    /**
     * 移除redis中的指定key
     *
     * @param key
     */
    public static void removeListCache(String key) {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.removeListCacheAttribute(key);
    }

    /**
     * 使session失效
     */
    public static void invalidateSession() {
        ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
        context.invalidateSession();
    }

    /**
     * 获取客户端ip
     *
     * @return 客户端ip
     */
    public static String getIp() {
        RequestCommonParams requestCommonParams = getRequestCommonParams();
        return requestCommonParams != null ? requestCommonParams.getIp() : null;
    }

}
