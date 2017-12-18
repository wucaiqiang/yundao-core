package com.yundao.core.filter;

import com.yundao.core.base.service.BaseService;
import com.yundao.core.code.Result;
import com.yundao.core.code.config.CommonCode;
import com.yundao.core.config.common.CommonConfigEnum;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.ed.Md5Utils;
import com.yundao.core.json.jackson.type.BaseTypeReference;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.ConfigUtils;
import com.yundao.core.utils.CookieUtils;
import com.yundao.core.utils.FileUtils;
import com.yundao.core.utils.JsonUtils;
import com.yundao.core.utils.RequestUtils;
import com.yundao.core.utils.ResponseUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录过滤器
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class LoginFilter extends BaseFilter {

	private static Log log = LogFactory.getLog(LoginFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;

		// 排除某些链接
		boolean isMatch = this.isMatchExcludeUrl(servletRequest);
		if (isMatch) {
			chain.doFilter(request, response);
			return;
		}

		// 校验用户是否登录
		String loginUrl = ConfigUtils.getValue(CommonConstant.LOGIN_URL);
		if (BooleanUtils.isBlank(loginUrl)) {
			log.error("登录链接没有设置");
			return;
		}
		String userId = CookieUtils.getCookie(servletRequest, CommonConstant.USERID);
		String ticket = CookieUtils.getCookie(servletRequest, CommonConstant.TICKET);
		if (BooleanUtils.isBlank(userId) || BooleanUtils.isBlank(ticket)) {
			log.info("跳转到登录页userId=" + userId + ",ticket=" + ticket);
			this.responseToUser(servletRequest, servletResponse);
			return;
		}

		try {
			// 从cookie中获取是否校验
			long cookieTime = NumberUtils.toLong(CookieUtils.getCookie(servletRequest, CommonConstant.COOKIE_TIME));
			String cookieSign = CookieUtils.getCookie(servletRequest, CommonConstant.COOKIE_SIGN);
			long validSeconds = NumberUtils.toLong(ConfigUtils.getValue(CommonConstant.LOGIN_VALID_SECONDS));
			if (validSeconds == 0) {
				validSeconds = NumberUtils.toLong(ConfigUtils.getValue(CommonConfigEnum.LOGIN_VALID_SECONDS.getKey()));
			}
			long now = System.currentTimeMillis();

			// 若强制校验或过期或签名失败
			int forceValidateTgt = NumberUtils.toInt(ConfigUtils.getValue(CommonConstant.FORCE_VALIDATE_TGT));
			if (forceValidateTgt == 1 || validSeconds * 1000 + cookieTime < now
					|| !this.getSign(userId, ticket, cookieTime).equals(cookieSign)) {
				// 校验用户的合法性
				Map<String, Object> params = new HashMap<String, Object>(2);
				params.put(CommonConstant.USERID, userId);
				params.put(CommonConstant.TICKET, ticket);
				String ubsValidateUrl = FileUtils.getRealPath(ConfigUtils.getValue(CommonConstant.UBS_URL),
						"tgt/validate/");
				BaseService service = BaseService.getBaseService();
				Result<Map<String, Object>> result = service.post(ubsValidateUrl, params,
						new BaseTypeReference<Result<Map<String, Object>>>() {
						});
				if (!result.getSuccess()) {
					log.info("用户登录失败userId=" + userId + ",ticket=" + ticket);
					this.responseToUser(servletRequest, servletResponse);
					return;
				} else {
					// 保存成功后的信息
					now = System.currentTimeMillis();
					String sign = this.getSign(userId, ticket, now);
					CookieUtils.addCookie(servletResponse, CommonConstant.COOKIE_SIGN, sign);
					CookieUtils.addCookie(servletResponse, CommonConstant.COOKIE_TIME, String.valueOf(now));
				}
			}
		} catch (Exception e) {
			log.error("校验用户时异常", e);
			return;
		}
		chain.doFilter(request, response);
	}

	private String getSign(String userId, String ticket, long now) {
		String salt = ConfigUtils.getValue(CommonConfigEnum.COOKIE_MD5_SIGN.getKey());
		return Md5Utils.md5(userId + "," + ticket + "," + now + "," + salt);
	}

	private void responseToUser(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws IOException {
		// 是否ajax请求
		boolean isAjax = RequestUtils.isAjax(servletRequest);
		if (isAjax) {
			Result<Integer> result = Result.newFailureResult(CommonCode.COMMON_1064);
			ResponseUtils.printlnJson(servletResponse, JsonUtils.objectToJson(result));
		} else {
			String loginUrl = ConfigUtils.getValue(CommonConstant.LOGIN_URL);
			servletResponse.sendRedirect(loginUrl);
		}
	}

}
