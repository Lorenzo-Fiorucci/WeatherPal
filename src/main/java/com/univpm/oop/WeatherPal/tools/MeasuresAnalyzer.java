package com.univpm.oop.WeatherPal.tools;

import java.lang.Math;
import java.util.Vector;

import com.univpm.oop.WeatherPal.model.Measures.Measure;
import com.univpm.oop.WeatherPal.model.Exceptions.EmptyVectorException;

public class MeasuresAnalyzer {

	public static <T extends Number> Measure<T> findMax(Vector<Measure<T>> values) throws EmptyVectorException {
		
		if(values.isEmpty())
			throw new EmptyVectorException("ERROR: empty vector passed to findMax.");
		
		int maxIndex = 0;

		for (int i = 1; i < values.size(); i++) {
			if(values.get(i).getValue().doubleValue() > values.get(maxIndex).getValue().doubleValue())
				maxIndex = i;
		}
		return values.get(maxIndex);
	}


	public static <T extends Number> Measure<T> findMin(Vector<Measure<T>> values) throws EmptyVectorException {

		if(values.isEmpty())
			throw new EmptyVectorException("ERROR: empty vector passed to findMin.");

			int minIndex = 0;

			for (int i = 1; i < values.size(); i++) {
				if(values.get(i).getValue().doubleValue() < values.get(minIndex).getValue().doubleValue())
					minIndex = i;
			}
			return values.get(minIndex);
	}


	public static <T extends Number> double calcAvg(Vector<Measure<T>> values) throws EmptyVectorException {

		if(values.isEmpty())
			throw new EmptyVectorException("ERROR: empty vector passed to calcAvg.");

		double avg = 0;
		
		for (Measure<T> m : values) {
			avg += m.getValue().doubleValue();
		}
		avg /= values.size();

		return avg;
	}


	public static <T extends Number> double calcVar(Vector<Measure<T>> values) throws EmptyVectorException {

		if(values.isEmpty())
			throw new EmptyVectorException("ERROE: empty vector passed to calcVar.");
		
		double var = 0;
		double avg = calcAvg(values);

		for (Measure<T> m : values) {
			var += Math.pow(m.getValue().doubleValue() - avg, 2);
		}
		var /= values.size() - 1;

		return var;
	}
}
