package com.cryptory.be.admin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : com.cryptory.be.admin.domain
 * fileName       : TrafficLog
 * author         : 조영상
 * date           : 2/24/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/24/25         조영상        최초 생성
 */
/*
요구사항:

    대시보드 통계 조회:
        총 방문자 수 (회원 + 비회원)
        총 페이지 뷰 수
        AI API 호출 횟수
        코인별 트래픽 (메인 페이지에 노출된 코인만)
        기간별 필터링: 일별, 주별, 월별, 전체
*/

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "traffic_logs")
public class TrafficLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // null 허용 (비회원 방문)

    private String ipAddress;
    private String userAgent;
    private String path;
    private LocalDateTime timestamp; // 통계 기준 날짜


    @Builder
    public TrafficLog(Long userId, String ipAddress, String userAgent, String path, LocalDateTime timestamp){
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.path = path;
        this.timestamp = timestamp;
    }
}
