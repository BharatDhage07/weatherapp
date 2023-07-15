package com.weatherapp.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherAppProperties {
	
	@Value("${weather.app.apikey}")
	private String apikey;
	
	public String getApikey() {
		return apikey;
	}
	
}
