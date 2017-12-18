package com.yundao.core.validator.number;

import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<Number, Object>{
	
	private static final String regex = "[0-9]+";

	private boolean isBlank;
	
    public void initialize(Number number) {
    	this.isBlank = number.isBlank();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
    	if (null == value) {
			return isBlank;
		}
    	Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value.toString());
		return matcher.matches();
    }

}