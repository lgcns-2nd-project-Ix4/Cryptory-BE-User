package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.dashboard.AdminDashboardResponseDTO;
import com.cryptory.be.admin.dto.dashboard.Statistics;
import com.cryptory.be.admin.repository.AdminDashboardRepository;
import com.cryptory.be.admin.repository.TrafficLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements AdminDashboardService{
    private final AdminDashboardRepository adminDashboardRepository;
    private final TrafficLogRepository trafficLogRepository;

    @Override
    public AdminDashboardResponseDTO getDashboardStatistics(LocalDate startDate, LocalDate endDate, String period) {
        if (period != null) {
            // period 값에 따라 startDate와 endDate를 계산
            if (period.equals("daily")) {
                startDate = LocalDate.now();
                endDate = LocalDate.now();
            } else if (period.equals("weekly")) {
                startDate = LocalDate.now().minusWeeks(1);
                endDate = LocalDate.now();
            } else if (period.equals("monthly")) {
                startDate = LocalDate.now().minusMonths(1);
                endDate = LocalDate.now();
            }
        }

        // TrafficLog 기반 통계
        Statistics visits = trafficLogRepository.getVisitStatistics(startDate, endDate);
        Statistics pageViews = trafficLogRepository.getPageViewStatistics(startDate, endDate);

        // 기존 통계
        Statistics newUsers = adminDashboardRepository.getNewUserStatistics(startDate, endDate);
        Map<String, Statistics> apiCallCounts = adminDashboardRepository.getApiCallStatisticsByCoin(startDate, endDate);

        return AdminDashboardResponseDTO.builder()
                .visits(visits)
                .pageViews(pageViews)
                .newUsers(newUsers)
                .apiCallCounts(apiCallCounts)
                .build();
    }
}
