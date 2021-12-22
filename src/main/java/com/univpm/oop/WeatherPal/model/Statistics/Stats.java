package com.univpm.oop.WeatherPal.model.Statistics;

import com.univpm.oop.WeatherPal.model.Measures.*;
import com.univpm.oop.WeatherPal.exceptions.EmptyVectorException;
import com.univpm.oop.WeatherPal.model.Forecast.*;
import com.univpm.oop.WeatherPal.tools.MeasuresAnalyzer;

import java.util.Vector;

/**
 * Class that stores all the statistics of a set of forecast events
 */
public class Stats {
	
	Population<? extends Measure<Double>> temp;
	Population<? extends Measure<Double>> feelsLike;
	Population<? extends Measure<Byte>> humidity;
	Population<? extends Measure<Integer>> wind;
	Population<? extends Measure<Integer>> pressure;
	Population<? extends Measure<Integer>> airPoll;
	Population<? extends Measure<Byte>> clouds;

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

		if (forecasts.get(0) instanceof HourlyForecast)
		{
			temp = new Population<InstantMeasure<Double>>("Temperature");
			feelsLike = new Population<InstantMeasure<Double>>("Feels like");
			humidity = new Population<InstantMeasure<Byte>>("Humidity");
			wind = new Population<InstantMeasure<Integer>>("Wind");
			pressure = new Population<InstantMeasure<Integer>>("Pressure");
			airPoll = new Population<InstantMeasure<Integer>>("Air pollution");
			clouds = new Population<InstantMeasure<Byte>>("Clouds");
		}

		else if (forecasts.get(0) instanceof DailyForecast)
		{
			temp = new Population<DailyMeasure<Double>>("Temperature");
			feelsLike = new Population<DailyMeasure<Double>>("Feels like");
			humidity = new Population<DailyMeasure<Byte>>("Humidity");
			wind = new Population<DailyMeasure<Integer>>("Wind");
			pressure = new Population<DailyMeasure<Integer>>("Pressure");
			airPoll = new Population<DailyMeasure<Integer>>("Air pollution");
			clouds = new Population<DailyMeasure<Byte>>("Clouds");
		} 
		
		setAll(temp, "temp", forecasts);
		setAll(feelsLike, "feelsLike", forecasts);
		setAll(humidity, "humidity", forecasts);
		setAll(wind, "wind", forecasts);
		setAll(pressure, "press", forecasts);
		setAll(airPoll, "airPollution", forecasts);
		setAll(clouds, "clouds", forecasts);
	}


	/**
	 * Method that sets max, min, average, variance and standard deviation of a {@code Population} of {@code Measure} or subclasses
	 * @param <Z>
	 * 		Z : class that extends {@code Measure<? extends Number>}
	 * @param pop
	 * 		: {@code Population} of {@code T} to be set
	 * @param forecasts
	 * 		: {@code Vector} of {@code Forecast} or subclasses, from which the {@code Measure}s are taken
	 * @param nameOfMeasure
	 * 		: name of the {@code Population}'s type
	 * @throws EmptyVectorException
	 * 		if {@code forecasts} is an empty vector. 
	 */

	private <Z extends Measure<? extends Number>> void setAll(Population<Z> pop, String nameOfMeasure, 
																				 Vector<? extends Forecast> forecasts) {
		try {
			pop.setMax((Z)MeasuresAnalyzer.findMax(getValues(forecasts, nameOfMeasure)));
			pop.setMin((Z)MeasuresAnalyzer.findMin(getValues(forecasts, nameOfMeasure)));
			pop.setAvg(MeasuresAnalyzer.calcAvg(getValues(forecasts, nameOfMeasure)));
			pop.setVar(MeasuresAnalyzer.calcVar(getValues(forecasts, nameOfMeasure)));
			pop.setStdDev(Math.sqrt(pop.getVar()));
		} 
		catch(EmptyVectorException e) {
			// il caso in cui forecasts e' vuoto e' stato gia' gestito nel costruttore, prima della chiamata a questa funzione
		}
	}


	/**
	 * 
	 * @param forecasts
	 * 		: {@code Vector} of {@code Forecast} (or subclasses), from which the wanted {@code Measure} (or subclasses) 
	 * 		vector is extracted
	 * @param nameOfMeasure
	 * 		: name of the wanted measure, of which a vector will be returned
	 * @return
	 * 		a vector of the {@code Measure} (or subclasses), specified by {@code nameOfMeasure}
	 * @throws EmptyVectorException
	 * 		if {@code forecasts} is an empty vector.
	 */

	private Vector<? extends Measure<? extends Number>> getValues(Vector<? extends Forecast> forecasts, String nameOfMeasure) {

		// il caso in cui forecasts e' vuoto e' stato gia' gestito nel costruttore.
		
		Vector<? extends Measure<? extends Number>> values = null; // verra' inizializato per forza in uno dei case dello switch
		
		switch(nameOfMeasure)
		{
			case "temp": {
				if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Double>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Double>>)values).add(new InstantMeasure<>(f.getTemp(), f.getDate(), f.getTime()));
					}
				} else if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Double>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Double>>)values).add(new DailyMeasure<>(forecasts.get(i).getTemp(),
																						  forecasts.get(i).getDate()));
					}
				} 
				break;
			}
			
			case "feelsLike": {
				if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Double>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Double>>)values).add(new InstantMeasure<>(f.getFeelsLike(), f.getDate(), f.getTime()));
					}
				} else if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Double>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Double>>)values).add(new DailyMeasure<>(forecasts.get(i).getFeelsLike(),
																						  forecasts.get(i).getDate()));
					}
				}
				break;
			}
					
			case "humidity": {
				if(forecasts.get(0) instanceof HourlyForecast) {
					values =  Vector<InstantMeasure<Byte>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Byte>>)values).add(new InstantMeasure<>(f.getHumidity(), f.getDate(), f.getTime()));
					}
				} else if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Byte>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Byte>>)values).add(new DailyMeasure<>(forecasts.get(i).getHumidity(),
																						  forecasts.get(i).getDate()));
					}
				}
				break;
			}

			case "wind": {
				if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Integer>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Integer>>)values).add(new InstantMeasure<>(f.getWind(), f.getDate(), f.getTime()));
					}
				} else if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Integer>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Integer>>)values).add(new DailyMeasure<>(forecasts.get(i).getWind(),
																						  forecasts.get(i).getDate()));
					}
				}
				break;
			}

			case "pressure": {
				if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Integer>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Integer>>)values).add(new InstantMeasure<>(f.getPressure(), f.getDate(), f.getTime()));
					}
				} else if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Integer>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Integer>>)values).add(new DailyMeasure<>(forecasts.get(i).getPressure(),
																						  forecasts.get(i).getDate()));
					}
				}
				break;
			}

			case "airPoll": {
				if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<AirPollution>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<AirPollution>)values).add(new AirPollution<>(f.getAirPoll(), f.getDate(), f.getTime()));
					}
				} else if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Integer>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Integer>>)values).add(new DailyMeasure<>(forecasts.get(i).getAirPoll(),
																						  forecasts.get(i).getDate()));
					}
				}
				break;
			}

			case "clouds": {
				if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Byte>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Byte>>)values).add(new InstantMeasure<>(f.getClouds(), f.getDate(), f.getTime()));
					}
				} else if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Byte>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Byte>>)values).add(new DailyMeasure<>(forecasts.get(i).getClouds(),
																						  forecasts.get(i).getDate()));
					}
				}
				break;
			}
		}
		return values;
	}



}
