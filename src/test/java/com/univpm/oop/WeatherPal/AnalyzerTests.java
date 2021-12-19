package com.univpm.oop.WeatherPal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Vector;

import com.univpm.oop.WeatherPal.model.Exceptions.EmptyVectorException;
import com.univpm.oop.WeatherPal.model.Measures.Measure;
import com.univpm.oop.WeatherPal.tools.MeasuresAnalyzer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class AnalyzerTests {

	Vector<Measure<Integer>> vett = new Vector<>();
	Measure<Integer> n1, n2, n3, n4;

	@BeforeAll
	public void setup() {
		n1 = new Measure<Integer>(10);
		n2 = new Measure<Integer>(20);
		n3 = new Measure<Integer>(30);
		n4 = new Measure<Integer>(40);
		vett.add(n1);
		vett.add(n2);
		vett.add(n3);
		vett.add(n4);

	}

	@AfterAll
	public void tearDown() {

	}

	@Test
	public void test() {
		try {
		assertEquals("value: 40", MeasuresAnalyzer.findMax(vett).toString());
		assertEquals("value: 10", MeasuresAnalyzer.findMin(vett).toString());
		assertEquals(25, MeasuresAnalyzer.calcAvg(vett));
		assertEquals(15, MeasuresAnalyzer.calcVar(vett));
		
		} catch(EmptyVectorException e) {
			System.out.println(e);
		}
	}
	
}
