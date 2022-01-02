package com.univpm.oop.WeatherPal.model.tools;

import java.time.*;
/**
 * Class to simplify conversions from and to epoch seconds
 */
public class EpochConverter {

	/**
	 * 
	 * @param epochSeconds : seconds passed from 01-01-1970 (EPOCH)
	 * @return LocalDateTime corresponding to {@code epochSeconds}
	 */
	public static LocalDateTime toLocalDateTime(long epochSeconds){
		return LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.ofHours(1));
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
	 * Gets a LocalDateTime object and returns seconds passed from the EPOCH moment
	 * @param dateTime
	 * @return second passed from 01-01-1970 (EPOCH)
	 */
	public static int toEpochSeconds(LocalDateTime dateTime) {
		long epoch = dateTime.toEpochSecond(ZoneOffset.ofHours(1));
		return (int) epoch;
	}

}
