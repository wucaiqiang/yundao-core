package com.yundao.core.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.dao.QueryTimeoutException;

import com.yundao.core.code.config.CommonCode;
import com.yundao.core.code.config.DatabaseCode;

/**
 * 异常处理类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class ExceptionHandler {

	/**
	 * 封装后重新抛出基类检查型异常
	 * 
	 * @param e
	 * @throws BaseException
	 */
	public static void rethrowBaseException(Exception e) throws BaseException {
		if (e instanceof DataIntegrityViolationException) {
			throw new BaseException(DatabaseCode.DB_100, e);
		}
		else if (e instanceof QueryTimeoutException) {
			throw new BaseException(DatabaseCode.DB_101, e);
		}
		else if (e instanceof DataRetrievalFailureException) {
			throw new BaseException(DatabaseCode.DB_102, e);
		}
		else if (e instanceof PermissionDeniedDataAccessException) {
			throw new BaseException(DatabaseCode.DB_103, e);
		}
		throw new BaseException(CommonCode.COMMON_1007, e);
	}
}
