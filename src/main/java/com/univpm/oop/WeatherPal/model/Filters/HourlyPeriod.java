package com.univpm.oop.WeatherPal.model.Filters;


import java.time.*;
import java.time.format.*;

import com.univpm.oop.WeatherPal.exceptions.*;

/**
 * Class that represent a period of time between two LocalDateTime objects.
 * Designed to facilitate verification of dates contained or not in a certain period.
 */
public class HourlyPeriod extends DailyPeriod{
	
	LocalTime startTime, endTime;

	/**
	 * 
	 * @param startDate : date on n which the period starts
	 * @param endDate : date on which the period ends
	 * @param startTime : time the period starts
	 * @param endTime : time the period ends
	 */
	public HourlyPeriod(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
		super(startDate, endDate);
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public HourlyPeriod(LocalDateTime start, LocalDateTime end) {
		super(start.toLocalDate(), end.toLocalDate());
		startTime = start.toLocalTime();
		endTime = end.toLocalTime();
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
	
	public HourlyPeriod(String startDate, String endDate, String dateFormat, String startTime, String endTime, String timeFormat)
		throws InvalidFormatterException, DateTimeParseException {
		
		super(startDate, endDate, dateFormat);
		this.startTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern(timeFormat));
		this.endTime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern(timeFormat));
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	/**
	 * 
	 * @param dateTime : LocalDateTime to be checked.
	 * @return True if {@code dateTime} is contained in this period, false otherwise. 
	 */
	public boolean contains(LocalDateTime dateTime) {
		
		LocalDateTime start = LocalDateTime.of(startDate, startTime);
		LocalDateTime end = LocalDateTime.of(endDate, endTime);
		
		return (dateTime.compareTo(start) >= 0) && (dateTime.compareTo(end) <= 0);
	}

	@Override
	public String toString() {
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		
		return "from " + startDate.format(dateFormatter) + " at " + startTime.format(timeFormatter) + 
			   ", to " + endDate.format(dateFormatter) + " at " + endTime.format(timeFormatter);
	}

}
