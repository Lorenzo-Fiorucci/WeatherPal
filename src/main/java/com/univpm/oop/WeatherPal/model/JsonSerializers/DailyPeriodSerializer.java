package com.univpm.oop.WeatherPal.model.JsonSerializers;

import com.univpm.oop.WeatherPal.model.Filters.DailyPeriod;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class DailyPeriodSerializer extends StdSerializer<DailyPeriod> {

	public DailyPeriodSerializer() {
		this(null);
	}
	
	public DailyPeriodSerializer(Class<DailyPeriod> klazz) {
		super(klazz);
	}

	@Override
	public void serialize(DailyPeriod period, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		
		jgen.writeStartObject();
		jgen.writeStringField("from", period.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		jgen.writeStringField("to", period.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		jgen.writeEndObject();
	}
}
