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
	InstantMeasure<Double> lat, lon, alt;

	@BeforeAll
	public void setup() {
		try {
			peso = new DailyMeasure<Integer>(65, "kg", "11-12-2021", "dd-MM-yyyy");
			mele = new DailyMeasure<Integer>(5, "11 dicembre 2021", "dd MMMM yyyy");
			//velocita = new DailyMeasure<Integer>(120, "km/h", "11-12-2021", "MM-dd-yyyy");
			lat = new InstantMeasure<Double>(12.4, "gradi", "11-12-2021", "dd-MM-yyyy", "15:30");
			lon = new InstantMeasure<Double>(42.7, "11-12-2021", "dd-MM-yyyy", "23:30");
			//alt = new InstantMeasure<Double>(156.13, "piedi", "11-12-2021", "dd-MM-yyyy", "9.30");
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
		assertEquals("value: 5 --- date: 11-12-2021", mele.toString());
		//assertEquals("value: 120 km/h --- date: 11-12-2021", velocita.toString()); ERRORE! il toString stampa come data 12:11:2021
		assertEquals("value: 12.4 gradi --- date: 11-12-2021 at 15:30", lat.toString());
		assertEquals("value: 42.7 --- date: 11-12-2021 at 23:30", lon.toString());
		//assertEquals("value: 156.13 piedi --- date: 11-12-2021 at 9.30", alt.toString()); ERRORE! 9.30 non è nel formato HH:mm
	}
}