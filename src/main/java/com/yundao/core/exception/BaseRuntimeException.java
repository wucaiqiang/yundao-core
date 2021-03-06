package com.yundao.core.exception;

import java.util.Map;

import com.yundao.core.code.config.CommonCode;

/**
 * 运行时异常基类
 *
 * @author wupengfei wupf86@126.com
 *
 */
public class BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 异常配置
	 */
	private ExceptionConfig config;

	public BaseRuntimeException(int code) {
		this(code, null, (Map<String, String>) null);
	}

	public BaseRuntimeException(Throwable throwable) {
		this(CommonCode.COMMON_1007, throwable, (Map<String, String>) null);
	}

	public BaseRuntimeException(int code, String desc) {
		config = new ExceptionConfig(code, desc);
	}

	public BaseRuntimeException(int code, Throwable t) {
		this(code, t, (Map<String, String>) null);
	}

	public BaseRuntimeException(int code, Map<String, String> args) {
		this(code, null, args);
	}

	public BaseRuntimeException(int code, Throwable t, Map<String, String> args) {
		config = new ExceptionConfig(code, t, args);
	}

	/**
	 * 获取描述信息
	 */
	@Override
	public String getMessage() {
		return this.getDesc();
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
