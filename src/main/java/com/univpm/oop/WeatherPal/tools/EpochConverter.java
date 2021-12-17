package com.univpm.oop.WeatherPal.tools;

import java.time.*;

public class EpochConverter {
	
	/**
	 * 
	 * @param epochSeconds : seconds passed from 01-01-1970 (EPOCH)
	 * @return LocalDateTime corresponding to {@code epochSeconds}
	 */
	public static LocalDateTime toLocalDateTime(long epochSeconds){
		LocalDateTime dateTime = LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.ofHours(1));
		return dateTime;
	}

	/**
	 * 
	 * @param epochSeconds : seconds passed from 01-01-1970 (EPOCH)
	 * @return LocalDate corresponding to {@code epochSeconds}
	 */
	public static LocalDate toLocalDate(long epochSeconds) {
		LocalDateTime dateTime = LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.ofHours(1));
		return dateTime.toLocalDate();
	}

	/**
	 * 
	 * @param epochSeconds : seconds passed from 01-01-1970 (EPOCH)
	 * @return LocalDateTime corresponding to {@code epochSeconds} 
	 */
	public static LocalTime toLocalTime(long epochSeconds) {
		LocalDateTime dateTime = LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.ofHours(1));
		return dateTime.toLocalTime();
	}

	/**
	 * Gets date and time and returns corresponding seconds passed from 01-01-1970 (EPOCH)
	 * @param date
	 * @param time
	 * @return seconds passed from 01-01-1970 (EPOCH)
	 */
	public static int toEpochSeconds(LocalDate date, LocalTime time) {
		long epoch = time.toEpochSecond(date, ZoneOffset.ofHours(1));
		return (int) epoch;
	}

	/**
	 * Gets a LOcalDateTime object and returns seconds passed from the EPOCH moment
	 * @param dateTime
	 * @return second passed from 01-01-1970 (EPOCH)
	 */
	public static int toEpochSeconds(LocalDateTime dateTime) {
		long epoch = dateTime.toEpochSecond(ZoneOffset.ofHours(1));
		return (int) epoch;
	}

}
