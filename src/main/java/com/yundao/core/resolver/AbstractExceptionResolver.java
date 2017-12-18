package com.yundao.core.resolver;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yundao.core.code.Result;
import com.yundao.core.code.config.DatabaseCode;
import com.yundao.core.exception.BaseException;
import com.yundao.core.exception.BaseRuntimeException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.yundao.core.code.config.CommonCode;

/**
 * 异常解析
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class AbstractExceptionResolver implements HandlerExceptionResolver {

	/**
	 * 处理异常
	 * 
	 * @param e
	 * @throws BaseException
	 */
	public static void handleException(Exception e) throws BaseException {
		Map<String, String> args = new HashMap<String, String>();
		int code = CommonCode.COMMON_1007;
		if (e instanceof SocketTimeoutException) {
			code = CommonCode.COMMON_1072;
		}
		else if (e instanceof ConnectTimeoutException) {
			ConnectTimeoutException connectException = (ConnectTimeoutException) e;
			code = CommonCode.COMMON_1074;
			args.put("url", connectException.getHost().toURI());
		}
		else if (e instanceof HttpHostConnectException) {
			HttpHostConnectException connectException = (HttpHostConnectException) e;
			code = CommonCode.COMMON_1074;
			args.put("url", connectException.getHost().toURI());
		}
		else if (e instanceof IOException) {
			code = CommonCode.COMMON_1073;
		}
		else if (e instanceof BaseException || e instanceof BaseRuntimeException) {
			throw new BaseException(e);
		}
		throw new BaseException(code, e, args);
	}

	/**
	 * 获取异常信息
	 * 
	 * @param request
	 * @param e
	 * @return
	 */
	protected Result<String> getExceptionResult(HttpServletRequest request, Exception e) {
		String message = e.getMessage();
		String stackMessage = null;
		int code = 0;
		if (e instanceof BaseException) {
			BaseException exception = (BaseException) e;
			code = exception.getCode();
			stackMessage = exception.getStackMessage();
		}
		else if (e instanceof BaseRuntimeException) {
			BaseRuntimeException exception = (BaseRuntimeException) e;
			code = exception.getCode();
			stackMessage = exception.getStackMessage();
		}
		else {
			if (e instanceof QueryTimeoutException) {
				code = DatabaseCode.DB_101;
			}
			else if (e instanceof DataRetrievalFailureException) {
				code = DatabaseCode.DB_102;
			}
			else if (e instanceof PermissionDeniedDataAccessException) {
				code = DatabaseCode.DB_103;
			}
			else if (e instanceof DataAccessException) {
				code = DatabaseCode.DB_105;
			}
			else if(e instanceof NumberFormatException || e instanceof ClassCastException){
				code = CommonCode.COMMON_1043;
			}
			else if(e instanceof MissingServletRequestParameterException){
				code = CommonCode.COMMON_1000;
			}
			else if(e instanceof HttpRequestMethodNotSupportedException){
				code = CommonCode.COMMON_1080;
			}
			else {
				code = CommonCode.COMMON_1007;
			}
			message = null;
			stackMessage = ExceptionUtils.getStackTrace(e);
		}
		return Result.newResult(code, message, stackMessage);
	}
}
