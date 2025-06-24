	package com.banking_app.validate;
	
import java.util.regex.Matcher;
import java.util.regex.Pattern;
	
	import jakarta.validation.ConstraintValidator;
	import jakarta.validation.ConstraintValidatorContext;
	
	public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator, String> {
	
		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			if (value == null || value.isBlank() || value.isEmpty()) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("Please Enter the Password...").addConstraintViolation();
				return false;
			}
			if (value.length() < 6) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(
						"Password is too small.Min. Length of Password is 6 characters.").addConstraintViolation();
				return false;
			}
			if (value.length() > 8) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(
						"Password is too large.Max. Length of Password is 8 characters.").addConstraintViolation();
				return false;
			}
			String regexForPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{6,8}$";
			Pattern compile = Pattern.compile(regexForPassword);
			Matcher matcher=compile.matcher(value);
//			boolean find = matcher.find();
			if (!matcher.find()) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("Please Enter the Valid Password..").addConstraintViolation();
				return false;
			}
			return true;
		}
	
	}
