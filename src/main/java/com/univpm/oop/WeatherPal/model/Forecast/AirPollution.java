package com.univpm.oop.WeatherPal.model.Forecast;

import com.univpm.oop.WeatherPal.model.Statistics.Distribution;

/**
 * Class that stores data on air composition in order to analyze air quality.
 * Its {@code index} field is a number between 1 and 5, where 1 means good quality, 5 means very poor quality.<p>
 * To be constructed with default constructor and filled through setters. 
 */
public class AirPollution implements Comparable<AirPollution>, Distribution {
	
	private double index, co, no, no2, o3, pm10;

	public double getIndex() {
		return this.index;
	}

	public void setIndex(double index) {
		this.index = index;
	}

	public double getCo() {
		return this.co;
	}

	public void setCo(double co) {
		this.co = co;
	}

	public double getNo() {
		return this.no;
	}

	public void setNo(double no) {
		this.no = no;
	}

	public double getNo2() {
		return this.no2;
	}

	public void setNo2(double no2) {
		this.no2 = no2;
	}

	public double getO3() {
		return this.o3;
	}

	public void setO3(double o3) {
		this.o3 = o3;
	}

	public double getPm10() {
		return this.pm10;
	}

	public void setPm10(double pm10) {
		this.pm10 = pm10;
	}

	/**
	 * 
	 * @param other : the object to be compared.
	 * @return a negative integer if this object is less than {@code other}, a positive integer if this object is greater than {@other},
	 * 		zero if they equals.
	 * @throws NullPointerException 
	 * 		if {@code other} is null
	 * @throws ClassCastException 
	 * 		if {@code other}'s effective type is not AirPollution'
	 */
	@Override
	public int compareTo(AirPollution other) throws NullPointerException, ClassCastException {
		
		if(other == null)
			throw new NullPointerException("Impossible to compare an AirPollution object with a null object");
		
		if(!(other instanceof AirPollution))
			throw new ClassCastException("Other object's effective type isn't AirPollution");

		double sum1 = no2 + o3 + pm10;
		double sum2 = other.no2 + other.o3 + other.pm10;

		int toReturn;
		if(sum1 < sum2)
			toReturn = (int)(sum1 - sum2 - 0.5); // +0.5/-0.5 serve a far si che, dopo il casting a int, il double sia arrotondato 
		else									 // all'intero piu' vicino al suo valore originario
			toReturn = (int)(sum1 - sum2 + 0.5);
		
		return toReturn;
	}

}
