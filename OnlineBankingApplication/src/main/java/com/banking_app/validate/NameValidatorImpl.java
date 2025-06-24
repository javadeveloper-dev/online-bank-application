package com.banking_app.validate;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import com.banking_app.dto.AdminDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidatorImpl implements ConstraintValidator<NameValidator,String>{
	
	public boolean isValid(String value, ConstraintValidatorContext context) {
//		 String value=adminDTO.getFirstName()==null?null:adminDTO.getFirstName();
		if(value==null || value.isBlank() || value.isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Please Enter the Name...").addConstraintViolation();
			return false;
		}
		if(value.length()<2) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Name is too small....").addConstraintViolation();
			return false;
		}
		if(value.length()>50) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Name is too large....").addConstraintViolation();
			return false;
		}
		if((int)value.charAt(0)>=97 && (int)value.charAt(0)<=122) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("First Character is UpperCase.....").addConstraintViolation();
			return false;
		}
		String regex="^[A-Z][a-z]+";
		Pattern pattern=Pattern.compile(regex);
		if(Pattern.matches(regex,value)==false) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Please Enter the Valid Name....").addConstraintViolation();
			return false;
		}
		
		return true;
	}
	

}
