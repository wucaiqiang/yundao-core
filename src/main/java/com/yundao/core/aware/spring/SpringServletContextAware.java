package com.yundao.core.aware.spring;

import javax.servlet.ServletContext;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.aware.AbstractServletContextAware;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.utils.ConfigUtils;

/**
 * 设置应用属性
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public class SpringServletContextAware extends AbstractServletContextAware {

	private static Log log = LogFactory.getLog(SpringServletContextAware.class);

	private ServletContext servletContext;

	private String resources;

	public void init() {
		String version = ConfigUtils.getValue(CommonConstant.VERSION);
		resources = "/resources_" + version;
		servletContext.setAttribute(CommonConstant.RESOURCES, servletContext.getContextPath() + resources);
		servletContext.setAttribute(CommonConstant.VERSION, version);

		log.info("资源映射，resources=" + resources);
		
		this.subclassInit();
	}
	
	protected void subclassInit() {
		
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String getResources() {
		return resources;
	}

}
