package com.cryptory.be.chart.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChartDto {
    private Long chartId; // 차트 ID
    private String date; // 날짜
    private double openingPrice; // 시가
    private double highPrice; // 종가
    private double lowPrice; // 고가
    private double tradePrice; // 저가
    private double changeRate; // 변화율
}
