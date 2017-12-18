package com.yundao.core.cache.visitor;

/**
 * 是否可被访问
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public interface Visitable {

	/**
	 * 接受访问者
	 * 
	 * @param vistor
	 * @param name
	 */
	public void accept(Visitor vistor, String name);

}
