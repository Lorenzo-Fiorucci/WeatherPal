package com.univpm.oop.WeatherPal.model.JsonSerializers;

import com.univpm.oop.WeatherPal.model.City.City;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CitySerializer extends StdSerializer<City> {
	
	public CitySerializer() {
		this(null);
	}

	public CitySerializer(Class<City> klazz) {
		super(klazz);
	}

	@Override
	public void serialize(City city, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		
		jgen.writeStartObject();
		jgen.writeStringField("name", city.getName());
		jgen.writeNumberField("lat", city.getLat());
		jgen.writeNumberField("lon", city.getLon());
		jgen.writeNumberField("alt", city.getAlt());
		jgen.writePOJOField("forecast", city.getForecasts());
		jgen.writeEndObject();
	}
}
