package com.cryptory.be.issue.dto;

import com.cryptory.be.chart.dto.ChartDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueDto {
	private Long issueId; // 이슈 ID
	private Long chartId; // 차트 ID
	private String date; // 날짜

	private double openingPrice;
	private double highPrice;
	private double lowPrice;
	private double tradePrice;

}