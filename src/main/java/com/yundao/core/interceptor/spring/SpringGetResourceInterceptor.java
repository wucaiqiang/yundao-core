package com.yundao.core.interceptor.spring;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yundao.core.base.service.BaseService;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.threadlocal.ThreadLocalUtils;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.RequestUtils;
import com.yundao.core.utils.ConfigUtils;

/**
 * spring获取资源拦截器
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public class SpringGetResourceInterceptor extends AbstractSpringInterceptor {

	private static Log log = LogFactory.getLog(SpringGetResourceInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean isAjax = RequestUtils.isAjax(request);
		if (isAjax) {
			return true;
		}
		try {
			// 获取系统编码
			String systemCode = ConfigUtils.getValue(CommonConstant.ID);
			if (BooleanUtils.isBlank(systemCode)) {
				systemCode = ThreadLocalUtils.getRequestCommonParams().getId();
			}

			// 获取用户id
			String userId = (String) request.getAttribute(CommonConstant.USERID);

			// 去UBS获取子资源链接的权限
			String uri = request.getRequestURI();

			BaseService service = BaseService.getBaseService();
			Map<String, Boolean> authorizeMap = service.getAuthorizeChildrenUrl(systemCode, userId, uri);

			log.info("获取校验子资源结果authorizeMap=" + authorizeMap);
			request.setAttribute(CommonConstant.AUTHORIZE_MAP, authorizeMap);
		}
		catch (Exception e) {
			log.error("获取子资源时异常", e);
		}
		return true;
	}

}
