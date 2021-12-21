package com.univpm.oop.WeatherPal.tools;

import com.univpm.oop.WeatherPal.model.Forecast.*;

import java.util.*;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.time.*;
import java.time.format.*;

public class JsonParser {

	public static Vector<Forecast> HourlyFile(String date, String time, byte hours, String folderPath) {
		try {
			LocalDate lclDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			LocalTime lclTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));

			Period period

		} catch(DateTimeParseException e) {

		}

	}


}
