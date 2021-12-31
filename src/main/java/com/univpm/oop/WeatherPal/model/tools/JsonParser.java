package com.univpm.oop.WeatherPal.model.tools;

import com.univpm.oop.WeatherPal.model.City.GeoPoint;
import com.univpm.oop.WeatherPal.model.Forecast.*;
import com.univpm.oop.WeatherPal.model.Filters.*;
import com.univpm.oop.WeatherPal.model.Measures.InstantMeasure;
import com.univpm.oop.WeatherPal.model.Measures.Measure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Vector;

public class JsonParser {

	/**
	 * 
	 * @param city : the city queried in the client request
	 * @return a vector of {@code HourlyForecast} contining the forecast measures for 5 days, every 3 hours.
	 * @throws InterruptedException if the operation of sending the request to OpenWeatherMap API is interrupted
	 * @throws IOException if an I/O error occurs when sending the request to OpenWeatherMap API
	 */
	public static Vector<HourlyForecast> from5dForecast(String city) throws InterruptedException, IOException {

		Vector<HourlyForecast> forecasts = new Vector<>();
		ObjectMapper mapper = new ObjectMapper();

		String apiKey = "65e03c27f11e0b756f47a70056be962f";
		String url = "https://api.openweathermap.org/data/2.5/forecast?units=metric&q=" + city + "&appid=" + apiKey;
		JsonNode jNode = mapper.readTree(httpGET(url).body());
		
		HourlyForecast forecast;
		for (JsonNode element : jNode.get("list")) {
			forecast = new HourlyForecast();
			
			LocalDateTime dateTime = EpochConverter.toLocalDateTime(element.get("dt").asLong());
			forecast.setDate(dateTime.toLocalDate());
			forecast.setTime(dateTime.toLocalTime());
			
			Weather weather = new Weather(element.get("weather").get(0).get("id").asInt(), 
										  element.get("weather").get(0).get("main").asText(),
										  element.get("weather").get(0).get("description").asText());
			forecast.setWeather(weather);

			forecast.setTemp(new Measure<Double>(element.get("main").get("temp").asDouble(), "°C"));
			forecast.setFeelsLike(new Measure<Double>(element.get("main").get("feels_like").asDouble(), "°C"));
			forecast.setHumidity(new Measure<Byte>((byte)element.get("main").get("humidity").asInt(), "%"));
			forecast.setPressure(new Measure<Integer>(element.get("main").get("pressure").asInt(), "hPa"));
			forecast.setWind(new Measure<Integer>(element.get("wind").get("speed").asInt(), "m/s"));
			forecast.setClouds(new Measure<Byte>((byte)element.get("clouds").get("all").asInt(), "%"));
			forecast.setPop(new Measure<Byte>((byte)Math.round(element.get("pop").asDouble() * 100), "%"));

			forecasts.add(forecast);
		}
		return forecasts;
	}

	public static Vector<Forecast> HourlyFile(HourlyPeriod hourlyPeriod) throws IOException, InterruptedException {

		Vector<Forecast> forecasts = new Vector<>();

		ObjectMapper mapper = new ObjectMapper();

		String root = System.getProperty("user.dir");
		String folderPath = "\\src\\main\\resources\\static\\Every1h";
		File folder = new File(root + folderPath);

		for (String fileName : folder.list()) {

			JsonNode node = mapper.readTree(new File(root + folderPath + fileName));

			LocalDateTime localDateTime = EpochConverter.toLocalDateTime(node.get("dt").asInt());

			HourlyForecast forecast = new HourlyForecast();
			forecast.setTemp(new Measure<Double>(node.get("main").get("temp").asDouble(), "°C"));
			forecast.setFeelsLike(new Measure<Double>(node.get("main").get("feels_like").asDouble(), "°C"));
			forecast.setHumidity(new Measure<Byte>((byte) node.get("main").get("humidity").asInt(), "%"));
			forecast.setWind(new Measure<Integer>(node.get("wind").get("speed").asInt(), "m/s"));
			forecast.setPressure(new Measure<Integer>(node.get("main").get("pressure").asInt(), "hPa"));
			forecast.setClouds(new Measure<Byte>((byte) node.get("clouds").get("all").asInt(), "%"));
			forecast.setDate(localDateTime.toLocalDate());
			forecast.setTime(localDateTime.toLocalTime());

			forecasts.add(forecast);

		}

		Vector<AirPollution> pollutions = historicAirPoll(43.55, 13.1667, LocalDateTime.of(hourlyPeriod.getStartDate(), hourlyPeriod.getStartTime()),
				LocalDateTime.of(hourlyPeriod.getEndDate(), hourlyPeriod.getEndTime()));
		for (int i = 0; i < forecasts.size(); i++)
			forecasts.get(i).setAirPoll(new Measure<AirPollution>(pollutions.get(i)));

		return forecasts;
	}


