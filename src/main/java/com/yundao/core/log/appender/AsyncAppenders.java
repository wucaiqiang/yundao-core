package com.yundao.core.log.appender;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.yundao.core.utils.BooleanUtils;
import org.slf4j.LoggerFactory;

/**
 * 异步输出
 *
 * @author wupengfei wupf86@126.com
 */
public class AsyncAppenders extends AsyncAppender {

    private static final String NAME = "AsyncAppenders";

    /**
     * 是否启用日志记录
     */
    private String isEnableLog;

    @Override
    public void setQueueSize(int queueSize) {
        if (queueSize > 0) {
            super.setQueueSize(queueSize);
        }
    }

    @Override
    protected boolean isDiscardable(ILoggingEvent event) {
        return Boolean.parseBoolean(isEnableLog) && super.isDiscardable(event);
    }

    @Override
    public void setName(String name) {
        if (BooleanUtils.isBlank(name)) {
            name = NAME;
        }
        super.setName(name);
    }

    /**
     * 是否启用日志记录
     *
     * @param isEnableLog 空或true：启用，false：停用
     */
    public void setIsEnableLog(String isEnableLog) {
        if (BooleanUtils.isBlank(isEnableLog)) {
            isEnableLog = Boolean.TRUE.toString();
        }
        this.isEnableLog = isEnableLog;
    }

    /**
     * 初始化数据
     */
    public void init() {
        setQueueSize(1000);
        setName(null);
        setIsEnableLog(null);
    }

    /**
     * 把当前类添加到根Logger
     */
    public void addToRootLogger() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(this);
        rootLogger.setLevel(Level.INFO);

        setContext(rootLogger.getLoggerContext());
    }
}
