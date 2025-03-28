package com.cryptory.be.admin.repository;

import com.cryptory.be.admin.dto.dashboard.Statistics;

import java.time.LocalDate;

/**
 * packageName    : com.cryptory.be.admin.repository
 * fileName       : TrafficLogRepositoryCustom
 * author         : 조영상
 * date           : 2/26/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/26/25         조영상        최초 생성
 */
public interface TrafficLogRepositoryCustom {
    Statistics getVisitStatistics(LocalDate startDate, LocalDate endDate);
    Statistics getPageViewStatistics(LocalDate startDate, LocalDate endDate);
}
