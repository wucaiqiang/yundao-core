package com.yundao.core.threadlocal.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * request或session属性
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public class RequestAttribute {

	private RequestCommonParams params;
	private HttpServletRequest request;
	private HttpSession session;

	public RequestAttribute(HttpServletRequest request) {
		this.request = request;
		this.session = this.getSession(true);
		this.params = RequestCommonParams.get(request);
	}

	/**
	 * 删除request中的属性
	 * 
	 * @param key
	 */
	public void removeRequestAttribute(String key) {
		if (request != null) {
			this.request.removeAttribute(key);
		}
	}

	/**
	 * 删除session中的属性
	 * 
	 * @param key
	 */
	public void removeSessionAttribute(String key) {
		HttpSession nowSession = this.getSession(false);
		if (nowSession != null) {
			nowSession.removeAttribute(key);
		}
	}

	/**
	 * 设置request属性
	 * 
	 * @param key
	 * @param value
	 */
	public void setRequestAttribute(String key, Object value) {
		if (request != null) {
			this.request.setAttribute(key, value);
		}
	}

	/**
	 * 设置session属性
	 * 
	 * @param key
	 * @param value
	 */
	public void setSessionAttribute(String key, Object value) {
		HttpSession newSession = this.getSession(false);
		if (newSession != null) {
			newSession.setAttribute(key, value);
		}
	}

	/**
	 * 从request中获取属性值
	 * 
	 * @param key
	 * @param scope
	 * @return
	 */
	public Object getRequestAttribute(String key) {
		if (request != null) {
			return this.request.getAttribute(key);
		}
		return null;
	}

	/**
	 * 从session中获取属性值
	 * 
	 * @param key
	 * @param scope
	 * @return
	 */
	public Object getSessionAttribute(String key) {
		HttpSession nowSession = this.getSession(false);
		if (nowSession != null) {
			return nowSession.getAttribute(key);
		}
		return null;
	}

	/**
	 * 使session无效
	 */
	public void invalidateSession() {
		HttpSession nowSession = this.getSession(false);
		if (nowSession != null) {
			nowSession.invalidate();
		}
	}

	/**
	 * 获取共用参数
	 * 
	 * @return
	 */
	public RequestCommonParams getRequestCommonParams() {
		return params;
	}

	/**
	 * 获取session
	 * 
	 * @param allowCreate
	 * @return
	 */
	private HttpSession getSession(boolean allowCreate) {
		if (this.request != null) {
			HttpSession newSession = this.request.getSession(allowCreate);
			if (newSession != null && this.session == null) {
				this.session = newSession;
			}
		}
		return this.session;
	}

}
