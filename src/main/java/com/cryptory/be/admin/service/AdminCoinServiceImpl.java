package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.coin.CoinDetailResponseDto;
import com.cryptory.be.admin.dto.coin.CoinListResponseDto;
import com.cryptory.be.global.dto.PagedResult;
import com.cryptory.be.infra.client.CoinServiceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCoinServiceImpl implements AdminCoinService{
    private final CoinServiceClient coinServiceClient;

    @Override
    public Page<CoinListResponseDto> getCoinList(String keyword, int page, int size, String sort) {
        try {
            log.info("관리자 기능: 코인 목록 조회 요청 (keyword: {}, page: {}, size: {}, sort: {})", keyword, page, size, sort);
            PagedResult<CoinListResponseDto> pagedResult = coinServiceClient.getCoinList(keyword, page, size, sort);

            if (pagedResult == null || pagedResult.getContent() == null) {
                log.warn("코인 서비스로부터 비정상적인 페이징 결과를 받았습니다.");
                return Page.empty(PageRequest.of(page, size));
            }
            Pageable pageable = PageRequest.of(pagedResult.getNumber(), pagedResult.getSize());
            log.info("관리자 기능: 코인 목록 조회 성공. 총 {}개 항목.", pagedResult.getTotalElements());
            return new PageImpl<>(pagedResult.getContent(), pageable, pagedResult.getTotalElements());

        } catch (FeignException e) {
            log.error("코인 목록 조회 중 코인 서비스 통신 오류: status={}, message={}", e.status(), e.getMessage(), e);
            throw new RuntimeException("코인 목록을 가져오는 중 코인 서비스와 통신에 실패했습니다. (오류코드: " + e.status() + ")", e);
        } catch (Exception e) {
            log.error("코인 목록 조회 중 예기치 않은 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("코인 목록 조회 중 예기치 않은 오류가 발생했습니다.", e);
        }
    }

    @Override
    public CoinDetailResponseDto getCoinDetails(Long coinId) {
        try {
            log.info("관리자 기능: 코인 상세 정보 조회 요청 (coinId: {})", coinId);
            CoinDetailResponseDto coinDetails = coinServiceClient.getCoinDetails(coinId);
            if (coinDetails == null) {
                throw new NoSuchElementException("코인 상세 정보 응답이 없습니다. (ID: " + coinId + ")");
            }
            log.info("관리자 기능: 코인 상세 정보 조회 성공 (coinId: {})", coinId);
            return coinDetails;
        } catch (FeignException.NotFound e) {
            log.warn("코인 상세 정보 조회 실패 (404 - Not Found): coinId={}", coinId);
            throw new NoSuchElementException("코인 상세 정보를 찾을 수 없습니다. (ID: " + coinId + ")");
        } catch (FeignException e) {
            log.error("코인 상세 정보 조회 중 코인 서비스 통신 오류: status={}, message={}", e.status(), e.getMessage(), e);
            throw new RuntimeException("코인 상세 정보를 가져오는 중 코인 서비스와 통신에 실패했습니다. (오류코드: " + e.status() + ")", e);
        } catch (Exception e) {
            log.error("코인 상세 정보 조회 중 예기치 않은 오류 발생 (coinId: {}): {}", coinId, e.getMessage(), e);
            throw new RuntimeException("코인 상세 정보 조회 중 예기치 않은 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void updateDisplaySetting(Long coinId, boolean isDisplayed) {
        try {
            log.info("관리자 기능: 코인 노출 상태 변경 요청 (coinId: {}, isDisplayed: {})", coinId, isDisplayed);
            ResponseEntity<Void> response = coinServiceClient.toggleCoinDisplay(coinId, isDisplayed);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("코인 노출 상태 변경 실패: 코인 서비스 응답 코드 {}", response.getStatusCode());
                if (response.getStatusCode().value() == 404) {
                    throw new NoSuchElementException("코인 서비스에서 해당 코인을 찾지 못했습니다. (ID: " + coinId + ")");
                } else if (response.getStatusCode().value() == 400) {
                    throw new IllegalArgumentException("코인 노출 개수 제한을 초과했거나 잘못된 요청입니다.");
                } else {
                    throw new RuntimeException("코인 서비스 처리 중 오류가 발생했습니다. 상태 코드: " + response.getStatusCode());
                }
            }
            log.info("관리자 기능: 코인 노출 상태 변경 요청 성공 (coinId: {})", coinId);

        } catch (FeignException e) {
            log.error("코인 노출 상태 변경 중 코인 서비스 통신 오류: status={}, message={}", e.status(), e.getMessage(), e);
            if (e.status() == 404) {
                throw new NoSuchElementException("코인 서비스 통신 실패: 해당 코인을 찾을 수 없습니다. (ID: " + coinId + ")");
            } else if (e.status() == 400) {
                throw new IllegalArgumentException("코인 서비스 통신 실패: 잘못된 요청 또는 노출 개수 제한 초과.");
            }
            throw new RuntimeException("코인 서비스와 통신 중 오류가 발생했습니다.", e);
        } catch (Exception e) {
            log.error("코인 노출 상태 변경 중 예기치 않은 오류 발생 (coinId: {}): {}", coinId, e.getMessage(), e);
            throw new RuntimeException("코인 노출 상태 변경 중 예기치 않은 오류가 발생했습니다.", e);
        }
    }
}
