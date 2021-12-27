package com.univpm.oop.WeatherPal.model.Statistics;

import com.univpm.oop.WeatherPal.model.Measures.*;
import com.univpm.oop.WeatherPal.model.tools.MeasuresAnalyzer;
import com.univpm.oop.WeatherPal.exceptions.EmptyVectorException;
import com.univpm.oop.WeatherPal.model.Filters.DailyPeriod;
import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;
import com.univpm.oop.WeatherPal.model.Forecast.*;

import java.util.*;
import java.lang.reflect.*;
import java.time.*;

/**
 * Class that stores all the statistics of a set of forecast events
 */
public class Stats {
	
	Population<? extends Measure<Double>> temp;
	Population<? extends Measure<Double>> feelsLike;
	Population<? extends Measure<Byte>> humidity;
	Population<? extends Measure<Integer>> wind;
	Population<? extends Measure<Integer>> pressure;
	Population<? extends Measure<Byte>> clouds;
	Population<? extends Measure<AirPollution>> airPoll;
	DailyPeriod period;

	/**
	 * Constructs a stats object containing all the statistics on a set of {@code Forecast} (or subclasses), 
	 * passed with a {@code Vector}.<p>
	 * If is passed a vector of {@code DailyForecast}, then the attributes of this object will be objects of
	 * {@code Population<DailyMeasure<? extends Number>>} class.<p>
	 * If is passed a vector of {@code HourlyForecast}, then the attributes of this object will be objects of
	 * {@code Population<InstantMeasure<? extends Number>>} class.
	 * 
	 * @param forecasts
	 * 		: vector of {@code Forecast} or subclasses, that contains the dataset of measures
	 * @throws EmptyVectorException
	 * 		if {@code forecasts} is empty
	 */

	public Stats(Vector<? extends Forecast> forecasts) throws EmptyVectorException {
		
		if(forecasts.isEmpty())
			throw new EmptyVectorException("Passed empty forecasts vector to Stats' constructor.");

		HashMap<String, ? extends Vector<? extends Measure<?>>> measuresMap = getMeasures(forecasts);
		
		if (forecasts.get(0) instanceof HourlyForecast)
		{
			LocalDateTime start = LocalDateTime.of(forecasts.get(0).getDate(), ((HourlyForecast)forecasts.get(0)).getTime());
			LocalDateTime end = LocalDateTime.of(forecasts.get(forecasts.size()-1).getDate(), ((HourlyForecast)forecasts.get(forecasts.size()-1)).getTime());
			period = new HourlyPeriod(start, end);
			
			temp = new Population<InstantMeasure<Double>>("Temperature");
			feelsLike = new Population<InstantMeasure<Double>>("Feels like");
			humidity = new Population<InstantMeasure<Byte>>("Humidity");
			wind = new Population<InstantMeasure<Integer>>("Wind");
			pressure = new Population<InstantMeasure<Integer>>("Pressure");
			clouds = new Population<InstantMeasure<Byte>>("Clouds");
			airPoll = new Population<InstantMeasure<AirPollution>>("Air Pollution");

			setAll((Population<InstantMeasure<Double>>) temp, (Vector<InstantMeasure<Double>>) measuresMap.get("temp"));
			setAll((Population<InstantMeasure<Double>>) feelsLike, (Vector<InstantMeasure<Double>>) measuresMap.get("feelsLike"));
			setAll((Population<InstantMeasure<Byte>>) humidity, (Vector<InstantMeasure<Byte>>) measuresMap.get("humidity"));
			setAll((Population<InstantMeasure<Integer>>) wind, (Vector<InstantMeasure<Integer>>) measuresMap.get("wind"));
			setAll((Population<InstantMeasure<Integer>>) pressure, (Vector<InstantMeasure<Integer>>) measuresMap.get("pressure"));
			setAll((Population<InstantMeasure<Byte>>) clouds, (Vector<InstantMeasure<Byte>>) measuresMap.get("clouds"));
			setAll((Population<InstantMeasure<AirPollution>>) airPoll, (Vector<InstantMeasure<AirPollution>>) measuresMap.get("airPoll"));
		}
		else if (forecasts.get(0) instanceof DailyForecast)
		{
			LocalDate start = forecasts.get(0).getDate();
			LocalDate end = forecasts.get(forecasts.size()-1).getDate();
			period = new DailyPeriod(start, end);
			
			temp = new Population<DailyMeasure<Double>>("Temperature");					
			feelsLike = new Population<DailyMeasure<Double>>("Feels like");
			humidity = new Population<DailyMeasure<Byte>>("Humidity");
			wind = new Population<DailyMeasure<Integer>>("Wind");
			pressure = new Population<DailyMeasure<Integer>>("Pressure");
			clouds = new Population<DailyMeasure<Byte>>("Clouds");
			airPoll = new Population<DailyMeasure<AirPollution>>("Air pollution");

			setAll((Population<DailyMeasure<Double>>) temp, (Vector<DailyMeasure<Double>>) measuresMap.get("temp"));
			setAll((Population<DailyMeasure<Double>>) feelsLike, (Vector<DailyMeasure<Double>>) measuresMap.get("feelsLike"));
			setAll((Population<DailyMeasure<Byte>>) humidity, (Vector<DailyMeasure<Byte>>) measuresMap.get("humidity"));
			setAll((Population<DailyMeasure<Integer>>) wind, (Vector<DailyMeasure<Integer>>) measuresMap.get("wind"));
			setAll((Population<DailyMeasure<Integer>>) pressure, (Vector<DailyMeasure<Integer>>) measuresMap.get("pressure"));
			setAll((Population<DailyMeasure<Byte>>) clouds, (Vector<DailyMeasure<Byte>>) measuresMap.get("clouds"));
			setAll((Population<DailyMeasure<AirPollution>>) airPoll, (Vector<DailyMeasure<AirPollution>>) measuresMap.get("airPoll"));
		}
	}

	
	/**
	 * @param <E> E : 
	 * 		{@code InstantMeasure} if {@code forecasts} is a vector of {@code HourlyForecast}, 
	 * 		{@code DailyMeasure} if {@code forecasts} is a vector of {@code DailyForecast}.
	 * @param forecasts 
	 * 		: vector of {@code HourlyForecast} or {@code DailyForecast}
	 * @return a hashmap.<p> 
	 * 		In each couple {@code String}-{@code Vector<E>} : the key is the name of a {@code Forecast} attribute of the class {@code Measure},
	 * 		the corresponding value ({@code Vector<E>}) is the list of all the measures of that attribute in {@code forecasts}' elements.
	 */
	private <T, E extends Measure<T>> HashMap<String,Vector<E>> getMeasures(Vector<? extends Forecast> forecasts) {

		// il caso in cui forecasts e' vuoto e' stato gia' gestito, nella prima riga del costruttore
		
		Class<? extends Forecast> klazz = forecasts.get(0).getClass();
		Field[] fields = klazz.getDeclaredFields();
		
		HashMap<String,Vector<E>> measuresMap = new HashMap<>();
		
		LocalDate date;
		LocalTime time = null;
		
		for(Forecast forecast : forecasts) {
			
			date = forecast.getDate();
			if(forecast instanceof HourlyForecast)
				time = ((HourlyForecast)forecast).getTime();
			
			for(Field field : fields) {
				if(field.getType().getSimpleName().equals("Measure") && !field.getName().equals("pop") && !field.getName().equals("uv")) {
					try {
						Method getField = klazz.getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
						Measure<T> newMeasure;
						if(time != null)
							newMeasure = new InstantMeasure<T>((Measure<T>)getField.invoke(forecast), date, time);
						else
							newMeasure = new DailyMeasure<T>((Measure<T>)getField.invoke(forecast), date);
						
						Vector<E> toPut;
						if(!measuresMap.containsKey(field.getName()))
							toPut = new Vector<>();
						else
							toPut = measuresMap.get(field.getName());
						
						toPut.add((E)newMeasure); // il tipo effettivo di newMeasure estende Measure<T> e corrisponde ad E, per ogni coppia String-Vector:
												  // se forecasts e' un vettore di HourlyForecast, newMeasure sara' sempre un'InstantMeasure<T>;
												  // se forecasts e' un vettore di DailyForecast, newMeasure sara' sempre una DailyMeasure<T> (con T che varia in base al field)
						
						 measuresMap.put(field.getName(), toPut);
					
					} catch (NoSuchMethodException e) {
						System.out.println(e);
					} catch (IllegalAccessException | InvocationTargetException e) {
						System.out.println(e);
					}
				}
			}
		}
		return measuresMap;
	}


