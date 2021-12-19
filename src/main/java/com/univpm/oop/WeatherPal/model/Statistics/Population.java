package com.univpm.oop.WeatherPal.model.Statistics;

/**
 * Class that represents a group (population) of something
 * @param T : non primitive type of the population's elements
 */
public class Population<T> implements Fluctuating<T> {

	public String name;
	public T max, min;
	public double avg, var, stdDev;

	@Override
	public T getMax() {
		return max;
	}

	@Override
	public T getMin() {
		return min;
	}

	@Override
	public double getAvg() {
		return avg;
	}

	@Override
	public double getVar() {
		return var;
	}

	@Override
	public double getStdDev() {
		return stdDev;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMax(T max) {
		this.max = max;
	}

	public void setMin(T min) {
		this.min = min;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public void setVar(double var) {
		this.var = var;
	}

	public void setStdDev(double stdDev) {
		this.stdDev = stdDev;
	}

}
