package com.banking_app.validate;

import com.banking_app.dto.AdminDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidatorImpl implements ConstraintValidator<ConfirmPasswordValidator, AdminDTO> {

	@Override
	public boolean isValid(AdminDTO adminDTO, ConstraintValidatorContext context) {
		if (adminDTO.getPassword() == null || adminDTO.getConfirmPassword() == null) {
			return false;
		}
		if(adminDTO.getPassword().equals(adminDTO.getConfirmPassword())==false) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Confirm Password and entered password are not same.").addConstraintViolation();
			return false;
		}
		return true;
	}

}
