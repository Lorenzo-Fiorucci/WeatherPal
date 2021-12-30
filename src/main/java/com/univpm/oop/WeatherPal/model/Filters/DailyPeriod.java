package com.univpm.oop.WeatherPal.model.Filters;

import com.univpm.oop.WeatherPal.exceptions.InvalidFormatterException;
import com.univpm.oop.WeatherPal.model.JsonSerializers.DailyPeriodSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.format.*;

/**
 * Class that represent a period of time between two LocalDate objects.
 * Designed to facilitate verification of dates contained or not in a certain period.
 */
@JsonSerialize(using = DailyPeriodSerializer.class)
public class DailyPeriod {
	
	protected LocalDate startDate, endDate;

	public DailyPeriod(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
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
	 * @throws InvalidFormatterException
	 * 		thrown if {@code dateFormat} isn't a valid pattern
	 * @throws DateTimeParseException
	 * 		thrown if {@code startDate} or {@code endDate} cannot be parsed using {@code dateFormat} 's pattern
	 */
	public DailyPeriod(String startDate, String endDate, String dateFormat) throws InvalidFormatterException, DateTimeParseException {
		this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(dateFormat));
		this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern(dateFormat));
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	/**
	 * 
	 * @param date : LocalDate to be checked
	 * @return True if {@code date} is between this period's start date and end date, false otherwise. 
	 */
	public boolean contains(LocalDate date) {
		return (date.compareTo(startDate) >= 0) && (date.compareTo(endDate) <= 0);
	}

	/**
	 * 
	 * @param otherPeriod : period o be checked
	 * @return true if {@code otherPeriod} is contained in this period
	 */
	public boolean contains(DailyPeriod otherPeriod) {
		return contains(otherPeriod.getStartDate()) && contains(otherPeriod.getEndDate());
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return "from " + startDate.format(formatter) + " to " + endDate.format(formatter);
	}
}
