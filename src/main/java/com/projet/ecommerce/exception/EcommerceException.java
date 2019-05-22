package com.projet.ecommerce.exception;

public class EcommerceException extends Exception {

	public EcommerceException() {
	}

	public EcommerceException(String s) {
		super(s);
	}

	public EcommerceException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public EcommerceException(Throwable throwable) {
		super(throwable);
	}

}
