package com.univpm.oop.WeatherPal.model.tools;

import com.univpm.oop.WeatherPal.model.Measures.Measure;
import com.univpm.oop.WeatherPal.model.Statistics.Distribution;

import java.util.*;

/**
 * Class to find max, min, average, variance and standard deviation of a given vector of {@code Measure} (or suubclasses)
 */
public class MeasuresAnalyzer {

	/**
	 * Method to get the maximum element of a vector of {@code Measure} (or subclasses). The values of these measures must implement
	 * {@code Comparable} interface, because vector's elements are compared using {@code Comparable}'s {@code compareTo} method.
	 * @param <T> T : class that implements {@code Comparable} interface
	 * @param array : vector of {@code Measure} (or subclasses) with values that implements {@code Comparable} interface
	 * @return the maximum element of {@code array}
	 */
	public static <T> Measure<? extends Comparable<T>> findMax(Vector<? extends Measure<? extends Comparable<T>>> array) {
		
		int maxIndex = 0;

		for (int i = 1; i < array.size(); i++) {
			if(array.get(i).getValue().compareTo((T)array.get(maxIndex).getValue()) > 0) // getValue() restituisce un oggetto che implementa
				maxIndex = i;												// Comparable<se_stesso>, quindi ha senso castare a se_stesso (T)
		}
		return array.get(maxIndex);
	}

	/**
	 * Method to get the minimum element of a vector of {@code Measure} (or subclasses). The values of these measures must implement
	 * {@code Comparable} interface, because vector's elements are compared using {@code Comparable}'s {@code compareTo} method.
	 * @param <T> T : class that implements {@code Comparable} interface
	 * @param array : vector of {@code Measure} (or subclasses) with values that implements {@code Comparable} interface
	 * @return the minumum element of array
	 */
	public static  <T> Measure<? extends Comparable<T>> findMin(Vector<? extends Measure<? extends Comparable<T>>> array) {

		int minIndex = 0;

		for (int i = 1; i < array.size(); i++) {
			if(array.get(i).getValue().compareTo((T)array.get(minIndex).getValue()) < 0) // getValue() restituisce un oggetto che implementa
				minIndex = i;												// Comparable<se_stesso>, quindi ha senso castare a se_stesso (T)
		}
		return array.get(minIndex);
	}

	/**
	 * 
	 * @param array : vector of {@code Measure} (or subclasses) of {@code Number} (or subclasses)
	 * @return the average of the values of {@code array}'s measures, as a double
	 */
	public static double numAvg(Vector<? extends Measure<? extends Number>> array) {
		
		return Distribution.simpleAvg(getValues((Vector) array));
	}

	/**
	 * 
	 * @param array : vector of {@code Measure} (or subclasses) of {@code Number} (or subclasses)
	 * @return the varince of the values of {@code array}'s measures, as a double
	 */
	public static double numVar(Vector<? extends Measure<? extends Number>> array) {
		
		return Distribution.simpleVar(getValues((Vector) array));
	}

	/**
	 * 
	 * @param array : vector of {@code Measure} (or subclasses) of {@code Number} (or subclasses)
	 * @return the standard deviation of the values of {@code array}'s measures, as a double
	 */
	public static double numStdDev(Vector<? extends Measure<? extends Number>> array) {
		
		return Distribution.simpleStdDev(getValues((Vector) array));
	}

	/**
	 * Method that creates an average representation of all the objects in the 'value' field of {@code array}'s measures 
	 * and returns it as an hashmap. This hashmap has the same tree node structure of the class of the values of {@code array}'s measures: 
	 * each key is the name of an attribute of that class, the corresponding value is the average of that attribute's values 
	 * in the measures of {@code array}.<p>
	 * The difference with an object of that class is that the hasmap has all double values, even those who were integers. 
	 * Not numerical fields of the reference class (e.g. strings) are not present in the hashmap.
	 * @param array 
	 * 		: a vector of {@code Measure}s (or subclasses). The 'value' field of those measures must be a class 
	 * 		that implements {@code Distribution} interface
	 * @return a hashmap that represents the average of the values of all the measures contained in {@code array}. 
	 */
	public static HashMap<String, Object> distribAvg(Vector<? extends Measure<? extends Distribution>> array) {
		
		return Distribution.complexAvg(getValues((Vector) array));
	}


	/**
	 * Method that creates a ''varince representation'' of all the objects in the 'value' field of {@code array}'s measures 
	 * nd returns it as an hashmap. This hashmap has the same tree node structure of the class of the values of {@code array}'s measures: 
	 * each key is the name of an attribute of that class, the corresponding value is the variance of that attribute's values in
	 * the measures of {@code array}.<p> 
	 * The difference with an object of that class is that the hasmap has all double values, even those who were integers. 
	 * Not numerical fields of the reference class (e.g. strings) are not present in the hashmap.
	 * @param array 
	 * 		: a vector of {@code Measure}s (or subclasses). The 'value' field of those measures must be a class 
	 * 		that implements {@code Distribution} interface
	 * @return a hashmap that represents the variance of the values of all the measures contained in {@code array}. 
	 */
	public static HashMap<String, Object> distribVar(Vector<? extends Measure<? extends Distribution>> array) {
		
		return Distribution.complexVar(getValues((Vector) array));
	}


	/**
	 * Method that creates a ''standard deviation representation'' of all the objects in the 'value' field of {@code array}'s measures 
	 * and returns it as an hashmap. This hashmap has the same tree node structure of the class of the values of {@code array}'s measures: 
	 * each key is the name of an attribute of that class, the corresponding value is the standard deviation of that attribute's values
	 * in the measures of {@code array}.<p>
	 * The difference with an object of that class is that the hasmap has all double values, even those who were integers. 
	 * Not numerical fields of the reference class (e.g. strings) are not present in the hashmap.
	 * @param array 
	 * 		: a vector of {@code Measure}s (or subclasses). The 'value' field of those measures must be a class 
	 * 		that implements {@code Distribution} interface
	 * @return a hashmap that represents the standard deviation of the values of all the measures contained in {@code array}. 
	 */
	public static HashMap<String, Object> distribStdDev(Vector<? extends Measure<? extends Distribution>> array) {
		
		return Distribution.complexStdDev(getValues((Vector) array));
	}

	/**
	 * Method to get a vector of certain values from a vector of measures of those values
	 * @param <T> T : class of the values of array's measures
	 * @param array : vector of {@code Measure} (or subclasses) of {@code T}
	 * @return a vector of only the values of array's measures 
	 */
	private static <T> Vector<T> getValues(Vector<? extends Measure<T>> array) {
		
		Vector<T> values = new Vector<>();
		for (Measure<T> m : array)
			values.add(m.getValue());
		
		return values;
	}
	
}
