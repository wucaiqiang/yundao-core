package com.yundao.core.interceptor.spring;

import com.yundao.core.log.Log;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.constant.StaticValue;
import com.yundao.core.log.LogFactory;
import com.yundao.core.threadlocal.ThreadLocalUtils;
import com.yundao.core.threadlocal.filter.RequestCommonParams;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.CookieUtils;
import com.yundao.core.utils.EDUtils;
import com.yundao.core.utils.RequestUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring通用拦截器
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public class SpringCommonInterceptor extends AbstractSpringInterceptor {

	private static Log log = LogFactory.getLog(SpringCommonInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		RequestCommonParams requestCommonParams = ThreadLocalUtils.getRequestCommonParams();
		String url = RequestUtils.getRequestUrl(request);
		String ip = requestCommonParams.getIp();
		String systemId = requestCommonParams.getId();

		// 获取用户的登录信息
		String userId = CookieUtils.getCookie(request, CommonConstant.USERID);
		if (!BooleanUtils.isBlank(userId)) {
			String mechanismId = CookieUtils.getCookie(request, CommonConstant.MECHANISM_ID);
			String organizationId = CookieUtils.getCookie(request, CommonConstant.ORGANIZATION_ID);
			String username = CookieUtils.getCookie(request, CommonConstant.MOBILE_USERNAME);
			String ticket = CookieUtils.getCookie(request, CommonConstant.TICKET);
			String realName = CookieUtils.getCookie(request, CommonConstant.REAL_NAME);
			request.setAttribute(CommonConstant.MECHANISM_ID, mechanismId);
			request.setAttribute(CommonConstant.ORGANIZATION_ID, organizationId);
			request.setAttribute(CommonConstant.USERID, userId);
			request.setAttribute(CommonConstant.MOBILE_USERNAME, username);
			request.setAttribute(CommonConstant.REAL_NAME, EDUtils.decode(realName));
			request.setAttribute(CommonConstant.TICKET, ticket);
		}

		request.setAttribute(CommonConstant.CONTEXT_PATH, StaticValue.contextPath);
		request.setAttribute(CommonConstant.IP, ip);
		request.setAttribute(CommonConstant.BEGIN_TIME, System.currentTimeMillis());
		log.info("ip=" + ip + ",url=" + url + ",systemId=" + systemId + ",userId=" + userId);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long now = System.currentTimeMillis();
		long consumeTime = now - (Long) request.getAttribute(CommonConstant.BEGIN_TIME);
		log.info("now=" + now + ",consumeTime=" + consumeTime);
	}

}
