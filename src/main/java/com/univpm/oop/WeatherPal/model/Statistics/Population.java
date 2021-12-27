package com.univpm.oop.WeatherPal.model.Statistics;

import com.univpm.oop.WeatherPal.model.JsonSerializers.PopulationSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;

/**
 * Class that represents a group (population) of something
 * @param T : non primitive type of the population's elements
 */
@JsonSerialize(using = PopulationSerializer.class)
public class Population<T> implements Fluctuating<T> {

	public String name;
	public T max, min;
	public HashMap<String,Object> avg, var, stdDev;

	public Population(String name) {
		this.name = name;
	}

	@Override
	public T getMax() {
		return max;
	}

	@Override
	public T getMin() {
		return min;
	}

	@Override
	public HashMap<String,Object> getAvg() {
		return avg;
	}

	@Override
	public HashMap<String,Object> getVar() {
		return var;
	}

	@Override
	public HashMap<String,Object> getStdDev() {
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

	public void setAvg(HashMap<String,Object> avg) {
		this.avg = avg;
	}

	public void setVar(HashMap<String,Object> var) {
		this.var = var;
	}

	public void setStdDev(HashMap<String,Object> stdDev) {
		this.stdDev = stdDev;
	}

}
