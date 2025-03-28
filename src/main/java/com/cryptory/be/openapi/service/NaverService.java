package com.cryptory.be.openapi.service;

import com.cryptory.be.news.exception.NewsErrorCode;
import com.cryptory.be.news.exception.NewsException;
import com.cryptory.be.openapi.client.NaverClient;
import com.cryptory.be.openapi.dto.NaverNews;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverService {

    private final NaverClient naverClient;

    public List<NaverNews> getNaverNewsWithWord(String coinName) {
        try {
            return naverClient.getNaverNewsWithWord(coinName);
        } catch (NewsException e) {
            printLog(e.getMessage());
            throw new NewsException(NewsErrorCode.NEWS_LOAD_FAILED);
        }
    }

    private void printLog(String message) {
        log.error("error: {}", message);
    }

}
