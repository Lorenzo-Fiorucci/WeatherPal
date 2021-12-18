package com.univpm.oop.WeatherPal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import com.univpm.oop.WeatherPal.model.*;
import com.univpm.oop.WeatherPal.model.Exceptions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class PeriodTests {
	
	Period p1, p2;
	LocalDateTime d;

	@BeforeAll
	public void setup() {
		try {
			p1 = new Period("12-12-2021", "16-12-2021", "dd-MM-yyyy", "09:15", "20:00", "HH:mm");
			p2 = new Period("12-12-2021", "16-12-2021", "dd-MM-yyyy");
			d = LocalDateTime.of(2021, 12, 16, 20, 00);
		} catch(InvalidFormatterException e) {
			System.out.println(e);
		}
	}

	@AfterAll
	public void teraDown() {
		
	}

	@Test
	public void test() {
		assertEquals("from 12-12-2021 09:15 to 16-12-2021 20:00", p1.toString());
		assertEquals("from 12-12-2021 00:00 to 16-12-2021 00:00", p2.toString());
		assertEquals(true, p1.contains(d));
	}
}