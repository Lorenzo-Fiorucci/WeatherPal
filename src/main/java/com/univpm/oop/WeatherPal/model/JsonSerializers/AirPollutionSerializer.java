package com.univpm.oop.WeatherPal.model.JsonSerializers;

import com.univpm.oop.WeatherPal.model.Forecast.AirPollution;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AirPollutionSerializer extends StdSerializer<AirPollution>{
	
	public AirPollutionSerializer() {
		this(null);
	}

	public AirPollutionSerializer(Class<AirPollution> klazz) {
		super(klazz);
	}

	@Override
	public void serialize(AirPollution airPoll, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		
		jgen.writeStartObject();
		jgen.writeNumberField("index", airPoll.getIndex());
		jgen.writeNumberField("CO", airPoll.getCo());
		jgen.writeNumberField("NO", airPoll.getNo());
		jgen.writeNumberField("NO2", airPoll.getNo2());
		jgen.writeNumberField("O3", airPoll.getO3());
		jgen.writeNumberField("PM10", airPoll.getPm10());
		jgen.writeEndObject();
	}
}
