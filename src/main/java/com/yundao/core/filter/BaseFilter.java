package com.yundao.core.filter;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.filter.http.BaseHttpServletRequestWrapper;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.RequestUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 基类过滤器
 *
 * @author wupengfei wupf86@126.com
 */
public class BaseFilter implements Filter {

    private String[] excludeUrls = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String name = "excludeUrl";
        String excludeUrl = filterConfig.getInitParameter(name);
        String publicUrl = filterConfig.getServletContext().getInitParameter(name);
        if (publicUrl != null) {
            excludeUrl = (excludeUrl == null) ? publicUrl : excludeUrl + "," + publicUrl;
        }

        if (!BooleanUtils.isBlank(excludeUrl)) {
            excludeUrls = excludeUrl.split(CommonConstant.COMMA_SEMICOLON);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        // 排除某些链接
        boolean isMatch = this.isMatchExcludeUrl(servletRequest);
        if (isMatch) {
            chain.doFilter(request, response);
            return;
        }

        BaseHttpServletRequestWrapper requestWrapper = new BaseHttpServletRequestWrapper(servletRequest);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }

    /**
     * 获取要排除的链接
     *
     * @return
     */
    protected String[] getExcludeUrls() {
        return excludeUrls;
    }

    /**
     * 是否匹配排除链接
     *
     * @param servletRequest
     * @return
     */
    protected boolean isMatchExcludeUrl(HttpServletRequest servletRequest) {
        String[] excludeUrls = this.getExcludeUrls();
        if (BooleanUtils.isEmpty(excludeUrls)) {
            return false;
        }
        List<String> excludeUrlList = Arrays.asList(excludeUrls);
        return RequestUtils.isExcludeUrl(servletRequest, excludeUrlList);
    }

}
