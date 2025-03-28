package com.cryptory.be.news.controller;

import com.cryptory.be.global.response.ApiResponse;
import com.cryptory.be.news.dto.NewsDto;
import com.cryptory.be.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ApiResponse<NewsDto> getNews() {
        List<NewsDto> news = newsService.getNews();

        return new ApiResponse<>(HttpStatus.OK, news);
    }
}
