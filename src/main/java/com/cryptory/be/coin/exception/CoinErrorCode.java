package com.cryptory.be.coin.exception;

import com.cryptory.be.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CoinErrorCode implements ErrorCode {

    COIN_DATA_MISSING(HttpStatus.NOT_FOUND, "코인 데이터가 존재하지 않습니다."),
    COIN_LOAD_FAILED(HttpStatus.NOT_FOUND, "업비트 API 오류입니다. 현재가를 가져오는 중 오류가 발생했습니다."),
    COIN_NEWS_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "뉴스 파싱 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
