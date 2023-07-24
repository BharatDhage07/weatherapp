package com.weatherapp.controllers;

import com.weatherapp.models.News;
import com.weatherapp.services.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news/agriculture")
    public List<News> getAgricultureNews() {
        return newsService.getAgricultureNewsArticles();
    }

    @GetMapping("/news/weather")
    public List<News> getWeatherNews() {
        return newsService.getWeatherNewsArticles();
    }
}
