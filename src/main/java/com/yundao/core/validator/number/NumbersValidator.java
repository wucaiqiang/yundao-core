package com.yundao.core.validator.number;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yundao.core.utils.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 数字验证器，若值为空或不为数字则返回false
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class NumbersValidator implements ConstraintValidator<Numbers, Object> {

	private boolean isBlank;
	private List<Long> valueList;

	@Override
	public void initialize(Numbers number) {
		isBlank = number.isBlank();
		valueList = new ArrayList<Long>();
		String[] values = number.value();
		int length = (values != null) ? values.length : 0;
		for (int i = 0; i < length; i++) {
			valueList.add(Long.valueOf(values[i]));
		}
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
		
		if (!BooleanUtils.isEmpty(valueList)) {
			return valueList.contains(NumberUtils.toLong(value.toString()));
		}
		try {
			Long.valueOf(value.toString());
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

}