package com.univpm.oop.WeatherPal.model.Measures;

import com.univpm.oop.WeatherPal.model.JsonSerializers.MeasureSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class that represents a generic measure
 * @param T : the concrete type for the measure value
 */
@JsonSerialize(using = MeasureSerializer.class)
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

	public void setValue(T newValue) {
		value = newValue;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String newUnit) {
		unit = newUnit;
	}

	@Override
	public String toString() {
		String toReturn = value.toString();
		if(unit != "")
			toReturn += " " + unit;
		return toReturn;
	}

}
