package com.yundao.core.converter.doubles;

import com.yundao.core.log.Log;
import com.yundao.core.converter.AbstractConverter;
import com.yundao.core.log.LogFactory;

/**
 * 浮点类形转化类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SpringDoubleConverter extends AbstractConverter<String, Double> {

	private static Log log = LogFactory.getLog(SpringDoubleConverter.class);

	@Override
	public Double convert(String source) {
		try {
			return Double.valueOf(source);
		}
		catch (Exception e) {
			log.error("浮点类型转化异常source=" + source);
		}
		return null;
	}

}
