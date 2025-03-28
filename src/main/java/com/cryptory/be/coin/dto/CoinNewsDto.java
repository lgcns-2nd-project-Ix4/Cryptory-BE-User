package com.cryptory.be.coin.dto;

import lombok.Data;

@Data
public class CoinNewsDto {
    private String title;
    private String link;
    private String description;
    private String pubDate;

    public CoinNewsDto(String title, String link, String description, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }
}
