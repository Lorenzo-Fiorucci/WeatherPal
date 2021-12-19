package com.univpm.oop.WeatherPal.model.Exceptions;

public class EmptyVectorException extends Exception {
	
	public EmptyVectorException() {
		super();
	}

	public EmptyVectorException(String message) {
		super(message);
	}

}
