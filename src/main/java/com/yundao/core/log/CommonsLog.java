package com.yundao.core.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * commons日志框架
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class CommonsLog extends AbstractLog {

	private Log log;

	public CommonsLog(Log log) {
		this.log = log;
	}

	public CommonsLog(String loggerName) {
		log = LogFactory.getLog(loggerName);
	}

	@Override
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	@Override
	public void debug(String msg) {
		log.debug(msg);
	}

	@Override
	public void debug(String msg, Throwable t) {
		log.debug(msg, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	@Override
	public void info(String msg) {
		log.info(msg);
	}

	@Override
	public void info(String msg, Throwable t) {
		log.info(msg, t);
	}

	@Override
	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}

	@Override
	public void warn(String msg) {
		log.warn(msg);
		com.yundao.core.log.LogFactory.incrementWarnCount();
	}

	@Override
	public void warn(String msg, Throwable t) {
		log.warn(msg, t);
		com.yundao.core.log.LogFactory.incrementWarnCount();
	}

	@Override
	public void error(String msg) {
		log.error(msg);
		com.yundao.core.log.LogFactory.incrementErrorCount();
	}

	@Override
	public void error(String msg, Throwable t) {
		log.error(msg, t);
		com.yundao.core.log.LogFactory.incrementErrorCount();
	}

}