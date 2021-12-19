package com.univpm.oop.WeatherPal.tools;


import java.time.*;
import java.time.format.*;

import com.univpm.oop.WeatherPal.exceptions.*;

/**
 * Class that represent a period of time between two LocalDateTime objects.
 * Designed to facilitate verification of dates contained or not in a certain period.
 */
public class Period {
	
	LocalDateTime start, end;

	public Period(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * 
	 * @param startDate
	 * 		: date on n which the period starts
	 * @param endDate
	 * 		: date on which the period ends
	 * @param dateFormat
	 * 		: format of {@code startDate} and {@code endDate}. It must follow DateTimeFormatter's pattern conventions 
	 * 		(refer to Oracle's <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html"
	 * 		>documentation on DateTimeFormatter</a>) 
	 * @param startTime
	 * 		: time the period starts
	 * @param endTime
	 * 		: time the period ends
	 * @param timeFormat
	 * 		: format of {@code startTime} and {@code endTime}. It must follow DateTimeFormatter's pattern conventions 
	 * 		(refer to Oracle's <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html"
	 * 		>documentation on DateTimeFormatter</a>) 
	 * @throws InvalidFormatterException
	 * 		thrown if {@code dateFormat} or {@code timeFormat} isn't a valid pattern
	 * @throws DateTimeParseException
	 * 		thrown if {@code startDate} or {@code endDate} cannot be parsed using {@code dateFormat}'s pattern
	 *		or if {@code startTime} or {@code endTime} cannot be parsed using {@code timeFormat}'s pattern
	 */
	
	public Period(String startDate, String endDate, String dateFormat, String startTime, String endTime, String timeFormat)
		throws InvalidFormatterException, DateTimeParseException {
		
		start = LocalDateTime.parse(startDate + " " + startTime, DateTimeFormatter.ofPattern(dateFormat + " " + timeFormat));
		end = LocalDateTime.parse(endDate + " " + endTime, DateTimeFormatter.ofPattern(dateFormat + " " + timeFormat));
	}

	public Period(String startDate, String endDate, String dateFormat) throws InvalidFormatterException, DateTimeParseException {
		start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dateFormat)).atStartOfDay();
		end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern(dateFormat)).atStartOfDay();
	}

	/**
	 * 
	 * @param dateTime : LocalDateTime to be checked.
	 * @return True if {@code dateTime} is contained in this period, false otherwise. 
	 */
	public boolean contains(LocalDateTime dateTime) {
		return (dateTime.compareTo(start) >= 0) && (dateTime.compareTo(end) <= 0);
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return "from " + start.format(formatter) + " to " + end.format(formatter);
	}

}
