package com.yundao.core.validator.spring;

import org.springframework.validation.FieldError;

/**
 * spring错误封装
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SpringError extends FieldError {

	private static final long serialVersionUID = 1L;

	/**
	 * 消息模板
	 */
	private final String messageTemplate;

	public SpringError(String objectName, String field, String defaultMessage, String messageTemplate) {
		this(objectName, field, null, false, null, null, defaultMessage, messageTemplate);
	}

	public SpringError(String objectName, String field, Object rejectedValue, boolean bindingFailure, String[] codes,
			Object[] arguments, String defaultMessage, String messageTemplate) {
		super(objectName, field, rejectedValue, bindingFailure, codes, arguments, defaultMessage);
		this.messageTemplate = messageTemplate;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}

}
