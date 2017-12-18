package com.yundao.core.validator.length;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

/**
 * 长度验证器，若值为空则返回false
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class LengthValidator implements ConstraintValidator<Length, Object> {

	private static final Log log = LoggerFactory.make();

	private boolean isBlank;
	private int min;
	private int max;

	@Override
	public void initialize(Length length) {
		isBlank = length.isBlank();
		min = length.min();
		max = length.max();
		validateParameters();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean isValueBlank = value == null || value.toString().equals("");
		if (!isBlank && isValueBlank) {
			return false;
		}
		if (isBlank && isValueBlank) {
			return true;
		}
		int length = value.toString().length();
		return length >= min && length <= max;
	}

	private void validateParameters() {
		if (min < 0) {
			throw log.getMinCannotBeNegativeException();
		}
		if (max < 0) {
			throw log.getMaxCannotBeNegativeException();
		}
		if (max < min) {
			throw log.getLengthCannotBeNegativeException();
		}
	}
}
