package com.banking_app.validate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileNotEmptyImpl implements ConstraintValidator<FileNotEmpty,MultipartFile>{

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		if(file!=null || file.isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Please Upload File....").addConstraintViolation();
			return false;
		}
		return true;
	}

}
