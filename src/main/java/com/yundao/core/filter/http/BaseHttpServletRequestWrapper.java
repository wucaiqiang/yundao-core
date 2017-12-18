package com.yundao.core.filter.http;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.StringUtils;

/**
 * http请求包装
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public class BaseHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private Map<String, String[]> params = new HashMap<String, String[]>();

	public BaseHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		this.params = request.getParameterMap();
	}

	public BaseHttpServletRequestWrapper(HttpServletRequest request, Map<String, String[]> params) {
		this(request);
		this.params = params;
	}

	@Override
	public String getParameter(String name) {
		String[] values = params.get(name);
		if (BooleanUtils.isEmpty(values)) {
			return null;
		}
		return values[0];
	}

	@Override
	public String[] getParameterValues(String name) {
		return params.get(name);
	}

	/**
	 * 添加参数
	 * 
	 * @param name
	 * @param value
	 */
	protected void addParameter(String name, Object value) {
		if (value != null) {
			String[] values = null;
			if (value instanceof String[]) {
				values = (String[]) value;
			}
			else if (value instanceof String) {
				values = new String[] { (String) value };
			}
			else {
				values = new String[] { String.valueOf(value) };
			}
			this.proccessValue(values);
			params.put(name, values);
		}
	}

	/**
	 * 处理值，如去除空格
	 * 
	 * @param values
	 */
	protected void proccessValue(String[] values) {
		if (values != null) {
			int length = values.length;
			for (int i = 0; i < length; i++) {
				if (values[i] != null) {
					values[i] = StringUtils.escapeHtml(values[i].trim());
				}
			}
		}
	}
	
}