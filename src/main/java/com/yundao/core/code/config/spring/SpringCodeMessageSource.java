package com.yundao.core.code.config.spring;

import java.util.Locale;

import com.yundao.core.code.config.CodeFileConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

/**
 * spring编码消息资源
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SpringCodeMessageSource implements MessageSource {

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return CodeFileConfig.getValue(code);
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return this.getMessage(code, args, null, locale);
	}

	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		return null;
	}

}
