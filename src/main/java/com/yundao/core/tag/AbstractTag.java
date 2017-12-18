package com.yundao.core.tag;

import javax.servlet.jsp.tagext.BodyTagSupport;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;

/**
 * 抽象标签类
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class AbstractTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(AbstractTag.class);

	@Override
	public int doStartTag() {
		int result = SKIP_BODY;
		try {
			result = this.startTag();
		}
		catch (Exception e) {
			log.error("执行开始标签时异常", e);
		}
		return result;
	}

	@Override
	public int doEndTag() {
		this.destroy();
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		super.release();
		this.destroy();
	}

	/**
	 * 销毁资源
	 */
	public void destroy() {

	}

	/**
	 * 执行开始标签
	 * 
	 * @return
	 * @throws Exception
	 */
	public int startTag() throws Exception {
		return SKIP_BODY;
	}

}
