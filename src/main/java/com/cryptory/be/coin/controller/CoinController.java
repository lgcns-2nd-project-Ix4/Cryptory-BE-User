package com.cryptory.be.coin.controller;

import java.util.List;

import com.cryptory.be.coin.dto.CoinDetailDto;
import com.cryptory.be.coin.dto.CoinNewsDto;
import com.cryptory.be.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryptory.be.coin.dto.CoinDto;
import com.cryptory.be.coin.service.CoinService;

@RestController
@RequestMapping("/api/v1/coins")
@RequiredArgsConstructor
public class CoinController {

	private final CoinService coinService;
	
	// 코인 목록 조회
	@GetMapping
	public ApiResponse<CoinDto> getCoins() throws Exception {
		
		List<CoinDto> coinList = coinService.getCoins();

		return new ApiResponse<>(HttpStatus.OK, coinList);
	}
	
	// 특정 코인 상세 조회
	@GetMapping("/{coinId}")
	public ApiResponse<CoinDetailDto> getCoinDetail(@PathVariable("coinId") Long coinId) {
		
		CoinDetailDto selectedCoinDetail = coinService.getCoinDetail(coinId);

		return new ApiResponse<>(HttpStatus.OK, selectedCoinDetail);
		
	}
	
	// 특정 코인 뉴스 조회 - 네이버 뉴스
	@GetMapping("/{coinId}/news")
	public ApiResponse<CoinNewsDto> searchCoinNews(@PathVariable("coinId") Long coinId) {
		List<CoinNewsDto> coinNewsList = coinService.getCoinNews(coinId);
		return new ApiResponse<>(HttpStatus.OK, coinNewsList);
	}

}
