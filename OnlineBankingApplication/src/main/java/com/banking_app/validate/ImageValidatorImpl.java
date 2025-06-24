package com.banking_app.validate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageValidatorImpl implements ConstraintValidator<ImageValidator,MultipartFile>{

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		if(file==null || file.isEmpty() || file.getSize()==0l) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Please Upload File....").addConstraintViolation();
			return false;
		}
		if(!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpg")) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("File extention should be JPEG or PNG...").addConstraintViolation();
			return false;
		}
		if(file.getSize()>2097152) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("File Size should be between 1 MB to 2 MB..").addConstraintViolation();
			return false;
		}
		return true;
	}
}
