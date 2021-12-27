package com.univpm.oop.WeatherPal.model.JsonSerializers;

import com.univpm.oop.WeatherPal.model.Measures.InstantMeasure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class InstantMeasureSerializer extends StdSerializer<InstantMeasure<?>> {
	
	public InstantMeasureSerializer() {
		this(null);
	}

	public InstantMeasureSerializer(Class<InstantMeasure<?>> klazz) {
		super(klazz);
	}

	@Override
	public void serialize(InstantMeasure<?> measure, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		
		jgen.writeStartObject();
		jgen.writePOJOField("value", measure.getValue());
		
		if(!measure.getUnit().equals(""))
			jgen.writeStringField("unit", measure.getUnit());
		
		jgen.writeStringField("date", measure.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		jgen.writeStringField("time", measure.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		jgen.writeEndObject();
	}
}
