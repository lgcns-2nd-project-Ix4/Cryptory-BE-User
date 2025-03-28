package com.cryptory.be.admin.controller;

import com.cryptory.be.admin.dto.dashboard.AdminDashboardResponseDTO;
import com.cryptory.be.admin.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * packageName    : com.cryptory.be.admin.controller
 * fileName       : AdminDashboardController
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    // 대시보드 통계 조회
    @GetMapping
    public ResponseEntity<AdminDashboardResponseDTO> getDashboardStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String period) {

       AdminDashboardResponseDTO statistics = adminDashboardService.getDashboardStatistics(startDate, endDate, period);
       return ResponseEntity.ok(statistics);
    }
}
