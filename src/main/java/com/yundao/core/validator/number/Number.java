package com.yundao.core.validator.number;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberValidator.class)
@Documented
@JsonInclude(Include.NON_NULL)
public @interface Number {

	/**
	 * 是否可以为空
	 * 
	 * @return
	 */
	boolean isBlank() default true;
	
	String message() default "{Number.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
}
