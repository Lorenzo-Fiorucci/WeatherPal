package com.univpm.oop.WeatherPal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Vector;

import com.univpm.oop.WeatherPal.model.Forecast.AirPollution;
import com.univpm.oop.WeatherPal.model.Measures.Measure;
import com.univpm.oop.WeatherPal.model.tools.MeasuresAnalyzer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class AnalyzerTests {

	Vector<Measure<Integer>> vett = new Vector<>();
	Measure<Integer> n1, n2, n3, n4;
	Vector<Measure<AirPollution>> pollutions = new Vector<>();
	Measure<AirPollution> p1, p2, p3;

	@BeforeAll
	public void setup() {
		n1 = new Measure<Integer>(10);  vett.add(n1);
		n2 = new Measure<Integer>(20);  vett.add(n2);
		n3 = new Measure<Integer>(30);  vett.add(n3);
		n4 = new Measure<Integer>(40);  vett.add(n4);

		AirPollution poll1 = new AirPollution();
		poll1.setCo(2.5);
		poll1.setNo(0.5);
		poll1.setNo2(2.2);
		poll1.setO3(3.3);
		poll1.setPm10(10.1);
		AirPollution poll2 = new AirPollution();
		poll2.setCo(2.7);
		poll2.setNo(0.7);
		poll2.setNo2(2.4);
		poll2.setO3(3.5);
		poll2.setPm10(10.3);
		AirPollution poll3 = new AirPollution();
		poll3.setCo(2.3);
		poll3.setNo(0.3);
		poll3.setNo2(2.0);
		poll3.setO3(3.1);
		poll3.setPm10(9.9);
		
		p1 = new Measure<AirPollution>(poll1);  pollutions.add(p1);
		p2 = new Measure<AirPollution>(poll2);  pollutions.add(p2);
		p3 = new Measure<AirPollution>(poll3);  pollutions.add(p3);
	}

	@AfterAll
	public void tearDown() {

	}

	@Test
	public void test1() {
		assertEquals("value: 40", MeasuresAnalyzer.findMax(vett).toString());
		assertEquals("value: 10", MeasuresAnalyzer.findMin(vett).toString());
		assertEquals(25, MeasuresAnalyzer.calcNumAvg(vett));
	}

	@Test
	public void test2() {
		assertEquals(160, MeasuresAnalyzer.calcNumVar(vett));
	}

	@Test
	public void test3() {
		assertEquals(13, MeasuresAnalyzer.calcNumStdDev(vett));
	}

	@Test 
	public void test4() {
		assertEquals("value: index: 0 -- co: 2.7 -- no: 0.7 -- no2: 2.4 -- o3: 3.5 -- pm10: 10.3",
					 MeasuresAnalyzer.findMax(pollutions).toString());
		
		assertEquals("value: index: 0 -- co: 2.3 -- no: 0.3 -- no2: 2.0 -- o3: 3.1 -- pm10: 9.9",
					 MeasuresAnalyzer.findMin(pollutions).toString());
	}

	@Test
	public void test5() {
		HashMap<String, Object> avg = MeasuresAnalyzer.calcDistribAvg(pollutions);
		assertEquals(2.5, avg.get("co"));
		assertEquals(0.5, avg.get("no"));
		assertEquals(2.2, avg.get("no2"));
		assertEquals(3.3, avg.get("o3"));
		assertEquals(10.1, avg.get("pm10"));
	}

	@Test
	public void test6() {
		HashMap<String, Object> var = MeasuresAnalyzer.calcDistribVar(pollutions);
		assertEquals(0.04, var.get("co"));
		assertEquals(0.04, var.get("no"));
		assertEquals(0.04, var.get("no2"));
		assertEquals(0.04, var.get("o3"));
		assertEquals(0.04, var.get("pm10"));
	}

	@Test
	public void test7() {
		HashMap<String, Object> stdDev = MeasuresAnalyzer.calcDistribStdDev(pollutions);
		assertEquals(0.2, stdDev.get("co"));
		assertEquals(0.2, stdDev.get("no"));
		assertEquals(0.2, stdDev.get("no2"));
		assertEquals(0.2, stdDev.get("o3"));
		assertEquals(0.2, stdDev.get("pm10"));
	}
	
}
