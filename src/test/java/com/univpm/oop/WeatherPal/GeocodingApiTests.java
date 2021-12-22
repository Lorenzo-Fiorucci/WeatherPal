package com.univpm.oop.WeatherPal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.*;

@TestInstance(Lifecycle.PER_CLASS)
public class GeocodingApiTests {
	
	JsonNode responseNode;
	
	@BeforeAll
	public void setup() {
		HttpClient client = HttpClient.newHttpClient();
		ObjectMapper mapper = new ObjectMapper();
		String apiKey = "65e03c27f11e0b756f47a70056be962f";
		String url = "http://api.openweathermap.org/geo/1.0/direct?appid=" + apiKey + "&limit=1&q=firenze";
		HttpRequest request = null;
		HttpResponse<String> response = null;
		try {
			request = HttpRequest.newBuilder(new URI(url)).GET().build();
			response = client.send(request, BodyHandlers.ofString());
			responseNode = mapper.readTree(response.body());
		} catch (URISyntaxException e) {
			System.out.println("invalid url");
		} catch (JsonProcessingException e) {
			System.out.println(e);
		} catch (IOException | InterruptedException e) {
				System.out.println(e);
		}
	}

	@AfterAll
	public void tearDown() {

	}

	@Test
	public void test() {
		assertEquals(43.7698712, responseNode.get(0).get("lat").asDouble());
		assertEquals(11.2555757, responseNode.get(0).get("lon").asDouble());
	}

}
