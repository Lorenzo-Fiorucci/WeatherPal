package com.univpm.oop.WeatherPal.tools;

import com.univpm.oop.WeatherPal.model.City.GeoPoint;
import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;
import com.univpm.oop.WeatherPal.model.Forecast.*;
import com.univpm.oop.WeatherPal.model.Measures.Measure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.time.*;
import java.time.format.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;

public class JsonParser {

	public static Vector<Forecast> HourlyFile(String date, String time, byte hours, String folderPath) {
		try {
			LocalDate lclDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			LocalTime lclTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));



		} catch(DateTimeParseException e) {

		}

	}


	/**
	 * Method that calls OpenWeatherMap's Historical API multiple times to cover the period of time specified
	 * in the client request, and stores the hourly measures in a vector of {@code HourlyForecast}.
	 * @param city : name of the city on which make stats
	 * @param period : filter that specifies the period queried by the client request
	 * @return a vector of {@code HourlyForecast} that contains all the data to be analyzed
	 * @throws JsonProcessingException
	 * 		if an error occurs during the processing of the response body json
	 * @throws InterruptedException
	 * 		if the operation of sending the request to OpenWeatherMap API is interrupted
	 * @throws IOException
	 * 		if an I/O error occurs when sending the request to OpenWeatherMap API
	 */

	public static Vector<HourlyForecast> fromHistoricalHourly(String city, HourlyPeriod period)
		throws JsonProcessingException, InterruptedException, IOException {

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
					forecast.setTemp(new Measure<Double>(hourlySample.get("temp").asDouble(), "Celsius"));
					forecast.setFeelsLike(new Measure<Double>(hourlySample.get("feels_like").asDouble(), "Celsius"));
					forecast.setHumidity(new Measure<Byte>((byte)hourlySample.get("humidity").asInt() , "%"));
					forecast.setWind(new Measure<Integer>(hourlySample.get("wind_speed").asInt(),"meter/sec"));
					forecast.setPressure(new Measure<Integer>(hourlySample.get("pressure").asInt(), "hPa"));
					forecast.setClouds(new Measure<Byte>((byte)hourlySample.get("clouds").asInt(), "%"));
					Weather weather = new Weather(hourlySample.get("weather").get(0).get("id").asInt(),
												  hourlySample.get("weather").get(0).get("main").asText(),
												  hourlySample.get("weather").get(0).get("descriprion").asText());
					forecast.setWeather(weather);
					
					forecasts.add(forecast);
				}
			}
			dateTime = dateTime.plusHours(1);
		}
		Vector<AirPollution> pollutions = historicAirPoll(lat, lon, LocalDateTime.of(period.getStartDate(), period.getStartTime()), endDateTime);
		
		for(int i = 0; i < forecasts.size(); i++)
			forecasts.get(i).setAirPoll(pollutions.get(i));

		return forecasts;
	}

	public static Vector<DailyForecast> fromHistoricalDaily() {
		
		Vector<DailyForecast> forecasts = new Vector<>();
		
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
	 * @throws JsonProcessingException if an error occurs during the processing of the response body json
	 * @throws IOException if an I/O error occurs when sending the request to OpenWeatherMap API
	 * @throws InterruptedException if the operation of sending the request to OpenWeatherMap API is interrupted
	 */
	private static GeoPoint getCoord(String city) throws JsonProcessingException, IOException, InterruptedException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String apiKey = "65e03c27f11e0b756f47a70056be962f";
		String url = "http://api.openweathermap.org/geo/1.0/direct?appid=" + apiKey + "&limit=1&q=" + city.toLowerCase();
		
		JsonNode jNode = mapper.readTree(httpGET(url).body());
		
		return new GeoPoint(jNode.get(0).get("lat").asDouble(), jNode.get(0).get("lon").asDouble());
	}
	

	/**
	 * Method to get the altitude of a given point of the globe
	 * @param point : {@code GeoPoint} object with null altitude and not null latitude and longitude.
	 * @return the same object recived in input, with the correct altitude
	 * @throws JsonProcessingException if an error occurs during the processing of the response body json
	 * @throws IOException if an I/O error occurs when sending the request to OpenWeatherMap API
	 * @throws InterruptedException if the operation of sending the request to OpenWeatherMap API is interrupted
	 */
	private static GeoPoint setAlt(GeoPoint point) throws JsonProcessingException, IOException, InterruptedException {
		
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
	 * @throws JsonProcessingException 
	 * 		if an error occurs during the processing of the response body json
	 * @throws IOException
	 * 		if an I/O error occurs when sending the request to OpenWeatherMap API
	 * @throws InterruptedException
	 * 		if the operation of sending the request to OpenWeatherMap API is interrupted
	 */

	private static Vector<AirPollution> historicAirPoll(double lat, double lon, LocalDateTime start, LocalDateTime end)
		throws JsonProcessingException, IOException, InterruptedException {

		ObjectMapper mapper = new ObjectMapper();
		
		String apiKey = "65e03c27f11e0b756f47a70056be962f";
		String url = "http://api.openweathermap.org/data/2.5/air_pollution/history?appid=" + apiKey + "&lat=" + lat + "&lon=" + lon + 
				"&start=" + EpochConverter.toEpochSeconds(start) + "&end=" + EpochConverter.toEpochSeconds(end); 
		
		JsonNode jNode = mapper.readTree(httpGET(url).body());

		Vector<AirPollution> pollutions = new Vector<>();
		for (JsonNode hourlyPoll : jNode.get("list")) {
			AirPollution poll = new AirPollution();
			poll.setIndex((byte) hourlyPoll.get("main").get("aqi").asInt());
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
