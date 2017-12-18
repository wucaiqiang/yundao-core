package com.yundao.core.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;

/**
 * 操作客户端cookie类
 * 
 * @author zhangmingxing
 *
 */
public abstract class CookieUtils {

	private static Log log = LogFactory.getLog(CookieUtils.class);

	/**
	 * 根据名字获取cookie值
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		return getCookie(request, name, true);
	}

	/**
	 * 根据名字获取cookie值
	 * 
	 * @param request
	 * @param name
	 * @param isDecrypt
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name, boolean isDecrypt) {
		Cookie cookie = getCookies(request, name);
		if (cookie != null) {
			try {
				return isDecrypt ? DesUtils.decrypt(cookie.getValue()) : cookie.getValue();
			} catch (Exception e) {
				log.error("获取cookie值时异常", e);
			}
		}
		return null;
	}

	/**
	 * 获取cookie对象
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookies(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			return cookieMap.get(name);
		}
		return null;
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 添加cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void addCookie(HttpServletResponse response, String name, String value) {
		addCookie(response, name, value, true);
	}

	/**
	 * 添加cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param isEncrypt
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, boolean isEncrypt) {
		try {
			if (value == null) {
				value = "";
			}
			Cookie cookie = new Cookie(name.trim(), isEncrypt ? DesUtils.encrypt(value.trim()) : value.trim());
			cookie.setPath("/");
			
			int maxAge = NumberUtils.toInt(ConfigUtils.getValue("cookie.active.time"));
			log.info("cookie存活时长为：" + maxAge + "秒");

			if (maxAge > 0) {
				cookie.setMaxAge(maxAge);
			}
			response.addCookie(cookie);
		} catch (Exception e) {
			log.error("获取cookie值时异常", e);
		}
	}

	/**
	 * 删除cookie
	 * 
	 * @param name
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		if (getCookies(request, name) != null) {
			Cookie cookie = new Cookie(name, null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}
}
