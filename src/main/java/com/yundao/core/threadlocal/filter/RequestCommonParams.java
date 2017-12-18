package com.yundao.core.threadlocal.filter;

import com.google.common.collect.Maps;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.threadlocal.ThreadLocalUtils;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.RequestUtils;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.constant.HeaderConstant;
import com.yundao.core.ed.EDConstant;
import com.yundao.core.utils.ConfigUtils;
import com.yundao.core.utils.MapUtils;
import com.yundao.core.utils.UUIDUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 请求时的共用参数
 *
 * @author wupengfei wupf86@126.com
 */
public class RequestCommonParams {

    private static Log log = LogFactory.getLog(RequestCommonParams.class);

    /**
     * 调用是否异步
     */
    private boolean isAsync = true;

    /**
     * 跟踪标识
     */
    private String traceId;

    /**
     * 版本号
     */
    private String version;

    /**
     * 系统标识
     */
    private String id;

    /**
     * 请求的时间
     */
    private Long time;

    /**
     * 加密类型
     */
    private String signType;

    /**
     * 加密值
     */
    private String sign;

    /**
     * 随机数
     */
    private String nonce;

    /**
     * 请求的参数
     */
    private String queryString;

    /**
     * 请求的方法
     */
    private String method;

    /**
     * ip
     */
    private String ip;
    /**
     * 应用类型，例如:pc或app
     */
    private String appType;

    /**
     * 方法参数
     */
    private Map<String, Object> methodParams = Maps.newHashMap();

    /**
     * 头部参数
     */
    private Map<String, String> headerParams = Maps.newHashMap();

    private RequestCommonParams() {

    }

    /**
     * 新建默认请求参数对象
     *
     * @return
     */
    public static RequestCommonParams newDefault() {
        return newDefault(null);
    }

    /**
     * 新建默认请求参数对象
     *
     * @param methodParams
     * @return
     */
    public static RequestCommonParams newDefault(Map<String, String> methodParams) {
        RequestCommonParams result = new RequestCommonParams();
        result.setId(ConfigUtils.getValue(CommonConstant.ID));
        result.setVersion(ConfigUtils.getValue(CommonConstant.VERSION));
        result.setTime(System.currentTimeMillis());
        result.setSignType(EDConstant.MD5);
        result.setMethodParams(MapUtils.cast(methodParams));

        // 设置跟踪标识
        RequestCommonParams requestCommonParams = ThreadLocalUtils.getRequestCommonParams();
        if (requestCommonParams != null) {
            log.info("当前ip=" + requestCommonParams.getIp());
            result.setTraceId(requestCommonParams.getTraceId());
            result.setIp(requestCommonParams.getIp());
        }
        else {
            result.setTraceId(methodParams != null ? methodParams.get(HeaderConstant.REQUEST_TRACE_ID) : null);
        }
        // result.setNonce(UUIDUtils.getUUID());
        return result;
    }

    /**
     * 获取请求参数对象
     *
     * @param request
     * @return
     */
    public static RequestCommonParams get(HttpServletRequest request) {
        RequestCommonParams result = null;
        if (request != null) {
            result = new RequestCommonParams();

            // 从头部或参数中获取
            String isAsync = getValueFromHeaderOrParameter(request, HeaderConstant.REQUEST_IS_ASYNC);
            String traceId = getValueFromHeaderOrParameter(request, HeaderConstant.REQUEST_TRACE_ID);
            String version = getValueFromHeaderOrParameter(request, HeaderConstant.REQUEST_VERSION);
            String time = getValueFromHeaderOrParameter(request, HeaderConstant.REQUEST_TIME);
            String signType = getValueFromHeaderOrParameter(request, HeaderConstant.REQUEST_SIGN_TYPE);
            String sign = getValueFromHeaderOrParameter(request, HeaderConstant.REQUEST_SIGN);
            String requestId = getValueFromHeaderOrParameter(request, HeaderConstant.REQUEST_ID);
            String nonce = getValueFromHeaderOrParameter(request, HeaderConstant.REQUEST_NONCE);
            String appType = getValueFromHeaderOrParameter(request, HeaderConstant.HEADER_APP_TYPE);//应用类型，例如:pc或app

            // 获取Ip
            String ip = getValueFromHeaderOrParameter(request, HeaderConstant.REQUEST_IP);
            if (BooleanUtils.isBlank(ip)) {
                log.info("头部或参数中的ip为空");
                ip = RequestUtils.getIp(request);
            }
            log.info("从请求中获取ip=" + ip);

            // 删除加密值
            Map<String, String> methodParams = RequestUtils.getParameterMap(request);
            if (methodParams.containsKey(CommonConstant.SIGN)) {
                methodParams.remove(CommonConstant.SIGN);
            }
            result.setAsync(isAsync == null || org.apache.commons.lang.BooleanUtils.toBoolean(isAsync));
            result.setTraceId(traceId);
            result.setVersion(version);
            result.setId(requestId);
            result.setTime(NumberUtils.toLong(time));
            result.setSignType(signType);
            result.setSign(sign);
            result.setNonce(nonce);
            result.setMethodParams(MapUtils.cast(methodParams));
            result.setQueryString(request.getQueryString());
            result.setMethod(request.getMethod());
            result.setIp(ip);
            result.setAppType(appType);
        }
        return result;
    }

    private static String getValueFromHeaderOrParameter(HttpServletRequest request, String key) {
        String result = request.getHeader(key);
        if (result == null) {
            result = request.getParameter(key);
        }
        return result;
    }

    /**
     * 添加方法参数
     *
     * @param key
     * @param value
     */
    public void addMethodParams(String key, Object value) {
        if (value == null) {
            value = "";
        }
        methodParams.put(key, value);
    }

    /**
     * 添加头部参数
     *
     * @param key   键
     * @param value 值
     */
    public void addHeaderParams(String key, String value) {
        headerParams.put(key, value);
    }

    public boolean isAsync() {
        return isAsync;
    }

    public void setAsync(boolean isAsync) {
        this.isAsync = isAsync;
    }

    public String getTraceId() {
        if (BooleanUtils.isBlank(traceId)) {
            traceId = UUIDUtils.getUUID();
        }
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Map<String, Object> getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(Map<String, Object> methodParams) {
        if (methodParams != null) {
            for (Map.Entry<String, Object> entry : methodParams.entrySet()) {
                if (entry.getValue() == null) {
                    methodParams.put(entry.getKey(), "");
                }
            }
        }
        this.methodParams = methodParams;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

	public String getAppType() {
	
		return appType;
	}

	public void setAppType(String appType) {
	
		this.appType = appType;
	}
    
}
