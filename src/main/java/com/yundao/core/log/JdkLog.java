package com.yundao.core.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * jdk日志框架
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class JdkLog extends AbstractLog {

	private Logger log;

	private String loggerName;

	public JdkLog(String loggerName) {
		this.loggerName = loggerName;
		log = Logger.getLogger(loggerName);
	}

	@Override
	public boolean isDebugEnabled() {
		return log.isLoggable(Level.FINE);
	}

	@Override
	public void debug(String msg) {
		log.logp(Level.FINE, loggerName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
	}

	@Override
	public void debug(String msg, Throwable t) {
		log.logp(Level.FINE, loggerName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return log.isLoggable(Level.INFO);
	}

	@Override
	public void info(String msg) {
		log.logp(Level.INFO, loggerName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
	}

	@Override
	public void info(String msg, Throwable t) {
		log.logp(Level.INFO, loggerName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg, t);
	}

	@Override
	public boolean isWarnEnabled() {
		return log.isLoggable(Level.WARNING);
	}

	@Override
	public void warn(String msg) {
		log.logp(Level.WARNING, loggerName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
		LogFactory.incrementWarnCount();
	}

	@Override
	public void warn(String msg, Throwable t) {
		log.logp(Level.WARNING, loggerName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg, t);
		LogFactory.incrementWarnCount();
	}

	@Override
	public void error(String msg) {
		log.logp(Level.SEVERE, loggerName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg);
		LogFactory.incrementErrorCount();
	}

	@Override
	public void error(String msg, Throwable t) {
		log.logp(Level.SEVERE, loggerName, Thread.currentThread().getStackTrace()[1].getMethodName(), msg, t);
		LogFactory.incrementErrorCount();
	}

}
