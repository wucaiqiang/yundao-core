package com.yundao.core.exception;

import java.util.Map;

import com.yundao.core.code.config.CommonCode;

/**
 * 检查型异常基类
 *
 * @author wupengfei wupf86@126.com
 *
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 异常配置
	 */
	private ExceptionConfig config;

	public BaseException(int code) {
		this(code, null, (Map<String, String>) null);
	}

	public BaseException(Throwable throwable) {
		this(CommonCode.COMMON_1007, throwable, (Map<String, String>) null);
	}

	public BaseException(int code, String desc) {
		config = new ExceptionConfig(code, desc);
	}

	public BaseException(int code, Throwable t) {
		this(code, t, (Map<String, String>) null);
	}

	public BaseException(int code, Map<String, String> args) {
		this(code, null, args);
	}

	public BaseException(int code, Throwable t, Map<String, String> args) {
		config = new ExceptionConfig(code, t, args);
	}

	@Override
	public String getMessage() {
		return this.getDesc();
	}

	@Override
	public Throwable getCause() {
		return config.getThrowable();
	}

	/**
	 * 获取编码
	 * 
	 * @return
	 */
	public int getCode() {
		return config.getCode();
	}

	/**
	 * 获取描述信息
	 * 
	 * @return
	 */
	public String getDesc() {
		return config.getDesc();
	}

	/**
	 * 获取堆栈信息
	 * 
	 * @return
	 */
	public String getStackMessage() {
		return config.getStackMessage();
	}

}
