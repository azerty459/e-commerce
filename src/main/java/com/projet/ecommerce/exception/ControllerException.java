package com.projet.ecommerce.exception;

public class ControllerException extends EcommerceException {

	public ControllerException() {
		super();
	}

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}

}
