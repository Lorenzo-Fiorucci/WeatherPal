package com.univpm.oop.WeatherPal.model.JsonSerializers;

import com.univpm.oop.WeatherPal.model.Forecast.Forecast;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ForecastSerializer extends StdSerializer<Forecast> {

	public ForecastSerializer() {
		this(null);
	}

	public ForecastSerializer(Class<Forecast> klazz) {
		super(klazz);
	}

	@Override
	public void serialize(Forecast forecast, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		
		jgen.writeStartObject();
		jgen.writeStringField("date", forecast.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		if(forecast.getWeather() != null)
			jgen.writePOJOField("weather", forecast.getWeather());
		jgen.writePOJOField("feels like", forecast.getFeelsLike());
		jgen.writePOJOField("humidity", forecast.getHumidity());
		jgen.writePOJOField("wind", forecast.getWind());
		jgen.writePOJOField("pressure", forecast.getPressure());
		jgen.writePOJOField("clouds", forecast.getClouds());
		if(forecast.getPop() != null)
			jgen.writePOJOField("probaility of precipitation", forecast.getPop());
		if(forecast.getUv() != null)
			jgen.writePOJOField("uv radiation", forecast.getUv());
		jgen.writePOJOField("air pollution", forecast.getAirPoll());
		jgen.writeEndObject();
	}
	
}
