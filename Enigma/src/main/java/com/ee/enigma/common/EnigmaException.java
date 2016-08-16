package com.ee.enigma.common;

public class EnigmaException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message = null;

	/**
	 * 
	 * @param message
	 */
	public EnigmaException(String message) {
		super(message);
		this.message = message;
	}

	public EnigmaException() {
		super();
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public EnigmaException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