	/**
	 * Method that calls OpenWeatherMap's Historical API multiple times to cover the period of time specified
	 * in the client request, and stores the hourly measures in a vector of {@code HourlyForecast}.
	 * @param city : name of the city on which make stats
	 * @param period : filter that specifies the period queried by the client request
	 * @return a vector of {@code HourlyForecast} that contains all the data to be analyzed
	 * @throws InterruptedException
	 * 		if the operation of sending the request to OpenWeatherMap API is interrupted
	 * @throws IOException
	 * 		if an I/O error occurs when sending the request to OpenWeatherMap API
	 */

	public static Vector<HourlyForecast> fromHistoricalHourly(String city, HourlyPeriod period) throws  IOException, InterruptedException {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jNode;
		
		GeoPoint point = getCoord(city);
		double lat = point.getLat();
		double lon = point.getLon();
		String apiKey = "65e03c27f11e0b756f47a70056be962f";
		String url;
		
		LocalDateTime dateTime = LocalDateTime.of(period.getStartDate(), period.getStartTime());
		LocalDateTime endDateTime = LocalDateTime.of(period.getEndDate(), period.getEndTime());
		int epochSeconds;
		Vector<HourlyForecast> forecasts = new Vector<>();
			
		while(dateTime.compareTo(endDateTime) <= 0) {
			
			epochSeconds = EpochConverter.toEpochSeconds(dateTime);
			url = "https://api.openweathermap.org/data/2.5/onecall/timemachine?appid=" + apiKey + "&lat=" + lat + "&lon=" + lon + 
				"&dt=" + epochSeconds + "&units=metric";
			
			jNode = mapper.readTree(httpGET(url).body());
			
			for (JsonNode hourlySample : jNode.get("hourly"))
			{
				dateTime = EpochConverter.toLocalDateTime(hourlySample.get("dt").asInt());
				if(period.contains(dateTime))
				{					
					HourlyForecast forecast = new HourlyForecast();
					forecast.setDate(dateTime.toLocalDate());
					forecast.setTime(dateTime.toLocalTime());
					forecast.setTemp(new Measure<Double>(hourlySample.get("temp").asDouble(), "°C"));
					forecast.setFeelsLike(new Measure<Double>(hourlySample.get("feels_like").asDouble(), "°C"));
					forecast.setHumidity(new Measure<Byte>((byte)hourlySample.get("humidity").asInt() , "%"));
					forecast.setWind(new Measure<Integer>(hourlySample.get("wind_speed").asInt(),"m/s"));
					forecast.setPressure(new Measure<Integer>(hourlySample.get("pressure").asInt(), "hPa"));
					forecast.setClouds(new Measure<Byte>((byte)hourlySample.get("clouds").asInt(), "%"));
					
					forecasts.add(forecast);
				}
			}
			dateTime = dateTime.plusHours(1);
		}
		Vector<AirPollution> pollutions = historicAirPoll(lat, lon, LocalDateTime.of(period.getStartDate(), period.getStartTime()), endDateTime);
		
		for(int i = 0; i < forecasts.size(); i++)
			forecasts.get(i).setAirPoll(new Measure<AirPollution>(pollutions.get(i))); // i vettori 'forecasts' e 'pollutions' hanno elementi distanziati di 1 ora,
																					  // hanno il primo elemento con lo stesso orario e hanno l'ultimo elemento con 
		return forecasts;															 // lo stesso orario, quindi vanno in parallelo
	}

