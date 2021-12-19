package com.univpm.oop.WeatherPal.model.Statistics;

/**
 * Interface that describes a fluctuating behavior
 */
public interface Fluctuating<T> {
	
	public T getMax();

	public T getMin();

	public double getAvg();

	public double getVar();

	public double getStdDev();

}
