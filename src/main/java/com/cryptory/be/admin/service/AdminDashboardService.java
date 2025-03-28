package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.dashboard.AdminDashboardResponseDTO;

import java.time.LocalDate;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminDashboardService
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
public interface AdminDashboardService {
    AdminDashboardResponseDTO getDashboardStatistics(LocalDate startDate, LocalDate endDate, String period);
}
