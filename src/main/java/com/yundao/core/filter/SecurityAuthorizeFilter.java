package com.yundao.core.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.yundao.core.base.service.BaseService;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.CookieUtils;

/**
 * 安全授权过滤器
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SecurityAuthorizeFilter extends BaseFilter {

	private static Log log = LogFactory.getLog(SecurityAuthorizeFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;

		// 排除某些链接
		String url = servletRequest.getRequestURI();
		boolean isMatch = this.isMatchExcludeUrl(servletRequest);
		if (isMatch) {
			chain.doFilter(request, response);
			return;
		}

		try {
			String userId = CookieUtils.getCookie(servletRequest, CommonConstant.USERID);
			BaseService service = BaseService.getBaseService();
			Map<String, Boolean> authorizeMap = service.getAuthorizeUrl(userId, url);

			// 执行链接校验
			int urlLength = url.length() - 1;
			boolean isEndSeparator = url != null && url.charAt(urlLength) == '/';
			if (!authorizeMap.containsKey(url)
					|| (isEndSeparator && !authorizeMap.containsKey(url.substring(0, urlLength)))
					|| (!isEndSeparator && !authorizeMap.containsKey(url + "/"))) {
				log.info("用户校验链接失败userId=" + userId + ",url=" + url);
				return;
			}
		}
		catch (Exception e) {
			log.error("校验用户链接时异常", e);
			return;
		}
		chain.doFilter(request, response);
	}

}
