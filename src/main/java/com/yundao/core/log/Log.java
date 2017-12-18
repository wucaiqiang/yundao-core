package com.yundao.core.log;

import java.util.function.Supplier;

/**
 * 日志接口
 *
 * @author wupengfei wupf86@126.com
 */
public interface Log {

    /**
     * 在方法的开头调用，级别是info
     *
     * @param msg
     */
    void begin(Object... msg);

    /**
     * 在方法结束时调用，级别是info
     */
    void end();

    /**
     * 是否启用debug级别
     *
     * @return
     */
    boolean isDebugEnabled();

    /**
     * 输出debug级别的信息
     *
     * @param msg
     */
    void debug(String msg);

    /**
     * 输出debug级别的信息
     *
     * @param charSequence
     * @param args
     */
    void debug(CharSequence charSequence, Object... args);

    /**
     * 输出debug级别的信息
     *
     * @param msg
     * @param t
     */
    void debug(String msg, Throwable t);

    /**
     * 是否启用info级别
     *
     * @return
     */
    boolean isInfoEnabled();

    /**
     * 输出info级别的信息
     *
     * @param msg
     */
    void info(String msg);

    /**
     * 输出info级别的信息
     *
     * @param supplier 表达式参数
     */
    void info(Supplier<String> supplier);

    /**
     * 输出info级别的信息
     *
     * @param charSequence
     * @param args
     */
    void info(CharSequence charSequence, Object... args);

    /**
     * 输出info级别的信息
     *
     * @param msg
     * @param t
     */
    void info(String msg, Throwable t);

    /**
     * 是否启用warn级别
     *
     * @return
     */
    boolean isWarnEnabled();

    /**
     * 输出warn级别的信息
     *
     * @param msg
     */
    void warn(String msg);

    /**
     * 输出warn级别的信息
     *
     * @param charSequence
     * @param args
     */
    void warn(CharSequence charSequence, Object... args);

    /**
     * 输出warn级别的信息
     *
     * @param msg
     * @param t
     */
    void warn(String msg, Throwable t);

    /**
     * 输出error级别的信息
     *
     * @param msg
     */
    void error(String msg);

    /**
     * 输出error级别的信息
     *
     * @param charSequence
     * @param args
     */
    void error(CharSequence charSequence, Object... args);

    /**
     * 输出error级别的信息
     *
     * @param msg
     * @param t
     */
    void error(String msg, Throwable t);

}
