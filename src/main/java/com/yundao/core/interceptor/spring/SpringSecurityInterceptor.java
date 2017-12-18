package com.yundao.core.interceptor.spring;

import com.yundao.core.log.Log;
import com.yundao.core.threadlocal.filter.RequestCommonParamsValidate;
import com.yundao.core.utils.RequestUtils;
import com.yundao.core.log.LogFactory;
import com.yundao.core.threadlocal.ThreadLocalUtils;
import com.yundao.core.threadlocal.filter.RequestCommonParams;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * spring安全拦截器
 *
 * @author wupengfei wupf86@126.com
 */
public class SpringSecurityInterceptor extends AbstractSpringInterceptor {

    private static Log log = LogFactory.getLog(SpringSecurityInterceptor.class);

    private List<String> excludeUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 过滤不拦截的链接
        boolean isExclude = RequestUtils.isExcludeUrl(request, excludeUrls);
        if (isExclude) {
            log.info("过滤不拦截的链接url=" + request.getRequestURI());
            return true;
        }

        // 获取请求时的共用参数
        RequestCommonParams commonParams = ThreadLocalUtils.getRequestCommonParams();
        if (commonParams == null) {
            commonParams = RequestCommonParams.get(request);
        }
        RequestCommonParamsValidate.validate(commonParams);
        return true;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
