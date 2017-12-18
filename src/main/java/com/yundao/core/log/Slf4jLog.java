package com.yundao.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * slf4j日志框架
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class Slf4jLog extends AbstractLog {

	private static final String NAME = Slf4jLog.class.getName();

	static {
		// check the logger is LocationAwareLogger instance, if not it can not
		// get correct StackTraceElement
		Logger logger = LoggerFactory.getLogger(Slf4jLog.class);
		if (!(logger instanceof LocationAwareLogger)) {
			throw new RuntimeException(logger.getClass() + " is not LocationAwareLogger instance");
		}
	}

	private LocationAwareLogger log;

	public Slf4jLog(LocationAwareLogger log) {
		this.log = log;
	}

	public Slf4jLog(String loggerName) {
		this.log = (LocationAwareLogger) LoggerFactory.getLogger(loggerName);
	}

	@Override
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	@Override
	public void debug(String msg) {
		log.log(null, NAME, LocationAwareLogger.DEBUG_INT, msg, null, null);
	}

	@Override
	public void debug(String msg, Throwable t) {
		log.log(null, NAME, LocationAwareLogger.DEBUG_INT, msg, null, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	@Override
	public void info(String msg) {
		log.log(null, NAME, LocationAwareLogger.INFO_INT, msg, null, null);
	}

	@Override
	public void info(String msg, Throwable t) {
		log.log(null, NAME, LocationAwareLogger.INFO_INT, msg, null, t);
	}

	@Override
	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}

	@Override
	public void warn(String msg) {
		log.log(null, NAME, LocationAwareLogger.WARN_INT, msg, null, null);
		LogFactory.incrementWarnCount();
	}

	@Override
	public void warn(String msg, Throwable t) {
		log.log(null, NAME, LocationAwareLogger.WARN_INT, msg, null, t);
		LogFactory.incrementWarnCount();
	}

	@Override
	public void error(String msg) {
		log.log(null, NAME, LocationAwareLogger.ERROR_INT, msg, null, null);
		LogFactory.incrementErrorCount();
	}

	@Override
	public void error(String msg, Throwable t) {
		log.log(null, NAME, LocationAwareLogger.ERROR_INT, msg, null, t);
		LogFactory.incrementErrorCount();
	}

}
