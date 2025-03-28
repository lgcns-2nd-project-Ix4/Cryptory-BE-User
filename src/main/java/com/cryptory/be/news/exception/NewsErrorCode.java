package com.cryptory.be.news.exception;

import com.cryptory.be.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NewsErrorCode implements ErrorCode {

    NEWS_LOAD_FAILED(HttpStatus.NOT_FOUND, "네이버 API 오류입니다. 뉴스를 가져오는 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
