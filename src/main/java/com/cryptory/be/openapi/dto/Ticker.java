package com.cryptory.be.openapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Ticker {
    // 종목 코드
    private String market;

    // 최근 거래 일자(UTC)
    @JsonProperty("trade_date")
    private String tradeDate;

    // 최근 거래 시각(UTC)
    @JsonProperty("trade_time")
    private String tradeTime;

    // 종가(현재가)
    @JsonProperty("trade_price")
    private Double tradePrice;

    // EVEN : 보합, RISE : 상승, FALL : 하락
    private String change;

    // 부호가 있는 변화액
    @JsonProperty("signed_change_price")
    private Double signedChangePrice;

    // 부호가 있는 변화율(변화량)
    @JsonProperty("signed_change_rate")
    private Double signedChangeRate;

    // 누적 거래 대금
    @JsonProperty("acc_trade_price")
    private Double accTradePrice;
}
