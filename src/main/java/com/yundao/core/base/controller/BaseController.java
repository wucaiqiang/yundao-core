package com.yundao.core.base.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yundao.core.utils.MapUtils;
import com.yundao.core.utils.RequestUtils;

/**
 * 控制基类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class BaseController {

	/**
	 * 获取请求的参数
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> getParams(HttpServletRequest request) {
		Map<String, String> params = RequestUtils.getParameterMap(request);
		return MapUtils.cast(params);
	}
}
