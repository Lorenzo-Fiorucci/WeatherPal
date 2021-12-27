package com.univpm.oop.WeatherPal.model.JsonSerializers;

import com.univpm.oop.WeatherPal.model.Statistics.Population;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PopulationSerializer extends StdSerializer<Population<?>> {
	
	public PopulationSerializer() {
		this(null);
	}

	public PopulationSerializer(Class<Population<?>> klazz) {
		super(klazz);
	}

	@Override
	public void serialize(Population<?> pop, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		
		jgen.writeStartObject();
		jgen.writeFieldName(pop.getName());
		
		jgen.writeStartObject();
		jgen.writePOJOField("max", pop.getMax());
		jgen.writePOJOField("min", pop.getMin());
		jgen.writePOJOField("average", pop.getAvg());
		jgen.writePOJOField("variance", pop.getVar());
		jgen.writePOJOField("standard deviation", pop.getStdDev());
		jgen.writeEndObject();
		
		jgen.writeEndObject();
		
	}

}
