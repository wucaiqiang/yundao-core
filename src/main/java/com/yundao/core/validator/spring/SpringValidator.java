package com.yundao.core.validator.spring;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ParameterNameProvider;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.ConstraintDescriptor;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * spring校验器
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SpringValidator extends LocalValidatorFactoryBean {

	private static Log log = LogFactory.getLog(SpringValidator.class);

	@Override
	protected void processConstraintViolations(Set<ConstraintViolation<Object>> violations, Errors errors) {
		for (ConstraintViolation<Object> violation : violations) {
			String field = determineField(violation);
			FieldError fieldError = errors.getFieldError(field);
			if (fieldError == null || !fieldError.isBindingFailure()) {
				try {
					ConstraintDescriptor<?> cd = violation.getConstraintDescriptor();
					String errorCode = determineErrorCode(cd);
					Object[] errorArgs = getArgumentsForConstraint(errors.getObjectName(), field, cd);
					if (errors instanceof BindingResult) {
						BindingResult bindingResult = (BindingResult) errors;
						String nestedField = bindingResult.getNestedPath() + field;
						if ("".equals(nestedField)) {
							String[] errorCodes = bindingResult.resolveMessageCodes(errorCode);
							bindingResult.addError(new ObjectError(errors.getObjectName(), errorCodes, errorArgs,
									violation.getMessage()));
						}
						else {
							Object rejectedValue = getRejectedValue(field, violation, bindingResult);
							String[] errorCodes = bindingResult.resolveMessageCodes(errorCode, field);
							bindingResult.addError(new SpringError(errors.getObjectName(), nestedField, rejectedValue,
									false, errorCodes, errorArgs, violation.getMessage(),
									violation.getMessageTemplate()));
						}
					}
					else {
						errors.rejectValue(field, errorCode, errorArgs, violation.getMessage());
					}
				}
				catch (NotReadablePropertyException ex) {
					log.error("属性没有配置读取的方法", ex);
				}
			}
		}
	}

	@Override
	public ParameterNameProvider getParameterNameProvider() {
		return null;
	}

	@Override
	public ExecutableValidator forExecutables() {
		return null;
	}

}
