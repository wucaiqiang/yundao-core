package com.yundao.core.threadlocal;

import com.yundao.core.cache.redis.JedisUtils;
import com.yundao.core.threadlocal.filter.RequestAttribute;
import com.yundao.core.threadlocal.filter.RequestCommonParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地线程属性值默认实现类
 *
 * @author wupengfei wupf86@126.com
 */
public class DefaultThreadLocalContext implements ThreadLocalContext {

    private final Map<String, Object> ctx = new HashMap<>();
    private RequestAttribute requestAttribute = null;

    @Override
    public RequestCommonParams getRequestCommonParams() {
        if (requestAttribute != null) {
            return requestAttribute.getRequestCommonParams();
        }
        return null;
    }

    @Override
    public void clearMap() {
        ctx.clear();
    }

    @Override
    public void setMapAttribute(String key, Object value) {
        ctx.put(key, value);
    }

    @Override
    public void setRequestAttribute(RequestAttribute requestAttribute) {
        this.requestAttribute = requestAttribute;
    }

    @Override
    public void setRequestAttribute(String key, Object value) {
        if (requestAttribute != null) {
            requestAttribute.setRequestAttribute(key, value);
        }
    }

    @Override
    public void setSessionAttribute(String key, Object value) {
        if (requestAttribute != null) {
            requestAttribute.setSessionAttribute(key, value);
        }
    }

    @Override
    public void setCacheAttribute(String key, int expireSeconds, Object value) {
        JedisUtils.setObject(key, expireSeconds, value);
    }

    @Override
    public void setListCacheAttribute(String key, String... value) {
        JedisUtils.setList(key, value);
    }

    @Override
    public void removeMapAttribute(String key) {
        ctx.remove(key);
    }

    @Override
    public void removeRequestAttribute(String key) {
        if (requestAttribute != null) {
            requestAttribute.removeRequestAttribute(key);
        }
    }

    @Override
    public void removeSessionAttribute(String key) {
        if (requestAttribute != null) {
            requestAttribute.removeSessionAttribute(key);
        }
    }

    @Override
    public void removeCacheAttribute(String key) {
        JedisUtils.remove(key);
    }

    @Override
    public void removeListCacheAttribute(String key) {
        JedisUtils.remove(key);
    }

    @Override
    public Object getAttribute(String key) {
        Object result = null;
        if (ctx.containsKey(key)) {
            result = ctx.get(key);
        }
        else {
            if (requestAttribute != null) {
                result = requestAttribute.getRequestAttribute(key);
                if (result == null) {
                    result = requestAttribute.getSessionAttribute(key);
                }
            }
        }
        return result;
    }

    @Override
    public Object getMapAttribute(String key) {
        return ctx.get(key);
    }

    @Override
    public Object getRequestAttribute(String key) {
        if (requestAttribute != null) {
            return requestAttribute.getRequestAttribute(key);
        }
        return null;
    }

    @Override
    public Object getSessionAttribute(String key) {
        if (requestAttribute != null) {
            return requestAttribute.getSessionAttribute(key);
        }
        return null;
    }

    @Override
    public Object getCacheAttribute(String key) {
        return JedisUtils.getObject(key);
    }

    @Override
    public List<String> getListCacheAttribute(String key) {
        return JedisUtils.getList(key);
    }

    @Override
    public void invalidateSession() {
        if (requestAttribute != null) {
            requestAttribute.invalidateSession();
        }
    }

}
