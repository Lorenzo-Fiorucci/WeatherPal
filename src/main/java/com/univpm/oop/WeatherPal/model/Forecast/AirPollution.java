package com.univpm.oop.WeatherPal.model.Forecast;

/**
 * Class that stores data on air composition in order to analyze air quality.
 * Its {@code index} field is a number between 1 and 5, where 1 means good quality, 5 means very poor quality.<p>
 * To be constructed with default constructor and filled through setters. 
 */
public class AirPollution {
	
	private byte index;
	private double co, no, no2, o3, pm10;

	public byte getIndex() {
		return this.index;
	}

	public void setIndex(byte index) {
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
}
