package com.univpm.oop.WeatherPal.model.JsonSerializers;

import com.univpm.oop.WeatherPal.model.Statistics.Stats;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class StatsSerializer extends StdSerializer<Stats> {

	public StatsSerializer() {
		this(null);
	}

	public StatsSerializer(Class<Stats> klazz) {
		super(klazz);
	}

	@Override
	public void serialize(Stats stat, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		
		jgen.writeStartObject();
		jgen.writePOJOField(stat.getTemp().getName(), stat.getTemp());
		jgen.writePOJOField(stat.getFeelsLike().getName(), stat.getFeelsLike());
		jgen.writePOJOField(stat.getHumidity().getName(), stat.getHumidity());
		jgen.writePOJOField(stat.getWind().getName(), stat.getWind());
		jgen.writePOJOField(stat.getPressure().getName(), stat.getPressure());
		jgen.writePOJOField(stat.getClouds().getName(), stat.getClouds());
		jgen.writePOJOField(stat.getAirPoll().getName(), stat.getAirPoll());
		jgen.writePOJOField("Stats period", stat.getPeriod());
		jgen.writeEndObject();
	}
}
