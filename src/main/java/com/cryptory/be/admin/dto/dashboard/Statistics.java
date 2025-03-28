package com.cryptory.be.admin.dto.dashboard;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.cryptory.be.admin.dto.dashboard
 * fileName       : Statistics
 * author         : 조영상
 * date           : 2/26/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/26/25         조영상        최초 생성
 */

@Getter
@Builder
// 단일 통계 항목 DTO
public class Statistics {
    private Long daily;
    private Long weekly;
    private Long monthly;
    private Long total;
}
