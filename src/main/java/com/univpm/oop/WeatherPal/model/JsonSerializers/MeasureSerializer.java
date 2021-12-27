package com.univpm.oop.WeatherPal.model.JsonSerializers;

import com.univpm.oop.WeatherPal.model.Measures.Measure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class MeasureSerializer extends StdSerializer<Measure<?>> {

	public MeasureSerializer() {
		this(null);
	}

	public MeasureSerializer(Class<Measure<?>> klazz) {
		super(klazz);
	}

	@Override
	public void serialize(Measure<?> measure, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		
		jgen.writeStartObject();
		jgen.writePOJOField("value", measure.getValue());
		
		if(!measure.getUnit().equals(""))
			jgen.writeStringField("unit", measure.getUnit());
		
		jgen.writeEndObject();
	}
	
}
