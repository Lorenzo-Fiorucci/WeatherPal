package com.univpm.oop.WeatherPal.model.Statistics;

import java.util.HashMap;

/**
 * Interface that describes a fluctuating behavior
 */
public interface Fluctuating<T> {
	
	public T getMax();

	public T getMin();

	public HashMap<String,Object> getAvg();

	public HashMap<String,Object> getVar();

	public HashMap<String,Object> getStdDev();

}
