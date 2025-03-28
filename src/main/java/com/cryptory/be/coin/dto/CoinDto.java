package com.cryptory.be.coin.dto;

import com.cryptory.be.coin.domain.CoinSymbol;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Data
public class CoinDto {
    private Long coinId;

    // 코인 이름
    private String koreanName;

    private String englishName;

    // 마켓 코드
    private String code; // KRW-BTC

    // 심볼 정보(색상, 이미지)
    private CoinSymbol coinSymbol;

    // 현재가
    private double tradePrice;

    // 전일 종가 대비 변화 금액
    private Double signedChangePrice;

    // 전일 종가 대비 변화량
    private Double signedChangeRate;
}
