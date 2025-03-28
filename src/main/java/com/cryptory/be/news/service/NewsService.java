package com.cryptory.be.news.service;

import com.cryptory.be.global.util.DateFormat;
import com.cryptory.be.news.dto.NewsDto;
import com.cryptory.be.openapi.service.NaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {

    private final static String QUERY_WORD = "코인";

    private final NaverService naverService;

    public List<NewsDto> getNews() {
        return naverService.getNaverNewsWithWord(QUERY_WORD).stream()
                .map(naverNews -> {
                    try {
                        return new NewsDto(naverNews.getTitle(), naverNews.getLink(), naverNews.getDescription(), DateFormat.formatNewsDate(naverNews.getPubDate()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }
}
