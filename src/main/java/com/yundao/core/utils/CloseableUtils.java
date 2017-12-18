package com.yundao.core.utils;

import java.io.Closeable;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;

/**
 * 
 * 关闭资源工具类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class CloseableUtils {

	private static Log log = LogFactory.getLog(CloseableUtils.class);

	/**
	 * 关闭资源
	 * 
	 * @param closeable
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			}
			catch (Exception e) {
				log.error("关闭资源时发生异常", e);
			}
		}
	}

}