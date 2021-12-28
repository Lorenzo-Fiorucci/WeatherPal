package com.univpm.oop.WeatherPal.model.Statistics;

import java.lang.reflect.*;
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
		HashMap<String, Vector<E>> fieldValues = getFieldValues(array); // non Vector<Object> perche' il compilatore vuole tutti elementi Object (e non sottoclassi)
																		// quindi mi impedisce di castare a Vector<? extends Number> a riga 24.
		
		for (Map.Entry<String, Vector<E>> entry : fieldValues.entrySet()) {
			
			if(entry.getValue().get(0) instanceof Number)
				toReturn.put(entry.getKey(), simpleAvg((Vector<? extends Number>)entry.getValue()));
			else
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
		HashMap<String, Vector<E>> fieldValues = getFieldValues(array);

		for (Map.Entry<String, Vector<E>> entry : fieldValues.entrySet()) {
			
			if(entry.getValue().get(0) instanceof Number)
				toReturn.put(entry.getKey(), simpleVar((Vector<? extends Number>)entry.getValue()));
			else
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
		HashMap<String, Vector<E>> fieldValues = getFieldValues(array);

		for (Map.Entry<String, Vector<E>> entry : fieldValues.entrySet()) {
			
			if(entry.getValue().get(0) instanceof Number)
				toReturn.put(entry.getKey(), cut( Math.sqrt(simpleVar((Vector<? extends Number>) entry.getValue())), (byte)4 ));
			else
				toReturn.put(entry.getKey(), complexStdDev((Vector<? extends Distribution>)entry.getValue()));
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
	 * @param klazz : referece class
	 * @return a {@code Field} array containing all the attribbutes of {@code klazz} class, even those inherited
	 */
	private Field[] getVisibleFields(Class<?> klazz) {
		
		Field[] thisFields = klazz.getDeclaredFields();
		
		while(!klazz.getSuperclass().equals(Object.class)) {
		
			Field[] superFields = klazz.getSuperclass().getDeclaredFields();
			Field[] allFields = new Field[thisFields.length + superFields.length];

			for(int i = 0; i < thisFields.length; i++)
				allFields[i] = thisFields[i];
			
			for(int i = thisFields.length; i < allFields.length; i++) {
				if(Modifier.isPublic(superFields[i - thisFields.length].getModifiers()) || Modifier.isProtected(superFields[i - thisFields.length].getModifiers()))
					allFields[i] = superFields[i - thisFields.length];
			}
			
			thisFields = new Field[allFields.length];
			for (int i = 0; i < allFields.length; i++) // thisField becomes the same as allFields
				thisFields[i] = allFields[i];
			
			klazz = klazz.getSuperclass(); //klazz turns into its superclass
		}
		return thisFields;
	}

}
