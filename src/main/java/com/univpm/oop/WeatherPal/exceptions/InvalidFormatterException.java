package com.univpm.oop.WeatherPal.exceptions;

/**
 * Exception thrown if the formatter's pattern used to parse a LocalDate (or LocalTime or LocalDateTime)
 * doesn't match the date (or time or date-time) pattern
 */
public class InvalidFormatterException extends Exception {
	
	public InvalidFormatterException() {
		super();
	}

	public InvalidFormatterException(String message) {
		super(message);
	}

}
