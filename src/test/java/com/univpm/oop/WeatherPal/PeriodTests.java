package com.univpm.oop.WeatherPal;

import com.univpm.oop.WeatherPal.exceptions.*;
import com.univpm.oop.WeatherPal.model.Filters.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@TestInstance(Lifecycle.PER_CLASS)
public class PeriodTests {
	
	HourlyPeriod hourPer1, hourPer2;
	DailyPeriod dayPer1;
	LocalDateTime dateTime1, dateTime2;
	LocalDate date1, date2;

	@BeforeAll
	public void setup() {
		try {
			hourPer1 = new HourlyPeriod("12-12-2021", "16-12-2021", "dd-MM-yyyy", "09:15", "20:00", "HH:mm");
			dateTime1 = LocalDateTime.parse("16-12-2021_20-00", DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm"));
			dateTime2 = LocalDateTime.parse("16-12-2021 21:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
			dayPer1 = new DailyPeriod("12-12-2021", "16-12-2021", "dd-MM-yyyy");
			date1 = LocalDate.parse("12-12/2021", DateTimeFormatter.ofPattern("dd-MM/yyyy"));
			date2 = LocalDate.parse("11-12/2021", DateTimeFormatter.ofPattern("dd-MM/yyyy"));
			
		} catch(InvalidFormatterException e) {
			System.out.println(e);
		}
	}

	@AfterAll
	public void teraDown() {
		
	}

	@Test
	public void test1() {
		assertEquals("from 12-12-2021 at 09:15, to 16-12-2021 at 20:00", hourPer1.toString());
		assertEquals("from 12-12-2021 to 16-12-2021", dayPer1.toString());
	}
	
	@Test
	public void test2() {
		assertTrue(hourPer1.contains(dateTime1));
		assertFalse(hourPer1.contains(dateTime2));
		assertTrue(dayPer1.contains(date1));
		assertFalse(dayPer1.contains(date2));
	}

}
