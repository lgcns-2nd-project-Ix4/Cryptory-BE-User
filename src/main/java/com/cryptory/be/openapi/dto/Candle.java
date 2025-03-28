package com.cryptory.be.openapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Candle {
    // 종목 코드
    private String market;

    // 캔들 기준 시각
    @JsonProperty("candle_date_time_utc")
    private String candleDateTime;

    // 시가
    @JsonProperty("opening_price")
    private double openingPrice;

    // 고가
    @JsonProperty("high_price")
    private double highPrice;

    // 저가
    @JsonProperty("low_price")
    private double lowPrice;

    // 종가
    @JsonProperty("trade_price")
    private double tradePrice;

    // 마지막 틱이 저장된 시각
    private Long timestamp;

    // 전일 종가 대비 변화 금액
    @JsonProperty("change_price")
    private double changePrice;

    // 전일 종가 대비 변화량
    @JsonProperty("change_rate")
    private double changeRate;
}