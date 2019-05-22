package com.projet.ecommerce.exception;

public class ControllerException extends EcommerceException {

	public ControllerException() {
		super();
	}

	public ControllerException(String s) {
		super(s);
	}

	public ControllerException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public ControllerException(Throwable throwable) {
		super(throwable);
	}

}
