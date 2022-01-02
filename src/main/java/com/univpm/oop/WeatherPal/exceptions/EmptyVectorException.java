package com.univpm.oop.WeatherPal.exceptions;

/**
 * Exception thrown if an empty vector is passed to a method
 */
public class EmptyVectorException extends Exception {
	
	public EmptyVectorException() {
		super();
	}

	public EmptyVectorException(String message) {
		super(message);
	}

}
