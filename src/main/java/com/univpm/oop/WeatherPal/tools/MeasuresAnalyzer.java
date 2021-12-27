package com.univpm.oop.WeatherPal.tools;

import java.lang.Math;
import java.util.*;

import com.univpm.oop.WeatherPal.model.Measures.Measure;
import com.univpm.oop.WeatherPal.model.Statistics.Distribution;

public class MeasuresAnalyzer {

	public static <T> Measure<? extends Comparable<T>> findMax(Vector<? extends Measure<? extends Comparable<T>>> array) {
		
		int maxIndex = 0;

		for (int i = 1; i < array.size(); i++) {
			if(array.get(i).getValue().compareTo((T)array.get(maxIndex).getValue()) > 0) // getValue() restituisce un oggetto che implementa
				maxIndex = i;												// Comparable<se_stesso>, quindi ha senso castare a se_stesso (T)
		}
		return array.get(maxIndex);
	}

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
	public static double calcNumAvg(Vector<? extends Measure<? extends Number>> array) {
		
		Vector<? extends Number> values = getValues(array);
		return average(values);
	}

	/**
	 * 
	 * @param array : vector of {@code Measure} (or subclasses) of {@code Number} (or subclasses)
	 * @return the varince of the values of {@code array}'s measures, as a double
	 */
	public static double calcNumVar(Vector<? extends Measure<? extends Number>> array) {
		
		Vector<? extends Number> values = getValues(array);
		return variance(values);
	}

	/**
	 * 
	 * @param array : vector of {@code Measure} (or subclasses) of {@code Number} (or subclasses)
	 * @return the standard deviation of the values of {@code array}'s measures, as a double
	 */
	public static double calcNumStdDev(Vector<? extends Measure<? extends Number>> array) {
		
		Vector<? extends Number> values = getValues(array);
		return Math.sqrt(variance(values));
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
	public static HashMap<String, Object> calcDistribAvg(Vector<? extends Measure<? extends Distribution>> array) {
		
		Vector<? extends Distribution> values = getValues(array);
		return Distribution.calcAvg(values);
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
	public static HashMap<String, Object> calcDistribVar(Vector<? extends Measure<? extends Distribution>> array) {
		
		Vector<? extends Distribution> values = getValues(array);
		return Distribution.calcVar(values);
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
	public static HashMap<String, Object> calcDistribStdDev(Vector<? extends Measure<? extends Distribution>> array) {
		
		Vector<? extends Distribution> values = getValues(array);
		return Distribution.calcStdDev(values);
	}

	
	/**
	 * 
	 * @param array : vector of {@code Number} or subclasses
	 * @return the average of {@code array}'s elements, as a double value.
	 */
	private static double average(Vector<? extends Number> array) {

		double toReturn = 0;
		for (Number n : array)
			toReturn += n.doubleValue();
		
			return toReturn / array.size();
	}

	/**
	 * 
	 * @param array : vector of {@code Number} or subclasses
	 * @return the variance of {@code array}'s elements, as a double value.
	 */
	private static double variance(Vector<? extends Number> array) {
		
		double toReturn = 0, avg = average(array);
		for (Number n : array)
			toReturn += Math.pow(n.doubleValue() - avg, 2);
		
		return toReturn /= array.size() - 1;
	}

	private static <T> Vector<T> getValues(Vector<? extends Measure<T>> array) {
		
		Vector<T> values = new Vector<>();
		for (Measure<T> m : array)
			values.add(m.getValue());
		
		return values;
	}

	/**
	 * 
	 * @param <T> T : class of measures' values of {@code array}
	 * @param array : vector of {@code Measure} (or subclasses) of T
	 * @return 
	 * 		the average of the objects that correspond to 'value' field of {@code array}'s measures. It's an object of the generic
	 * 		type {@code T} and its fields are the average of corresponding fields in each {@code value} attribute of {@code array}'s measures
	 * @throws EmptyVectorException
	 * 		if {@code array} is empty
	 * @throws NotAmmittedClassesException
	 * 		if {@code T} isn't AirPollution
	 */
/*	public static <T> T calcObjAvg(Vector<? extends Measure<T>> array) throws EmptyVectorException, NotAmmittedClassesException {
		
		if(array.isEmpty())
			throw new EmptyVectorException("ERROE: empty vector passed to calcObjAvg.");

		if(array.get(0).getValue() instanceof AirPollution) { // controllo se il tipo effettivo di T e' AirPollution
			
			Vector<AirPollution> pollutions = new Vector<>();
			for (Measure<T> m : array) {
				pollutions.add((AirPollution) m.getValue()); // ha senso perche' m.getValue() ritorna un T, che ha come tipo effettivo AirPollution
			}
			return (T) AirPollAnalyzer.calcAvg(pollutions);
		}
		else throw new NotAmmittedClassesException("Can't calculate the average of measures of the given class");
	}
*/
	/**
	 * 
	 * @param <T> T : class of measures' values of {@code array}
	 * @param array : vector of {@code Measure} (or subclasses) of T
	 * @return
	 * 		the variance of the objects that correspond to 'value' field of {@code array}'s measures. It's an object of the generic
	 * 		type {@code T} and its fields are the variance of corresponding fields in each {@code value} attribute of {@code array}'s measures
	 * @throws EmptyVectorException
	 * 		if {@code array} is empty
	 * @throws NotAmmittedClassesException
	 * 		if {@code T} isn't AirPollution
	 */
/*	public static <T> T calcObjVar(Vector<? extends Measure<T>> array) throws EmptyVectorException, NotAmmittedClassesException {
		
		if(array.isEmpty())
			throw new EmptyVectorException("ERROE: empty vector passed to calcObjVar.");

			if(array.get(0).getValue() instanceof AirPollution) { // controllo se il tipo effettivo di T e' AirPollution
			
			Vector<AirPollution> pollutions = new Vector<>();
			for (Measure<T> m : array) {
				pollutions.add((AirPollution) m.getValue()); // ha senso perche' m.getValue() ritorna un T, che ha come tipo effettivo AirPollution
			}
			return (T) AirPollAnalyzer.calcVar(pollutions);
		}
		else throw new NotAmmittedClassesException("Can't calculate the variance of measures of the given class");
	}
*/
	
}
