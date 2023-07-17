package com.weatherapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapp.models.Weather;
import com.weatherapp.models.WeatherForecast;
import com.weatherapp.services.WeatherService;

@RestController
@RequestMapping("/api")
public class WeatherApiController {
	
	@Autowired WeatherService service;

	@GetMapping("/now/{country}/{city}")
	public ResponseEntity<Weather> getWeather(@PathVariable String country, @PathVariable String city) {
		Weather weather = this.service.getWeather(country, city);
		if (weather != null) {
			return ResponseEntity.ok(weather);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/weekly/{country}/{city}")
	public ResponseEntity<WeatherForecast> getWeatherForecast(@PathVariable String country, @PathVariable String city) {
		WeatherForecast weatherForecast = this.service.getWeatherForecast(country, city);
		if (weatherForecast != null) {
			return ResponseEntity.ok(weatherForecast);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
