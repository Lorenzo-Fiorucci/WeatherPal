package com.univpm.oop.WeatherPal;

import com.univpm.oop.WeatherPal.exceptions.InvalidFormatterException;
import com.univpm.oop.WeatherPal.model.Measures.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

@TestInstance(Lifecycle.PER_CLASS)
public class MeasuresTests {
	
	DailyMeasure<Integer> peso, velocita, mele;
	InstantMeasure<Double> lat, lon, alt;
	
	@Test
	public void test1() {
		try {
			peso = new DailyMeasure<Integer>(65, "kg", "11-12-2021", "dd-MM-yyyy");
			mele = new DailyMeasure<Integer>(5, "11 dicembre 2021", "dd MMMM yyyy");
			lat = new InstantMeasure<Double>(12.4, "degrees", "11-12-2021", "dd-MM-yyyy", "15:30");
			lon = new InstantMeasure<Double>(42.7, "11-12-2021", "dd-MM-yyyy", "23:30");
		} catch (InvalidFormatterException e) {
			// i pattern sono  corretti
		}
		assertEquals("value: 65 kg --- date: 11-12-2021", peso.toString());
		assertEquals("value: 5 --- date: 11-12-2021", mele.toString());
		
		assertEquals("value: 12.4 degrees --- date: 11-12-2021 at 15:30", lat.toString());
		assertEquals("value: 42.7 --- date: 11-12-2021 at 23:30", lon.toString());
	}
	
	@Test
	public void test2() {
		try {
			velocita = new DailyMeasure<Integer>(120, "km/h", "11-12-2021", "MM-dd-yyyy");
		} catch(InvalidFormatterException e) {
			// il pattern e' sintatticamente corretto
		}
		assertEquals("value: 120 km/h --- date: 12-11-2021", velocita.toString());
	}

	@Test
	public void test3() {
		
		assertThrows(DateTimeParseException.class, () -> new InstantMeasure<Double>(156.13, "meters", "11-12-2021", "dd-MM-yyyy", "9.30"));
	}
}