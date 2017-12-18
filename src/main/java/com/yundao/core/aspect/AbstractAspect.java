package com.yundao.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 抽象切面
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class AbstractAspect {

	/**
	 * 获取方法签名
	 * 
	 * @param jp
	 * @return
	 */
	protected MethodSignature getMethodSignature(JoinPoint jp) {
		Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		return methodSignature;
	}
}
