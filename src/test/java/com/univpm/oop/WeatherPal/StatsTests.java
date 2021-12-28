package com.univpm.oop.WeatherPal;

import com.univpm.oop.WeatherPal.model.Statistics.*;
import com.univpm.oop.WeatherPal.exceptions.EmptyVectorException;
import com.univpm.oop.WeatherPal.model.Forecast.*;
import com.univpm.oop.WeatherPal.model.Measures.Measure;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

@TestInstance(Lifecycle.PER_CLASS)
public class StatsTests {
	
	DailyForecast forecast1, forecast2;
	Vector<? extends Forecast> forecasts;
	Stats statsObj;

	@BeforeAll
	public void setup() {
		forecast1 = new DailyForecast();
		forecast1.setTemp(new Measure<Double>((double)10));
		forecast1.setFeelsLike(new Measure<Double>((double)9));
		forecast1.setHumidity(new Measure<Byte>((byte)70));
		forecast1.setWind(new Measure<Integer>((int)18));
		forecast1.setPressure(new Measure<Integer>((int)2250));
		forecast1.setClouds(new Measure<Byte>((byte)10));
		AirPollution poll1 = new AirPollution();
		poll1.setCo(2.7);
		poll1.setNo(0.7);
		poll1.setNo2(2.4);
		poll1.setO3(3.5);
		poll1.setPm10(10.3);
		Measure<AirPollution> pollMes1 = new Measure<AirPollution>(poll1);
		forecast1.setAirPoll(pollMes1);
		forecast1.setDate(LocalDate.parse("12-12-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		
		forecast2 = new DailyForecast();
		forecast2.setTemp(new Measure<Double>((double)7));
		forecast2.setFeelsLike(new Measure<Double>((double)4));
		forecast2.setHumidity(new Measure<Byte>((byte)90));
		forecast2.setWind(new Measure<Integer>((int)34));
		forecast2.setPressure(new Measure<Integer>((int)2500));
		forecast2.setClouds(new Measure<Byte>((byte)5));
		AirPollution poll2 = new AirPollution();
		poll2.setCo(2.3);
		poll2.setNo(0.3);
		poll2.setNo2(2.0);
		poll2.setO3(3.1);
		poll2.setPm10(9.9);
		Measure<AirPollution> pollMes2 = new Measure<AirPollution>(poll2);
		forecast2.setAirPoll(pollMes2);
		forecast2.setDate(LocalDate.parse("25-12-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		forecasts = new Vector<DailyForecast>();
		((Vector<DailyForecast>)forecasts).add(forecast1);
		((Vector<DailyForecast>)forecasts).add(forecast2);

		try {
			statsObj = new Stats(forecasts);
		} catch (EmptyVectorException e) {
			// forecast non e' vuoto
		}

	}

	@AfterAll
	public void tearDown() {

	}

	@Test
	public void testTemperature() {
		assertEquals("value: 10.0 --- date: 12-12-2021", statsObj.getTemp().getMax().toString());
		assertEquals("value: 7.0 --- date: 25-12-2021", statsObj.getTemp().getMin().toString());
	}

	@Test
	public void testPressure() {
		assertEquals("value: 2500 --- date: 25-12-2021", statsObj.getPressure().getMax().toString());
		assertEquals("value: 2250 --- date: 12-12-2021", statsObj.getPressure().getMin().toString());
	}

	@Test
	public void testAirPollution() {
		assertEquals(2.7, statsObj.getAirPoll().getMax().getValue().getCo());
		assertEquals(2.3, statsObj.getAirPoll().getMin().getValue().getCo());
		assertEquals(2.5, statsObj.getAirPoll().getAvg().get("co"));
		assertEquals(0.08, statsObj.getAirPoll().getVar().get("co"));
		assertEquals(0.2828, statsObj.getAirPoll().getStdDev().get("co"));
	}

	@Test
	public void testPrint() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			assertEquals("ciao", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(statsObj)); // il messaggio di errore "expected 'ciao' but was..."
																										// serve a vedere come viene stampato statsObj
		} catch (JsonProcessingException e) {														
			System.out.println(e);
		}
	}

}
