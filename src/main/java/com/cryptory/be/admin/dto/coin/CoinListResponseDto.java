package com.cryptory.be.admin.dto.coin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * packageName    : com.cryptory.be.admin.dto.coin
 * fileName       : CoinListResponseDto
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@Getter
@Builder
@AllArgsConstructor
@Jacksonized
public class CoinListResponseDto {
    private Long cryptoId;
    private String koreanName;
    private String englishName;
    private String symbol;
    private String logoUrl;
    private boolean isDisplayed;
}
