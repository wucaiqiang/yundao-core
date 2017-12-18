package com.yundao.core.validator.spring;

import java.util.List;

import com.yundao.core.code.Result;
import com.yundao.core.constant.CommonConstant;
import com.yundao.core.exception.BaseException;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 校验结果处理类
 * 
 * @author zhangmingxing
 *
 */
public abstract class BindingResultHandler {

	/**
	 * 以抛异常的方式处理校验结果
	 * 
	 * @param bindingResult
	 * @throws BaseException
	 */
	public static void handleByException(BindingResult bindingResult) throws BaseException {
		Result<Integer> result = handle(bindingResult);
		if (!result.getSuccess()) {
			throw new BaseException(result.getCode(), result.getMessage());
		}
	}

	/**
	 * 处理校验结果
	 * 
	 * @param bindingResult
	 * @return
	 */
	public static <T> Result<T> handle(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			int code = CommonConstant.ZERO;
			List<ObjectError> list = bindingResult.getAllErrors();
			ObjectError oe = list.get(0);
			String message = oe.getDefaultMessage();
			if (oe instanceof SpringError) {
				SpringError se = (SpringError) oe;
				String template = se.getMessageTemplate();
				template = template.replaceFirst("\\{", "");
				template = template.replaceFirst("\\}", "");
				code = NumberUtils.toInt(template);
			}
			return Result.newResult(code, message, null);
		}
		return Result.newResult(CommonConstant.ONE, null);
	}

}
