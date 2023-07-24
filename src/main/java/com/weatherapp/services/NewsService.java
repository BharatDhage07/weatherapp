package com.weatherapp.services;

import com.weatherapp.models.News;
import com.weatherapp.models.NewsApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final String NEWS_API_URL = "https://newsapi.org/";
    private final String API_KEY = "2a7a773973d1419188f2aa19c493591d";

    private final RestTemplate restTemplate;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<News> getNewsArticles() {
        String url = NEWS_API_URL + "?apiKey=" + API_KEY;
        ResponseEntity<NewsApiResponse> response = restTemplate.getForEntity(url, NewsApiResponse.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            NewsApiResponse newsApiResponse = response.getBody();
            if (newsApiResponse != null && newsApiResponse.getArticles() != null) {
                return newsApiResponse.getArticles();
            }
        }
        return new ArrayList<>();
    }

    public List<News> getAgricultureNewsArticles() {
        List<News> allArticles = getNewsArticles();
        return allArticles.stream()
                .filter(article -> article.getTitle().contains("agriculture") ||
                        article.getDescription().contains("agriculture"))
                .collect(Collectors.toList());
    }

    public List<News> getWeatherNewsArticles() {
        List<News> allArticles = getNewsArticles();
        return allArticles.stream()
                .filter(article -> article.getTitle().contains("weather") ||
                        article.getDescription().contains("weather"))
                .collect(Collectors.toList());
    }
}
