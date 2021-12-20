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
	
	Population<? extends Measure<Float>> temp;
	Population<? extends Measure<Float>> feelsLike;
	Population<? extends Measure<Byte>> humidity;
	Population<? extends Measure<Short>> wind;
	Population<? extends Measure<Short>> pressure;
	Population<? extends Measure<Short>> airPoll;
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
	
		temp = new Population<Measure<Float>>("Temperature");
		feelsLike = new Population<Measure<Float>>("Feels like");
		humidity = new Population<Measure<Byte>>("Humidity");
		wind = new Population<Measure<Short>>("Wind");
		pressure = new Population<Measure<Short>>("Pressure");
		airPoll = new Population<Measure<Short>>("Air pollution");
		clouds = new Population<Measure<Byte>>("Clouds");
		
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
	 * @param <T>
	 * 		T : class that extends {@code Measure<? extends Number>}
	 * @param pop
	 * 		: {@code Population} of {@code T} to be set
	 * @param forecasts
	 * 		: {@code Vector} of {@code Forecast} or subclasses, from which the {@code Measure}s are taken
	 * @param nameOfMeasure
	 * 		: name of the {@code Population}'s type
	 * @throws EmptyVectorException
	 * 		if {@code forecasts} is an empty vector. 
	 */

	private <T extends Measure<? extends Number>> void setAll(Population<T> pop, String nameOfMeasure, 
						Vector<? extends Forecast> forecasts) throws EmptyVectorException {
		
		pop.setMax((T)MeasuresAnalyzer.findMax(getValues(forecasts, nameOfMeasure)));
		pop.setMin((T)MeasuresAnalyzer.findMin(getValues(forecasts, nameOfMeasure)));
		pop.setAvg(MeasuresAnalyzer.calcAvg(getValues(forecasts, nameOfMeasure)));
		pop.setVar(MeasuresAnalyzer.calcVar(getValues(forecasts, nameOfMeasure)));
		pop.setStdDev(Math.sqrt(pop.getVar()));
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

	private Vector<? extends Measure<? extends Number>> getValues(Vector<? extends Forecast> forecasts, String nameOfMeasure)
		throws EmptyVectorException {
		
		if(forecasts.isEmpty())
			throw new EmptyVectorException("ERROR: Empty vector passed to Satts constructor.");
		
		Vector<? extends Measure<? extends Number>> values = null;
		
		switch(nameOfMeasure)
		{
			case "temp": {
				if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Float>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Float>>)values).add(new DailyMeasure<>(forecasts.get(i).getTemp(),
																						  forecasts.get(i).getDate()));
					}
				} else if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Float>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Float>>)values).add(new InstantMeasure<>(f.getTemp(), f.getDate(), f.getTime()));
					}
				}
				break;
			}
			
			case "feelsLike": {
				if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Float>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Float>>)values).add(new DailyMeasure<>(forecasts.get(i).getFeelsLike(),
																						  forecasts.get(i).getDate()));
					}
				} else if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Float>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Float>>)values).add(new InstantMeasure<>(f.getFeelsLike(), f.getDate(), f.getTime()));
					}
				}
				break;
			}
					
			case "humidity": {
				if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Byte>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Byte>>)values).add(new DailyMeasure<>(forecasts.get(i).getHumidity(),
																						  forecasts.get(i).getDate()));
					}
				} else if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Byte>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Byte>>)values).add(new InstantMeasure<>(f.getHumidity(), f.getDate(), f.getTime()));
					}
				}
				break;
			}

			case "wind": {
				if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Short>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Short>>)values).add(new DailyMeasure<>(forecasts.get(i).getWind(),
																						  forecasts.get(i).getDate()));
					}
				} else if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Short>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Short>>)values).add(new InstantMeasure<>(f.getWind(), f.getDate(), f.getTime()));
					}
				}
				break;
			}

			case "pressure": {
				if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Short>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Short>>)values).add(new DailyMeasure<>(forecasts.get(i).getPressure(),
																						  forecasts.get(i).getDate()));
					}
				} else if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Short>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Short>>)values).add(new InstantMeasure<>(f.getPressure(), f.getDate(), f.getTime()));
					}
				}
				break;
			}

			case "airPoll": {
				if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Short>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Short>>)values).add(new DailyMeasure<>(forecasts.get(i).getAirPoll(),
																						  forecasts.get(i).getDate()));
					}
				} else if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Short>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Short>>)values).add(new InstantMeasure<>(f.getAirPoll(), f.getDate(), f.getTime()));
					}
				}
				break;
			}

			case "clouds": {
				if (forecasts.get(0) instanceof DailyForecast) {
					values = new Vector<DailyMeasure<Byte>>();
					for (int i = 0; i < forecasts.size(); i++) {
						((Vector<DailyMeasure<Byte>>)values).add(new DailyMeasure<>(forecasts.get(i).getClouds(),
																						  forecasts.get(i).getDate()));
					}
				} else if(forecasts.get(0) instanceof HourlyForecast) {
					values = new Vector<InstantMeasure<Byte>>();
					for (HourlyForecast f : (Vector<HourlyForecast>)forecasts) {
						((Vector<InstantMeasure<Byte>>)values).add(new InstantMeasure<>(f.getClouds(), f.getDate(), f.getTime()));
					}
				}
				break;
			}
		}
		return values;
	}



}
