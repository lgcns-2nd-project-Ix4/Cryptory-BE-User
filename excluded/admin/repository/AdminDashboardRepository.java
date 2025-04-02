package com.cryptory.be.admin.repository;

import com.cryptory.be.admin.dto.dashboard.AdminDashboardResponseDTO;
import com.cryptory.be.admin.dto.dashboard.Statistics;

import java.time.LocalDate;
import java.util.Map;

/**
 * packageName    : com.cryptory.be.admin.repository
 * fileName       : AdminDashboardRepository
 * author         : 조영상
 * date           : 2/26/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/26/25         조영상        최초 생성
 */
public interface AdminDashboardRepository {
    Statistics getNewUserStatistics(LocalDate startDate, LocalDate endDate);
    Map<String, Statistics> getApiCallStatisticsByCoin(LocalDate startDate, LocalDate endDate);
    Long countNewUsersAfterDate(LocalDate startDate);
    Long countNewUsersBetweenDates(LocalDate startDate, LocalDate endDate);
    Long countIssuesByCoinCodeAfterDate(String coinCode, LocalDate startDate);
    Long countIssuesByCoinCodeBetweenDates(String coinCode, LocalDate startDate, LocalDate endDate);
}
