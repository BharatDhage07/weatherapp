package com.weatherapp.models;

public class HomeData {

    private WeatherInfo weatherInfo;
    private WeatherForecast weatherForecast;

    public HomeData(WeatherInfo weatherInfo, WeatherForecast weatherForecast) {
        this.weatherInfo = weatherInfo;
        this.weatherForecast = weatherForecast;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(WeatherForecast weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

}
