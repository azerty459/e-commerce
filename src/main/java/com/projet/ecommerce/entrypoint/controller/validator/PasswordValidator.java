package com.projet.ecommerce.entrypoint.controller.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

	@Override
	public void initialize(Password constraintAnnotation) {

	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
		if (password == null) {
			return false;
		} else if ("".equals(password)) {
			return true;
		}
		return password.matches("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,}$");
	}

}
