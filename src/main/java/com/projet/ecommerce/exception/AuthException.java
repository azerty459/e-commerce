package com.projet.ecommerce.exception;

public class AuthException extends SecurityException {

	public AuthException() {
		super();
	}

	public AuthException(String s) {
		super(s);
	}

	public AuthException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public AuthException(Throwable throwable) {
		super(throwable);
	}

}
