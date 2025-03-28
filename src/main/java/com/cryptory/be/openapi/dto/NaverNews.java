package com.cryptory.be.openapi.dto;

import lombok.Data;

@Data
public class NaverNews {
    private String title;
    private String link;
    private String description;
    private String pubDate;
}