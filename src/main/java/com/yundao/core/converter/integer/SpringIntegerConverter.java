package com.yundao.core.converter.integer;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.converter.AbstractConverter;

/**
 * 整形转化类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SpringIntegerConverter extends AbstractConverter<String, Integer> {

	private static Log log = LogFactory.getLog(SpringIntegerConverter.class);

	@Override
	public Integer convert(String source) {
		try {
			return Integer.valueOf(source);
		}
		catch (Exception e) {
			log.error("整形转化异常source=" + source);
		}
		return null;
	}

}
