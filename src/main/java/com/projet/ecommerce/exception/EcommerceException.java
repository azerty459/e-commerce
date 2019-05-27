package com.projet.ecommerce.exception;

public class EcommerceException extends Exception {

	public EcommerceException() {
		super();
	}

	public EcommerceException(String message) {
		super(message);
	}

	public EcommerceException(String message, Throwable cause) {
		super(message, cause);
	}

	public EcommerceException(Throwable cause) {
		super(cause);
	}

}
