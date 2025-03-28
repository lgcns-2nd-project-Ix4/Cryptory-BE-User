package com.cryptory.be.news.dto;

import lombok.Data;

@Data
public class NewsDto {
    private String title;
    private String link;
    private String description;
    private String pubDate;

    public NewsDto(String title, String link, String description, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }
}