	/**
	 * Method that calls OpenWeatherMap's Historical API multiple times to cover the period of time specified
	 * in the client request, and stores the measures in a vector of {@code DailyForecast}.
	 * @param city : name of the city on which make stats
	 * @param period : filter that specifies the period queried by the client request
	 * @return a vector of {@code DailyForecast} that contains all the data to be analyzed
	 * @throws IOException
	 * 		if the operation of sending the request to OpenWeatherMap API is interrupted
	 * @throws InterruptedException
	 * 		if an I/O error occurs when sending the request to OpenWeatherMap API
	 */
	public static Vector<DailyForecast> fromHistoricalDaily(String city, DailyPeriod period) throws IOException, InterruptedException {
		
		Vector<DailyForecast> toReturn = new Vector<>();
		
		LocalDateTime endDateTime;
		if(period.getEndDate().equals(LocalDate.now()))
			endDateTime = LocalDateTime.now();
		else
			endDateTime = period.getEndDate().atTime(23, 00);
		Vector<HourlyForecast> hourlyForecasts = fromHistoricalHourly(city, new HourlyPeriod(period.getStartDate().atTime(01, 00), endDateTime));
		
		Vector<HourlyForecast> thisDayForecasts = new Vector<>();
		LocalDate thisDay = period.getStartDate();
		
		for (HourlyForecast hFor : hourlyForecasts) {
			
			if(hFor.getDate().equals(thisDay))
				thisDayForecasts.add(hFor);
			else {
				toReturn.add(calcDailyForecast(thisDayForecasts));
				thisDayForecasts.clear();
				thisDayForecasts.add(hFor);
				thisDay = thisDay.plusDays(1);
			}
		}
		if(!thisDayForecasts.isEmpty())
			toReturn.add(calcDailyForecast(thisDayForecasts));
		
		return toReturn;
	}

	/**
	 * 
	 * @param hForecasts : vector of {@code HourlyForecast} with the same date
	 * @return
	 * 		a {@code DailyForecast} object, with the date of {@code hForecasts}' elements.
	 * 		The other fields are the average of the corresponding fields in {@code hForecasts}' elements
	 */
	private static <T> DailyForecast calcDailyForecast(Vector<HourlyForecast> hForecasts) {
		
		DailyForecast dFor = new DailyForecast();
		dFor.setDate(hForecasts.get(0).getDate());
		
		HashMap<String, Vector<T>> hForecastsFields = ReflectionTools.getFieldValues(hForecasts);
		dFor.setTemp(new Measure<Double>( MeasuresAnalyzer.numAvg((Vector<InstantMeasure<Double>>) hForecastsFields.get("temp")), "°C"));
		dFor.setFeelsLike(new Measure<Double>( MeasuresAnalyzer.numAvg((Vector<InstantMeasure<Double>>) hForecastsFields.get("feelsLike")), "°C"));
		dFor.setHumidity(new Measure<Byte>( (byte)MeasuresAnalyzer.numAvg((Vector<InstantMeasure<Byte>>) hForecastsFields.get("humidity")), "%"));
		dFor.setWind(new Measure<Integer>( (int)MeasuresAnalyzer.numAvg((Vector<InstantMeasure<Integer>>) hForecastsFields.get("wind")), "meter/sec"));
		dFor.setPressure(new Measure<Integer>( (int)MeasuresAnalyzer.numAvg((Vector<InstantMeasure<Integer>>) hForecastsFields.get("pressure")), "hPa"));
		dFor.setClouds(new Measure<Byte>( (byte)MeasuresAnalyzer.numAvg((Vector<InstantMeasure<Byte>>) hForecastsFields.get("clouds")), "%"));
		
		AirPollution poll = new AirPollution();
		HashMap<String,Object> pollMap = MeasuresAnalyzer.distribAvg((Vector<InstantMeasure<AirPollution>>)hForecastsFields.get("airPoll"));
		poll.setIndex((int) Math.round((double)pollMap.get("index")));
		poll.setCo((double) pollMap.get("co"));
		poll.setNo((double) pollMap.get("no"));
		poll.setNo2((double) pollMap.get("no2"));
		poll.setO3((double) pollMap.get("o3"));
		poll.setPm10((double) pollMap.get("pm10"));
		dFor.setAirPoll(new Measure<AirPollution>(poll));

		return dFor;
	}
	

