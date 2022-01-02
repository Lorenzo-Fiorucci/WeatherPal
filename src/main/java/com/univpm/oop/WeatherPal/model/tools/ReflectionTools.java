package com.univpm.oop.WeatherPal.model.tools;

import java.util.*;
import java.lang.reflect.*;

/**
 * Class with static methods that use reflection to retrieve certain elements of a class without knowing what class it is
 */
public class ReflectionTools {
	
	/**
	 * @param array : a vector of objects that implement {@code Distribution} interface
	 * @return
	 * 		a hashmap with a string-Vector couple for each attribute of {@code array}'s elements
	 * 		(attributes that have a calem case getter).<p>
	 *		Keys (String) identify attributes' names.
	 * 		Corresponding values (Vector) identify the values of those attributes in the elements of {@code array}.
	 */
	public static <E, T> HashMap<String, Vector<E>> getFieldValues(Vector<T> array) {
		
		HashMap<String, Vector<E>> fieldValues = new HashMap<>(); // come Vector<Vector<?>>, ma ogni Vector interno ha un nome associato
		
		Class<?> klazz = array.get(0).getClass();
		Field[] fields = getVisibleFields(klazz);
		
		for (T element : array) {
			for (Field field : fields) {
				try {
					String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
					Method getField = getVisibleMethod(klazz, getterName); // getter del field attuale
					Vector<E> toPut; // vettore da assegnare alla chiave di fieldValues corrispondente all'attributo attuale
					
					if(!fieldValues.containsKey(field.getName()))
						toPut = new Vector<>();
					else
						toPut = fieldValues.get(field.getName());
					
					Object toAdd = getField.invoke(element);
					if(toAdd != null) {
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
	public static Field[] getVisibleFields(Class<?> klazz) {
		
		Field[] thisFields = klazz.getDeclaredFields();
		
		while(!klazz.getSuperclass().equals(Object.class)) { // finche' la superclasse non e' object, aggiungi i suoi campi public o protected
		
			Field[] superFields = klazz.getSuperclass().getDeclaredFields();
			Field[] allFields = new Field[thisFields.length + superFields.length];

			for(int i = 0; i < thisFields.length; i++)
				allFields[i] = thisFields[i];
			
			for(int i = thisFields.length; i < allFields.length; i++) {
				if(Modifier.isPublic(superFields[i - thisFields.length].getModifiers()) || Modifier.isProtected(superFields[i - thisFields.length].getModifiers()))
					allFields[i] = superFields[i - thisFields.length];
			}
			
			thisFields = new Field[allFields.length];
			for (int i = 0; i < allFields.length; i++) // thisField diventa uguale a allFields
				thisFields[i] = allFields[i];		   //
			
			klazz = klazz.getSuperclass(); // klazz diventa la sua superclasse
		}
		return thisFields;
	}

	/**
	 * 
	 * @param klazz : reference class
	 * @param methodName : name of the method to return
	 * @return a {@code Method} object representing the searched method of the class {@code klazz}, even if inherited
	 * @throws NoSuchMethodException 
	 */
	public static Method getVisibleMethod(Class<?> klazz, String methodName) throws NoSuchMethodException {

		Method toReturn = null;
		boolean inCatch = true;
		Class<?> actualClass = klazz;
		
		while(!actualClass.equals(Object.class) && inCatch) {
			inCatch = false;
			try {
				Method toAssign = actualClass.getDeclaredMethod(methodName);
				if(actualClass.equals(klazz))
					toReturn = toAssign;
				else if(Modifier.isPublic(toAssign.getModifiers()) || Modifier.isProtected(toAssign.getModifiers())) // se actualClass non e' klazz (ma una superclasse)
					toReturn = toAssign;																			// controllo che il metodo sia public o protected
				else throw new NoSuchMethodException(); // cosi' entro nel catch che mi prepara al nuovo ciclo
																						
			} catch (NoSuchMethodException e) {
				actualClass = actualClass.getSuperclass();
				inCatch = true;
			}
		}
		if(toReturn == null) // se fuori dal while 'toReturn' non e' stato assegnato, cerco il metodo in Object; se non esiste neanche
			toReturn = Object.class.getDeclaredMethod(methodName); // in Object, viene lanciata l'eccezione NoSuchMethodException
		
		return toReturn;
	}

}
