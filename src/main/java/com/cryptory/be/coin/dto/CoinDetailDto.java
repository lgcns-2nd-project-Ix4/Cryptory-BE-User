package com.cryptory.be.coin.dto;

import com.cryptory.be.chart.dto.ChartDto;
import com.cryptory.be.coin.domain.CoinSymbol;
import com.cryptory.be.issue.dto.IssueDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CoinDetailDto {
    private Long coinId;
    private String koreanName;
    private String englishName;
    private String code;

    private CoinSymbol coinSymbol; // 심볼 정보

    private double tradePrice; // 현재가
    private double signedChangeRate; // 변화율
    private double signedChangePrice; // 변화 금액
    private String timestamp; // 최근 거래 시간

    // 차트 리스트(차트 ID, 날짜, 시가, 종가, 고가, 저가, 변화율)
    private List<ChartDto> chartList;

    // 이슈 리스트(이슈 넘버, 차트 넘버, 날짜)
    private List<IssueDto> issueList;
}
