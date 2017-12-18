package com.yundao.core.exception;

import java.util.Map;

import com.yundao.core.code.config.CodeFileConfig;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * 异常配置
 *
 * @author wupengfei wupf86@126.com
 *
 */
public class ExceptionConfig {

	/**
	 * 编码
	 */
	private int code;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 原始异常
	 */
	private Throwable throwable;

	public ExceptionConfig(int code) {
		this(code, null, null);
	}

	public ExceptionConfig(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public ExceptionConfig(int code, Throwable throwable) {
		this(code, throwable, (Map<String, String>) null);
	}

	public ExceptionConfig(int code, Throwable throwable, Map<String, String> args) {
		if (throwable instanceof BaseException) {
			BaseException be = (BaseException) throwable;
			this.code = be.getCode();
			this.desc = be.getDesc();
			this.throwable = be.getCause();
		}
		else if (throwable instanceof BaseRuntimeException) {
			BaseRuntimeException bre = (BaseRuntimeException) throwable;
			this.code = bre.getCode();
			this.desc = bre.getDesc();
			this.throwable = bre.getCause();
		}
		else {
			this.code = code;
			this.desc = CodeFileConfig.getValue(String.valueOf(code), args);
			this.throwable = throwable;
		}
	}

	/**
	 * 获取编码
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 获取异常描述信息
	 * 
	 * @return
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * 获取原始异常
	 * 
	 * @return
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	/**
	 * 获取堆栈信息
	 * 
	 * @return
	 */
	public String getStackMessage() {
		if (throwable != null) {
			return ExceptionUtils.getStackTrace(throwable);
		}
		return null;
	}

}
