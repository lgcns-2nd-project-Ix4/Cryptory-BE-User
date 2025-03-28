package com.cryptory.be.coin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * packageName    : com.cryptory.be.coin.dto
 * fileName       : CoinDto
 * author         : 조영상
 * date           : 2/21/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/21/25         조영상        최초 생성
 */
@Getter
@Builder
@AllArgsConstructor
@Jacksonized
public class CoinShortDto {
    private final int coinId;
    private final String coinSymbol;
    private final String coinName;
    private final String coinLogoUrl;
}
