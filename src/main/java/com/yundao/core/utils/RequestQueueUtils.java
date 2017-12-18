package com.yundao.core.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletRequest;

import com.yundao.core.log.Log;
import org.apache.commons.lang.math.NumberUtils;

import com.yundao.core.config.common.CommonConfigEnum;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.log.LogFactory;
import com.yundao.core.threadlocal.ThreadLocalUtils;
import com.yundao.core.threadlocal.filter.RequestCommonParams;

/**
 * 请求链接队列工具类
 * 
 * @author wupengfei wupf86@126.com
 */
public abstract class RequestQueueUtils {

	private static Log log = LogFactory.getLog(RequestQueueUtils.class);

	private static int abandonInterval = NumberUtils.toInt(ConfigUtils.getValue(CommonConstant.ABANDON_INTERVAL),
			CommonConstant.NEGATIVE_ONE);
	private static int capacity = NumberUtils.toInt(ConfigUtils.getValue(CommonConstant.CAPACITY),
			CommonConstant.NEGATIVE_ONE);
	private static BlockingQueue<Request> queue = null;

	private static Thread thread = new Thread() {
		@Override
		public void run() {
			doExecute();
		}
	};

	static {
		if (abandonInterval == CommonConstant.NEGATIVE_ONE) {
			abandonInterval = NumberUtils.toInt(ConfigUtils.getValue(CommonConfigEnum.SYSTEM_ABANDON_INTERVAL.getKey()));
		}
		if (capacity == CommonConstant.NEGATIVE_ONE) {
			capacity = NumberUtils.toInt(ConfigUtils.getValue(CommonConfigEnum.SYSTEM_CAPACITY.getKey()));
		}
		queue = new ArrayBlockingQueue<Request>(capacity);

		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * 往队列里添加数据
	 * 
	 * @param request
	 * @return
	 */
	public static boolean offer(Request request) {
		if (request == null) {
			return false;
		}
		boolean result = queue.offer(request);
		if (!result) {
			log.error("往队列添加数据失败，队列数据的数据已达上限，size=" + queue.size());
		}
		return result;
	}

	/**
	 * 从队列里获取数据
	 * 
	 * @return
	 */
	public static Request take() {
		try {
			return queue.take();
		}
		catch (Exception e) {
			log.error("从队列里获取异步数据时异常", e);
			return null;
		}
	}

	/**
	 * 增加异步请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean addAsyncRequest(HttpServletRequest request) {
		boolean isAsync = ThreadLocalUtils.getRequestCommonParams().isAsync();
		if (isAsync) {
			Request requestQueue = Request.newDefault(request);
			RequestQueueUtils.offer(requestQueue);
			return true;
		}
		return false;
	}

	private static void doExecute() {
		Request request = take();
		while (request != null) {
			try {
				long requestTime = request.getRequestTime();
				long now = System.currentTimeMillis();
				long interval = now - requestTime;

				RequestCommonParams requestParams = request.getRequestCommonParams();
				String url = request.getUrl();
				if (interval <= abandonInterval) {
					requestParams.setAsync(false);
					HttpUtils.post(url, requestParams);
				}
				else {
					log.begin("系统繁忙，丢弃异步链接，url=", url, "，请求时间requestTime=", requestTime, ",interval=" + interval,
							",requestParams=", requestParams);
				}
				request = take();
			}
			catch (Exception e) {
				log.error("异步链接执行时异常", e);
			}
		}
	}

	/**
	 * 请求链接对象
	 */
	public static class Request {

		/**
		 * 请求的链接
		 */
		private String url;

		/**
		 * 请求对象的参数
		 */
		private RequestCommonParams requestCommonParams;

		/**
		 * 请求时间
		 */
		private long requestTime;

		private Request() {

		}

		/**
		 * 新建默认对象
		 * 
		 * @param request
		 * @return
		 */
		public static Request newDefault(HttpServletRequest request) {
			Request result = new Request();
			RequestCommonParams rcp = ThreadLocalUtils.getRequestCommonParams();
			rcp.setHeaderParams(RequestUtils.getHeaders(request));
			result.setUrl(request.getRequestURL().toString());
			result.setRequestCommonParams(rcp);
			result.setRequestTime(System.currentTimeMillis());
			return result;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public RequestCommonParams getRequestCommonParams() {
			return requestCommonParams;
		}

		public void setRequestCommonParams(RequestCommonParams requestCommonParams) {
			this.requestCommonParams = requestCommonParams;
		}

		public long getRequestTime() {
			return requestTime;
		}

		public void setRequestTime(long requestTime) {
			this.requestTime = requestTime;
		}

	}
}
