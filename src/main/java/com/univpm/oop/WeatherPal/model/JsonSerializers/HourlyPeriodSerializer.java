package com.univpm.oop.WeatherPal.model.JsonSerializers;

import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class HourlyPeriodSerializer extends StdSerializer<HourlyPeriod> {

	public HourlyPeriodSerializer() {
		this(null);
	}
	
	public HourlyPeriodSerializer(Class<HourlyPeriod> klazz) {
		super(klazz);
	}

	@Override
	public void serialize(HourlyPeriod period, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		
		jgen.writeStartObject();
		String from =  period.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " at " + period.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		String to =  period.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " at " + period.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		jgen.writeStringField("from", from);
		jgen.writeStringField("to", to);
		jgen.writeEndObject();
	}
}
