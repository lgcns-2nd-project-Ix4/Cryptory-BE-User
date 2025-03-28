package com.cryptory.be.chart.exception;

import com.cryptory.be.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChartErrorCode implements ErrorCode {

    TICKER_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "올바르지 않은 코인에 대한 차트 요청입니다."),
    CHART_LOAD_FAILED(HttpStatus.NOT_FOUND, "업비트 API 오류입니다. 차트를 가져오는 중 오류가 발생했습니다."),
    CHART_DATA_MISSING(HttpStatus.NOT_FOUND, "차트 데이터가 존재하지 않습니다."),
    TICKER_LOAD_FAILED(HttpStatus.NOT_FOUND, "업비트 API 오류입니다. 현재가를 가져오는 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
