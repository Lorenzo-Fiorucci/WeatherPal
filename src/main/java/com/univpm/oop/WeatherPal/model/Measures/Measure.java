package com.univpm.oop.WeatherPal.model.Measures;

public class Measure<T> {
	
	T value;
	String unit;

	/**
	 * 
	 * @param value : value of the measure, which has the non-primitive type {@code T}
	 * @param unit : unit of measure (kg, degrees, metres, etc.)
	 */
	public Measure(T value, String unit) {
		this.value = value;
		this.unit = unit;
	}

	/**
	 * 
	 * @param value : value of the measure, which has the non-primitive type {@code T} 
	 */
	public Measure(T value) {
		this(value, "");
	}

	@Override
	public String toString() { //TODO aggiusta con if per unit
		return "value: " + value + " " + unit;
	}

}
