package com.github.ginjaninja.bb.account.account;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for Account objects
 *
 */

public class AccountValidator implements Validator{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean supports(Class clazz) {
		return Account.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "required");
	}

}
