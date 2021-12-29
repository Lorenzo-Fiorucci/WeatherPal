package com.univpm.oop.WeatherPal.model.Statistics;

import com.univpm.oop.WeatherPal.model.tools.ReflectionTools;
import java.util.*;

public interface Distribution {

	/**
	 * 
	 * @param array : vector of {@code Number} or subclasses
	 * @return the average of {@code array}'s elements, as a double value with 4 decimal places.
	 */
	public static double simpleAvg(Vector<? extends Number> array) {
		double toReturn = 0;
		for (Number n : array)
			toReturn += n.doubleValue();
		
		return cut(toReturn/array.size(), (byte)4);
	}
	
	/**
	 * 
	 * @param array : vector of {@code Number} or subclasses
	 * @return the variance of {@code array}'s elements, as a double value with 4 decimal places.
	 */
	public static double simpleVar(Vector<? extends Number> array) {
		double toReturn = 0, avg = simpleAvg(array);
		for(Number n : array)
			toReturn += Math.pow(n.doubleValue() - avg, 2);
			
		toReturn /= (array.size() - 1);
		return cut(toReturn, (byte)4);
	}

	/**
	 * 
	 * @param array : vector of {@code Number} or subclasses
	 * @return the standard deviation of {@code array}'s elements, as a double value with 4 decimal places.
	 */
	public static double simpleStdDev(Vector<? extends Number> array) {
		
		return cut( Math.sqrt(simpleVar(array)), (byte)4 );
	}
	
	/**
	 * Method to cut the decimal digits in excess with a "to nearest" rounding logic.
	 * @param toCut : the double to cut
	 * @param decimalPlaces : the number of decimal places to keep
	 * @return {@code toCut} in which the number of decimal places is {@code decimalPlaces}.
	 */
	private static double cut(double toCut, byte decimalPlaces) {
		double bigger = toCut * Math.pow(10, decimalPlaces);
		long integer;
		if(bigger < 0)
			integer = (long) (bigger - 0.5);
		else
			integer = (long) (bigger + 0.5);
		
		return (double)integer / Math.pow(10, decimalPlaces);
	}
	
	/**
	 * Method that creates an average representation of all the objects contained in {@code array} and returns it as an hashmap. 
	 * This hashmap has the same tree node structure of the class of {@code array}'s elements: each key is an attribute name, the 
	 * corresponding value is the average of that attribute's values in the elements of {@code array}.<p>
	 * The difference with an object of the reference class is that the hasmap returned has all double values, even those who were integers. 
	 * Not numerical fields of the reference class (e.g. strings) are not present in the hashmap.
	 * @param array : a vector of objects that implement {@code Distribution} interface
	 * @return a hashmap that represents the average of all the objects contained in {@code array}. 
	 */
	public static <E> HashMap<String,Object> complexAvg(Vector<? extends Distribution> array) {
		
		HashMap<String, Object> toReturn = new HashMap<>();
		HashMap<String, Vector<E>> fieldValues = ReflectionTools.getFieldValues(array); // non Vector<Object> perche' il compilatore vuole tutti elementi Object (e non sottoclassi)
																		// quindi mi impedisce di castare a Vector<? extends Number> a riga 24.
		
		for (Map.Entry<String, Vector<E>> entry : fieldValues.entrySet()) {
			
			if(entry.getValue().get(0) instanceof Number)
				toReturn.put(entry.getKey(), simpleAvg((Vector<? extends Number>)entry.getValue()));
			else
				if(entry.getValue().get(0) instanceof Distribution)
					toReturn.put(entry.getKey(), complexAvg((Vector<? extends Distribution>)entry.getValue()));
		}
		return toReturn;
	}


	/**
	 * Method that creates a 'variance representation' of all the objects contained in {@code array} and returns it as an hashmap. 
	 * This hashmap has the same tree node structure of the class of {@code array}'s elements: each key is an attribute name, the 
	 * corresponding value is the variance of that attribute's values in the elements of {@code array}.<p>
	 * The difference with an object of the reference class is that the hasmap returned has all double values, even those who were integers. 
	 * Not numerical fields of the reference class (e.g. strings) are not present in the hashmap.
	 * @param array : a vector of objects that implement {@code Distribution} interface
	 * @return a hashmap that represents the variance of all the objects contained in {@code array}. 
	 */
	public static <E> HashMap<String,Object> complexVar(Vector<? extends Distribution> array) {

		HashMap<String, Object> toReturn = new HashMap<>();
		HashMap<String, Vector<E>> fieldValues = ReflectionTools.getFieldValues(array);

		for (Map.Entry<String, Vector<E>> entry : fieldValues.entrySet()) {
			
			if(entry.getValue().get(0) instanceof Number)
				toReturn.put(entry.getKey(), simpleVar((Vector<? extends Number>)entry.getValue()));
			else
				if(entry.getValue().get(0) instanceof Distribution)
					toReturn.put(entry.getKey(), complexVar((Vector<? extends Distribution>)entry.getValue()));
		}
		return toReturn;

	}


	/**
	 * Method that creates a 'standard deviation representation' of all the objects contained in {@code array} and returns it as an hashmap. 
	 * This hashmap has the same tree node structure of the class of {@code array}'s elements: each key is an attribute name, the 
	 * corresponding value is the standard deviation of that attribute's values in the elements of {@code array}.<p>
	 * The difference with an object of the reference class is that the hasmap returned has all double values, even those who were integers. 
	 * Not numerical fields of the reference class (e.g. strings) are not present in the hashmap.
	 * @param array : a vector of objects that implement {@code Distribution} interface
	 * @return a hashmap that represents the standard deviation of all the objects contained in {@code array}. 
	 */
	public static <E> HashMap<String,Object> complexStdDev(Vector<? extends Distribution> array) {

		HashMap<String, Object> toReturn = new HashMap<>();
		HashMap<String, Vector<E>> fieldValues = ReflectionTools.getFieldValues(array);

		for (Map.Entry<String, Vector<E>> entry : fieldValues.entrySet()) {
			
			if(entry.getValue().get(0) instanceof Number)
				toReturn.put(entry.getKey(), cut( Math.sqrt(simpleVar((Vector<? extends Number>) entry.getValue())), (byte)4 ));
			else
				if(entry.getValue().get(0) instanceof Distribution)
					toReturn.put(entry.getKey(), complexStdDev((Vector<? extends Distribution>)entry.getValue()));
		}
		return toReturn;

	}
}