	/**
	 * 
	 * @param <Z> Z : whatever class
	 * @param <T> T : class that extends a {@code Measure} of some value that implements {@code Comparable} interface
	 * @param pop : {@code Population} of {@code T} to be completely set (set max, min, average, variance, standard deviation)
	 * @param measures : vector of {@code T} that contains the data on which calculate max, min, average,variance and standard deviation
	 */
	private <Z, T extends Measure<? extends Comparable<Z>>, X extends Number & Comparable<Z>, Y extends Distribution & Comparable<Z>>
			void setAll(Population<T> pop, Vector<T> measures) {
		
			pop.setMax((T) MeasuresAnalyzer.findMax(measures)); // findMax() e findMin() ritornano una Measure<? extends Comparable>, ma quello e' il tipo apparente
			pop.setMin((T) MeasuresAnalyzer.findMin(measures)); // poiche' il tipo effettivo (Daily o HourlyMeasure) dipende dal vettore di misure (measures).
			
			if(measures.get(0).getValue() instanceof Number) { // se measures e' vettore di misure con valori che estendono Number (oltre che implementare Comparable)
				
				HashMap<String, Object> toSet = new HashMap<>();
				toSet.put("value", MeasuresAnalyzer.calcNumAvg((Vector<? extends Measure<X>>) measures)); 
				pop.setAvg(toSet);
				
				toSet.put("value", MeasuresAnalyzer.calcNumVar((Vector<? extends Measure<X>>) measures));
				pop.setVar(toSet);
				
				toSet.put("value", MeasuresAnalyzer.calcNumStdDev((Vector<? extends Measure<X>>) measures));
				pop.setVar(toSet);
			}
			else if (measures.get(0).getValue() instanceof Distribution) { // se measures e' vettore di misure che implementano Distribution (oltre che Comparable) 
				
				pop.setAvg(MeasuresAnalyzer.calcDistribAvg((Vector<? extends Measure<Y>>)measures));
				pop.setVar(MeasuresAnalyzer.calcDistribVar((Vector<? extends Measure<Y>>)measures));
				pop.setStdDev(MeasuresAnalyzer.calcDistribStdDev((Vector<? extends Measure<Y>>)measures));
			}
	}

}
