package com.banking_app.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressValidatorImpl implements ConstraintValidator<AddressValidator, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isBlank() || value.isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Please Enter the Address...").addConstraintViolation();
			return false;
		}
		if (!(value.length() >= 10 && value.length() <= 60)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Address Length between 10 to 60.").addConstraintViolation();
			return false;
		}
		return false;
	}

}
