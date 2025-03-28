package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.coin.CoinDetailResponseDto;
import com.cryptory.be.admin.dto.coin.CoinListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminCoinServiceImpl
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCoinServiceImpl implements AdminCoinService{
    private final CoinRepository coinRepository;

    @Override
    public Page<CoinListResponseDto> getCoinList(String keyword, int page, int size, String sort) {
        Sort sorting = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sorting);

        //Page<Coin> coins = coinRepository.searchCoins("%bit%", pageable);
        String parsedKeyword;
        if (keyword == null || keyword.trim().isEmpty()) {
            parsedKeyword = null; // 또는 ""
        } else {
            parsedKeyword = "%" + keyword.toLowerCase().trim() + "%";
        }
        System.out.println(parsedKeyword);

        Page<Coin> coins;
        if( parsedKeyword == null ){
            coins = coinRepository.findAll(pageable);
        } else {
            coins = coinRepository.searchCoins(parsedKeyword, pageable);
        }

        return coins.map(this::convertToCoinListResponseDto);
    }

    @Override
    public CoinDetailResponseDto getCoinDetails(Long coinId) {
        Coin coin =  coinRepository.findById(coinId)
                .orElseThrow(() -> new NoSuchElementException("해당 코인을 찾을 수 없습니다. ID: " + coinId));
        return convertToCoinDetailResponseDto(coin);
    }

    @Transactional
    @Override
    public void updateDisplaySetting(Long coinId, boolean isDisplayed) {
        Coin coin = coinRepository.findById(coinId)
                .orElseThrow(() -> new NoSuchElementException("해당 코인을 찾을 수 없습니다. ID: " + coinId));

        if(isDisplayed) {
            long count = coinRepository.countByIsDisplayedTrue();
            if(count >= 10) {
                throw new IllegalArgumentException("메인 페이지에 노출 가능한 코인 수(10개)를 초과했습니다.");
            }
        }

        coin.setIsDisplayed(isDisplayed);
    }

    private CoinListResponseDto convertToCoinListResponseDto(Coin coin) {
        return CoinListResponseDto.builder()
                .cryptoId(coin.getId())
                .koreanName(coin.getKoreanName())
                .englishName(coin.getEnglishName())
                .symbol(coin.getCode())
                .logoUrl(coin.getCoinSymbol().getLogoUrl())
                .isDisplayed(coin.isDisplayed())
                .build();
    }

    private CoinDetailResponseDto convertToCoinDetailResponseDto(Coin coin) {
        return CoinDetailResponseDto.builder()
                .cryptoId(coin.getId())
                .name(coin.getKoreanName())
                .symbol(coin.getCode())
                .logoUrl(coin.getCoinSymbol().getLogoUrl())
                .cryptoColor(coin.getCoinSymbol().getColor())
                .isDisplayed(coin.isDisplayed())
                .build();
    }

    private Sort parseSort(String sort) {
        if(sort == null || sort.isEmpty()){
            // 정렬 조건이 없다면 기본적으로 한국어 이름 오름차순으로 정렬함
            return Sort.by("id").ascending();
        }

        String[] parts = sort.split(",");
        String property = parts[0];
        Sort.Direction direction = Sort.Direction.ASC;
        if(parts.length > 1 && "desc".equalsIgnoreCase(parts[1])) {
            direction = Sort.Direction.DESC;
        }

        return Sort.by(direction, property);
    }

}
