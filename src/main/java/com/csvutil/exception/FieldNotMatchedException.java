package com.csvutil.exception;

public class FieldNotMatchedException extends RuntimeException {

	private static final long serialVersionUID = 475015914515321066L;

	public FieldNotMatchedException() {
		super();
	}

	public FieldNotMatchedException(String message) {
		super(message);
	}

}
