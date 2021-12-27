package com.univpm.oop.WeatherPal.model.Measures;

import com.univpm.oop.WeatherPal.exceptions.InvalidFormatterException;
import com.univpm.oop.WeatherPal.model.tools.EpochConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class that represents measures with date and time
 * @param T 
 * 		: the concrete type for the measure value. It must be a class that extends
 * 		<a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Number.html">Number</a>
 */
public class InstantMeasure<T> extends DailyMeasure<T> {

	LocalTime time;

	public InstantMeasure(Measure<T> measure, LocalDate date, LocalTime time) {
		super(measure, date);
		this.time = time;
	}

	/**
	 * 
	 * @param value : the value of the measure, which is of the non-primitive type {@code T}
	 * @param unit : a string containing the unit of measure (kg, degrees,  metres, etc.)
	 * @param epochSeconds :  second passed from 01-01-1970 (EPOCH)
	 */
	public InstantMeasure(T value, String unit, long epochSeconds) {
		super(value, unit, epochSeconds);
		time = EpochConverter.toLocalTime(epochSeconds);
	}

	public InstantMeasure(T value, long epochSeconds) {
		this(value, "", epochSeconds);
	}

	/**
	 * 
	 * @param value
	 * 		: the value of the measure, which is of the non-primitive type {@code T}
	 * @param unit
	 * 		: a string containing the unit of measure (kg, degrees,  metres, etc.)
	 * @param stringDate
	 * 		: the date of the measure, following the date format specified in {@code dateFormat} 
	 * @param dateFormat
	 * 		: format of {@code stringDate}. It must follow DateTimeFormatter's pattern conventions 
	 * 		(refer to Oracle's <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html"
	 * 		>documentation on DateTimeFormatter</a>) 
	 * @param timeString
	 * 		: the hour of the measure. It must follow the pattern HH:mm
	 * @throws InvalidFormatterException
	 * 		thrown if {@code dateFormat} isn't a valid pattern
	 * @throws DateTimeParseException
	 *		thrown if {@code stringDate} cannot be parsed using {@code dateFormat} 's pattern
	 *		or if {@code stringTime} cannot be parsed using the pattern HH:mm
	 */

	public InstantMeasure(T value, String unit, String stringDate, String dateFormat, String stringTime) throws InvalidFormatterException, DateTimeParseException {
		super(value, unit, stringDate, dateFormat);
		this.time = LocalTime.parse(stringTime);
	}

	public InstantMeasure(T value, String stringDate, String dateFormat, String timeString) throws InvalidFormatterException, DateTimeParseException {
		this(value, "", stringDate, dateFormat, timeString);
	}

	public LocalTime getTime() {
		return time;
	}

	/**
	 * 
	 * @param dateFormat 
	 * 		: date formatter's pattern. It must follow DateTimeFormatter's pattern conventions 
	 * 		(refer to Oracle's <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html"
	 * 		>documentation on DateTimeFormatter</a>) 
	 * @return 
	 * 		a string representation of the InstantMeasure object, whit {@code dateFormat} as personalized date pattern
	 */

	public String toString(String dateFrormat) throws InvalidFormatterException {
		
		String toReturn = "value: " + value;
		if(unit != "")
			toReturn += " " + unit;
		
		DateTimeFormatter formatter = buildFormatter(dateFrormat);
		toReturn += " --- date: " + date.format(formatter) + " at ";

		formatter = DateTimeFormatter.ofPattern("HH:mm");
		toReturn += time.format(formatter);

		return toReturn;
	}

	public String toString() {

		String toReturn = "";
		try {
			toReturn = toString("dd-MM-yyyy");
		} catch (InvalidFormatterException e) {
			// non ci entrerò mai perchè le stringhe passate al toString sono pattern validi
		}
		return toReturn;
	}
	
}
