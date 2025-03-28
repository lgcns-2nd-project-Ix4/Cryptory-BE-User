package com.cryptory.be.admin.dto.dashboard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * packageName    : com.cryptory.be.admin.dto.dashboard
 * fileName       : AdminDashboardResponseDTO
 * author         : 조영상
 * date           : 2/26/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/26/25         조영상        최초 생성
 */
@Builder
@Getter
@Setter
public class AdminDashboardResponseDTO {
    private Statistics visits;
    private Statistics pageViews;
    private Statistics newUsers;
    private Map<String, Statistics> apiCallCounts;
}