	/**
	 * The method to make a GET request to an API.
	 * @param url : url leading to the endpoint, with eventual query params
	 * @return the response, of the class {@code HttpResponse<String>}.
	 * @throws InterruptedException if the operation of sending the request to the API is interrupted
	 * @throws IOException if an I/O error occurs when sending the request to the API
	 */
	private static HttpResponse<String> httpGET(String url) throws InterruptedException, IOException {
	
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = null;
			try {
				request = HttpRequest.newBuilder(new URI(url)).GET().build();
			} catch (URISyntaxException e) {
				// l'url rispetta le regole dell'api
			}
		return client.send(request, BodyHandlers.ofString());
	}


	/**
	 * Method to get the coordinates of a city
	 * @param city : name of the city of which get the coordinates
	 * @return a {@code GeoPoint} object containing the city's coordinates and a null altitude
	 * @throws IOException if an I/O error occurs when sending the request to OpenWeatherMap API
	 * @throws InterruptedException if the operation of sending the request to OpenWeatherMap API is interrupted
	 */
	private static GeoPoint getCoord(String city) throws IOException, InterruptedException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String apiKey = "65e03c27f11e0b756f47a70056be962f";
		String url = "http://api.openweathermap.org/geo/1.0/direct?appid=" + apiKey + "&limit=1&q=" + city.toLowerCase();
		
		JsonNode jNode = mapper.readTree(httpGET(url).body());
		
		return new GeoPoint(jNode.get(0).get("lat").asDouble(), jNode.get(0).get("lon").asDouble());
	}
	

	/**
	 * Method to get the altitude of a given point of the globe
	 * @param point : {@code GeoPoint} object with null altitude and not null latitude and longitude.
	 * @return the same object received in input, with the correct altitude
	 * @throws IOException if an I/O error occurs when sending the request to OpenWeatherMap API
	 * @throws InterruptedException if the operation of sending the request to OpenWeatherMap API is interrupted
	 */
	private static GeoPoint setAlt(GeoPoint point) throws IOException, InterruptedException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String url = "https://api.open-elevation.com/api/v1/lookup?locations=" + point.getLat() + "," + point.getLon();
	
		JsonNode jNode = mapper.readTree(httpGET(url).body());
		
		point.setAlt(jNode.get("results").get(0).get("elevation").asInt());
		return point;
	}

	/**
	 * 
	 * @param lat : latitude
	 * @param lon : longitude
	 * @param start : start {@code LocalDateTime}
	 * @param end : end {@code LocalDateTime}
	 * @return an {@code AirPollution} vector. Its elements are 1 hour spaced measures of air quality.
	 * @throws IOException
	 * 		if an I/O error occurs when sending the request to OpenWeatherMap API
	 * @throws InterruptedException
	 * 		if the operation of sending the request to OpenWeatherMap API is interrupted
	 */

	private static Vector<AirPollution> historicAirPoll(double lat, double lon, LocalDateTime start, LocalDateTime end)
		throws IOException, InterruptedException {

		Vector<AirPollution> pollutions = new Vector<>();
		ObjectMapper mapper = new ObjectMapper();
		
		String apiKey = "65e03c27f11e0b756f47a70056be962f";
		String url = "http://api.openweathermap.org/data/2.5/air_pollution/history?appid=" + apiKey + "&lat=" + lat + "&lon=" + lon + 
				"&start=" + EpochConverter.toEpochSeconds(start) + "&end=" + EpochConverter.toEpochSeconds(end); 
		
		JsonNode jNode = mapper.readTree(httpGET(url).body());
		AirPollution poll;

		for (JsonNode hourlyPoll : jNode.get("list")) {
			
			poll = new AirPollution();
			poll.setIndex(hourlyPoll.get("main").get("aqi").asInt());
			hourlyPoll = hourlyPoll.get("components");
			poll.setCo(hourlyPoll.get("co").asDouble());
			poll.setNo(hourlyPoll.get("no").asDouble());
			poll.setNo2(hourlyPoll.get("no2").asDouble());
			poll.setO3(hourlyPoll.get("o3").asDouble());
			poll.setPm10(hourlyPoll.get("pm10").asDouble());

			pollutions.add(poll);
		}
		return pollutions;
	}
	
	
}
