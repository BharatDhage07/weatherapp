package com.weatherapp.models;

public class WeatherInfo {

	private final String country;

	private final String city;

	private final Integer code;

	private final String icon;
	
	private final String description;

	private final double temperature;

	public WeatherInfo(String country, String city, Weather weather) {
		this.country = country;
		this.city = city;
		this.code = weather.getWeatherId();
		this.icon = weather.getWeatherIcon();
		this.temperature = weather.getTemperature();
		this.description=weather.getDescription();
	}

	public String getCountry() {
		return this.country;
	}

	public String getCity() {
		return this.city;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getIcon() {
		return this.icon;
	}
	
	public String getDescription() {
		return description;
	}

	public String getFahrenheitTemperature() {
		double fahrenheitTemp = (this.temperature * 1.8) - 459.67;
		return String.format("%4.2f", fahrenheitTemp);
	}

	public String getCelsiusTemperature() {
		double celsiusTemp = this.temperature - 273.15;
		return String.format("%4.2f", celsiusTemp);
	}

}

