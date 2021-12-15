package com.univpm.oop.WeatherPal.model.Measures;

/**
 * Class that represents a generic measure
 * @param T : the concrete type for the measure value. It must be a non-primitive type
 */
public class Measure<T> {
	
	protected T value;
	protected String unit;

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

	public T getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	@Override
	public String toString() { //TODO aggiusta con if per unit
		return "value: " + value + " " + unit;
	}

}
