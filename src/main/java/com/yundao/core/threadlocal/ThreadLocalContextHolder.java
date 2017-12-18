package com.yundao.core.threadlocal;

/**
 * 本地线程保持者
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class ThreadLocalContextHolder {

	private static final ThreadLocal<ThreadLocalContext> THREAD_LOCAL = new ThreadLocal<ThreadLocalContext>() {
		@Override
		public ThreadLocalContext initialValue() {
			return new DefaultThreadLocalContext();
		}
	};

	/**
	 * 获取本地线程上下文
	 * 
	 * @return
	 */
	public static ThreadLocalContext getThreadLocalContext() {
		ThreadLocalContext context = THREAD_LOCAL.get();
		if (context == null) {
			context = new DefaultThreadLocalContext();
			THREAD_LOCAL.set(context);
		}
		return context;
	}

	/**
	 * 清空本地线程上下文
	 * 
	 */
	public static void remove() {
		ThreadLocalContext threadLocalContext = getThreadLocalContext();
		threadLocalContext.clearMap();
		THREAD_LOCAL.set(null);
	}

}