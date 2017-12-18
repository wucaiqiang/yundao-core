package com.yundao.core.jsonp;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import com.yundao.core.constant.CommonConstant;

/**
 * Jsonp注解
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
@ControllerAdvice(annotations = Jsonp.class)
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

	public JsonpAdvice() {
		super(CommonConstant.CALLBACK);
	}
}