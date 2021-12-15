package com.univpm.oop.WeatherPal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.univpm.oop.WeatherPal.model.Measures.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class MeasuresTests {
	DailyMeasure<Integer> peso, velocita, mele;

	@BeforeAll
	public void setup() {
		try {
			peso = new DailyMeasure<Integer>(65, "kg", "11-12-2021", "dd-MM-yyyy");
			//velocita = new DailyMeasure<Integer>(120, "km/h", "11-12-2021", "MM-dd-yyyy");
			mele = new DailyMeasure<Integer>(5, "11 dicembre 2021", "dd MMMM yyyy");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@AfterAll
	public void tearDown() {

	}

	@Test
	public void testToString() {
		assertEquals("value: 65 kg --- date: 11-12-2021", peso.toString());
		//assertEquals("value: 120 km/h --- date: 11-12-2021", velocita.toString());
		assertEquals("value: 5 --- date: 11-12-2021", mele.toString());
	}
}