package com.univpm.oop.WeatherPal.model.JsonSerializers;

import java.io.IOException;

import com.univpm.oop.WeatherPal.model.Forecast.Weather;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class WeatherSerializer extends StdSerializer<Weather> {
	
	public WeatherSerializer() {
		this(null);
	}

	public WeatherSerializer(Class<Weather> klazz) {
		super(klazz);
	}

	@Override
	public void serialize(Weather weather, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		
		jgen.writeStartObject();
		jgen.writeStringField("type", weather.getType());
		jgen.writeStringField("description", weather.getDescription());
		jgen.writeEndObject();
	}

}
