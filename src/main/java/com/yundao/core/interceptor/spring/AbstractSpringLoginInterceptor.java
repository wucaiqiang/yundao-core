package com.yundao.core.interceptor.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring登录拦截器
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class AbstractSpringLoginInterceptor extends AbstractSpringInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object userSession = this.getSession(request);
		return (userSession != null && this.afterLoginValidate(response, userSession));
	}

	/**
	 * 获取用户登录后的会话
	 * 
	 * @param request
	 * @return
	 */
	public abstract Object getSession(HttpServletRequest request);

	/**
	 * 登录后的验证
	 * 
	 * @param response
	 * @param userSession
	 * @return
	 */
	public boolean afterLoginValidate(HttpServletResponse response, Object userSession) {
		return true;
	}

}
