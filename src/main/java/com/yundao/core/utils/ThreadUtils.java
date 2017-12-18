package com.yundao.core.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * thread 工具类
 *
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class ThreadUtils {

	private static ExecutorService service = null;

	static {
		int processors = Runtime.getRuntime().availableProcessors();
		service = Executors.newFixedThreadPool(processors);
	}

	/**
	 * 执行线程
	 * 
	 * @param runnable
	 */
	public static void execute(Runnable runnable) {
		service.execute(runnable);
	}

}
