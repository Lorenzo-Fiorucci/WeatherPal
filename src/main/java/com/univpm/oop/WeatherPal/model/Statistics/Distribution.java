package com.univpm.oop.WeatherPal.model.Statistics;

import java.lang.reflect.*;
import java.util.*;

public interface Distribution {
	
	/**
	 * Method that creates an average representation of all the objects contained in {@code array} and returns it as an hashmap. 
	 * This hashmap has the same tree node structure of the class of {@code array}'s elements: each key is an attribute name, the 
	 * corresponding value is the average of that attribute's values in the elements of {@code array}.<p>
	 * The difference with an object of the reference class is that the hasmap returned has all double values, even those who were integers. 
	 * Not numerical fields of the reference class (e.g. strings) are not present in the hashmap.
	 * @param array : a vector of objects that implement {@code Distribution} interface
	 * @return a hashmap that represents the average of all the objects contained in {@code array}. 
	 */
	public static <E> HashMap<String,Object> calcAvg(Vector<? extends Distribution> array) {
		
		HashMap<String, Object> toReturn = new HashMap<>();
		HashMap<String, Vector<E>> fieldValues = getFieldValues(array); // non Vector<Object> perche' il compilatore vuole tutti elementi Object (e non sottoclassi)
																		// quindi mi impedisce di castare a Vector<? extends Number> a riga 24.
		
		for (Map.Entry<String, Vector<E>> entry : fieldValues.entrySet()) {
			
			if(entry.getValue().get(0) instanceof Number)
				toReturn.put(entry.getKey(), average((Vector<? extends Number>)entry.getValue()));
			else
				toReturn.put(entry.getKey(), calcAvg((Vector<? extends Distribution>)entry.getValue()));
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
	public static <E> HashMap<String,Object> calcVar(Vector<? extends Distribution> array) {

		HashMap<String, Object> toReturn = new HashMap<>();
		HashMap<String, Vector<E>> fieldValues = getFieldValues(array);

		for (Map.Entry<String, Vector<E>> entry : fieldValues.entrySet()) {
			
			if(entry.getValue().get(0) instanceof Number)
				toReturn.put(entry.getKey(), variance((Vector<? extends Number>)entry.getValue()));
			else
				toReturn.put(entry.getKey(), calcVar((Vector<? extends Distribution>)entry.getValue()));
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
	public static <E> HashMap<String,Object> calcStdDev(Vector<? extends Distribution> array) {

		HashMap<String, Object> toReturn = new HashMap<>();
		HashMap<String, Vector<E>> fieldValues = getFieldValues(array);

		for (Map.Entry<String, Vector<E>> entry : fieldValues.entrySet()) {
			
			if(entry.getValue().get(0) instanceof Number)
				toReturn.put(entry.getKey(), cut( Math.sqrt(variance((Vector<? extends Number>) entry.getValue())), (byte)4 ));
			else
				toReturn.put(entry.getKey(), calcStdDev((Vector<? extends Distribution>)entry.getValue()));
		}
		return toReturn;

	}


	/**
	 * @param array : a vector of objects that implement {@code Distribution} interface
	 * @return
	 * 		a hashmap with a string-Vector couple for each attribute of {@code array}'s elements.
	 * 		(attributes that: (1) have a calem case getter,
	 *						  (2) extend Number or implement Distribution).<p>
	 *		Keys (String) identify attributes' names.
	 * 		Corresponding values (Vector) identify the values of those attributes in the elements of {@code array}.
	 */
	public static <E> HashMap<String, Vector<E>> getFieldValues(Vector<? extends Distribution> array) {
		
		HashMap<String, Vector<E>> fieldValues = new HashMap<>(); // come Vector<Vector<?>>, ma ogni Vector interno ha un nome associato
		
		Class<?> klazz = array.get(0).getClass();
		Field[] fields = klazz.getDeclaredFields();
		
		for (Distribution element : array) {
			for (Field field : fields) {
				try {
					Method getField = klazz.getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)); //getField del field attuale
					Vector<E> toPut; // vettore da assegnare alla chiave di fieldValues corrispondente all'attributo attuale
					
					if(!fieldValues.containsKey(field.getName()))
						toPut = new Vector<>();
					else
						toPut = fieldValues.get(field.getName());
					
					Object toAdd = getField.invoke(element);
					if(toAdd != null && (toAdd instanceof Number || toAdd instanceof Distribution)) { // controllo che quell'attributo non sia nullo e che estenda Number o Distribution
						toPut.add( (E)toAdd );
						fieldValues.put(field.getName(), toPut);
					}
				}catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					System.out.println(e);
				}
			}
		}
		return fieldValues;
	}

	/**
	 * 
	 * @param array : vector of {@code Number} or subclasses
	 * @return the average of {@code array}'s elements, as a double value with 4 decimal places.
	 */
	private static double average(Vector<? extends Number> array) {
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
	private static double variance(Vector<? extends Number> array) {
		double toReturn = 0, avg = average(array);
		for(Number n : array)
			toReturn += Math.pow(n.doubleValue() - avg, 2);
			
		toReturn /= (array.size() - 1);
		return cut(toReturn, (byte)4);
	}

	private static double cut(double toCut, byte decimalPlaces) {
		double bigger = toCut * Math.pow(10, decimalPlaces);
		long integer;
		if(bigger < 0)
			integer = (long) (bigger - 0.5);
		else
			integer = (long) (bigger + 0.5);
		
		return (double)integer / Math.pow(10, decimalPlaces);
	}
}
