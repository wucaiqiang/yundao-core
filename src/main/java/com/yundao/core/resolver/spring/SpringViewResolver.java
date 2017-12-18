package com.yundao.core.resolver.spring;

import java.util.Locale;
import java.util.Map;

import com.yundao.core.resolver.AbstractViewResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * spring视图解析
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SpringViewResolver extends AbstractViewResolver {

	private Map<String, ViewResolver> resolvers;

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		String ext = null;
		String viewNoExt = viewName;
		int index = viewName.lastIndexOf(".");
		if (index != -1) {
			ext = viewName.substring(index + 1);
			viewNoExt = viewName.substring(0, index);
		}
		ViewResolver resolver = resolvers.get(ext);
		if (resolver == null) {
			resolver = resolvers.get("jsp");
		}
		return resolver.resolveViewName(viewNoExt, locale);
	}

	public void setResolvers(Map<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
	}

}