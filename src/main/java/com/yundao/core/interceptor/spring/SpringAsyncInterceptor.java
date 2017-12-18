package com.yundao.core.interceptor.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yundao.core.threadlocal.filter.RequestCommonParams;
import com.yundao.core.utils.RequestQueueUtils;

/**
 * spring异步拦截器
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public class SpringAsyncInterceptor extends AbstractSpringInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		RequestCommonParams params = RequestCommonParams.get(request);
		if (params.isAsync()) {
			RequestQueueUtils.Request requestQueue = RequestQueueUtils.Request.newDefault(request);
			RequestQueueUtils.offer(requestQueue);
			return false;
		}
		return true;
	}

}
