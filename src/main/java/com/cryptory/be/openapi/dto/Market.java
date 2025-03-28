package com.cryptory.be.openapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Market {
    // 종목 코드(KRW-BTC, BTC-ETH 등)
    private String market;

    // 한글 이름
    @JsonProperty("korean_name")
    private String koreanName;

    // 영어 이름
    @JsonProperty("english_name")
    private String englishName;
}
