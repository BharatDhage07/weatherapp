package com.weatherapp.models;

import java.util.Date;

public class News {
    private String title;
    private String description;
    private String source;
    private Date publishedAt;

    public News(String title, String description, String source, Date publishedAt) {
        this.title = title;
        this.description = description;
        this.source = source;
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
}
