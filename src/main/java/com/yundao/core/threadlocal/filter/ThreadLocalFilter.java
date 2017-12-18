package com.yundao.core.threadlocal.filter;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.constant.HeaderConstant;
import com.yundao.core.filter.BaseFilter;
import com.yundao.core.threadlocal.ThreadLocalContext;
import com.yundao.core.threadlocal.ThreadLocalContextHolder;
import com.yundao.core.threadlocal.ThreadLocalUtils;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.ConfigUtils;
import org.slf4j.MDC;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 本地线程过滤器
 *
 * @author wupengfei wupf86@126.com
 */
public class ThreadLocalFilter extends BaseFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            String acaoFrontUrl = ConfigUtils.getValue(CommonConstant.ACAO_FRONT_URL);
            if (!BooleanUtils.isBlank(acaoFrontUrl)) {
                httpResponse.addHeader("Access-Control-Allow-Origin", acaoFrontUrl);
            }

            ThreadLocalContext context = ThreadLocalContextHolder.getThreadLocalContext();
            context.setRequestAttribute(new RequestAttribute(httpRequest));

            // 设置跟踪标识
            RequestCommonParams requestCommonParams = ThreadLocalUtils.getRequestCommonParams();
            String traceIdSuffix = getRequestTraceIdSuffix(httpRequest);
            traceIdSuffix = traceIdSuffix == null ? "" : "-" + traceIdSuffix;
            requestCommonParams.setTraceId(requestCommonParams.getTraceId() + traceIdSuffix);
            MDC.put(HeaderConstant.REQUEST_TRACE_ID, requestCommonParams.getTraceId());

            chain.doFilter(request, response);
        }
        finally {
            ThreadLocalContextHolder.remove();
            MDC.remove(HeaderConstant.REQUEST_TRACE_ID);
        }
    }

    /**
     * 子类可以实现，请求跟踪标识后辍，输出到log中
     *
     * @param httpRequest 请求
     * @return 请求跟踪标识后辍
     */
    protected String getRequestTraceIdSuffix(HttpServletRequest httpRequest) {
        return null;
    }

}
