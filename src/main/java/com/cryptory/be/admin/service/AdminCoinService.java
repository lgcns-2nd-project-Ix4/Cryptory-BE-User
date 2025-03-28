package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.coin.CoinDetailResponseDto;
import com.cryptory.be.admin.dto.coin.CoinListResponseDto;
import org.springframework.data.domain.Page;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminCoinService
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
public interface AdminCoinService {
    // 코인 목록 조회 (검색, 페이징, 정렬)
    Page<CoinListResponseDto> getCoinList(String keyword, int page, int size, String sort);

    // 특정 코인 상세 정보 조회
    CoinDetailResponseDto getCoinDetails(Long coinId);

    // 코인 메인 페이지 노출 여부 변경
    void updateDisplaySetting(Long coinId, boolean isDisplayed);
}
