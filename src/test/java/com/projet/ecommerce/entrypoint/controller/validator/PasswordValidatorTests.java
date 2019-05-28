package com.projet.ecommerce.entrypoint.controller.validator;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordValidatorTests {

	private PasswordValidator passwordValidator = new PasswordValidator();

	@Test
	public void isValid() {
		Assert.assertTrue(passwordValidator.isValid("", null));
		Assert.assertTrue(passwordValidator.isValid("minMAJ456;:!", null));
		Assert.assertTrue(passwordValidator.isValid("azertY8!", null));
		Assert.assertFalse(passwordValidator.isValid(null, null));
		Assert.assertFalse(passwordValidator.isValid("\t", null));
		Assert.assertFalse(passwordValidator.isValid("aZ8!", null));
		Assert.assertFalse(passwordValidator.isValid("azerY8!", null));
		Assert.assertFalse(passwordValidator.isValid("jesuisunmotdepasseenminuscule", null));
		Assert.assertFalse(passwordValidator.isValid("minMAJ456", null));
		Assert.assertFalse(passwordValidator.isValid("minMAJ;:!", null));
		Assert.assertFalse(passwordValidator.isValid("min;:!456", null));
		Assert.assertFalse(passwordValidator.isValid(",;:MAJ456", null));
	}

}
