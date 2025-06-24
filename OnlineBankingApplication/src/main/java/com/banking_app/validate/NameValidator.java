package com.banking_app.validate;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ ElementType.PARAMETER, FIELD })
@Constraint(validatedBy=NameValidatorImpl.class)
public @interface NameValidator {
	
	String message() default "";
	
	String fieldName() default "";
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
