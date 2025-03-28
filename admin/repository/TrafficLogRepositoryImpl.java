package com.cryptory.be.admin.repository;

import com.cryptory.be.admin.dto.dashboard.Statistics;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * packageName    : com.cryptory.be.admin.repository
 * fileName       : TrafficLogRepositoryImpl
 * author         : 조영상
 * date           : 2/26/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/26/25         조영상        최초 생성
 */
@Repository
public class TrafficLogRepositoryImpl implements TrafficLogRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Statistics getVisitStatistics(LocalDate startDate, LocalDate endDate) {
        Long total = countDistinctIpAddresses(startDate, endDate);
        Long daily = countDistinctIpAddresses(LocalDate.now(), LocalDate.now());
        Long weekly = countDistinctIpAddresses(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), LocalDate.now());
        Long monthly = countDistinctIpAddresses(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalDate.now());

        return Statistics.builder()
                .total(total)
                .daily(daily)
                .weekly(weekly)
                .monthly(monthly)
                .build();
    }

    @Override
    public Statistics getPageViewStatistics(LocalDate startDate, LocalDate endDate) {
        Long total = countTrafficLogs(startDate, endDate);
        Long daily = countTrafficLogs(LocalDate.now(), LocalDate.now());
        Long weekly = countTrafficLogs(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), LocalDate.now());
        Long monthly = countTrafficLogs(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalDate.now());

        return Statistics.builder()
                .total(total)
                .daily(daily)
                .weekly(weekly)
                .monthly(monthly)
                .build();
    }

    // 중복 방문을 제외한 방문자 수 카운트 (IP 주소 기준)
    private Long countDistinctIpAddresses(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : LocalDateTime.now();

        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(DISTINCT t.ipAddress) FROM TrafficLog t WHERE t.timestamp >= :startDateTime AND t.timestamp <= :endDateTime", Long.class);
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);
        return query.getSingleResult();
    }


    // 전체 TrafficLog 카운트 (페이지 뷰)
    private Long countTrafficLogs(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : LocalDateTime.now();

        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(t) FROM TrafficLog t WHERE t.timestamp >= :startDateTime AND t.timestamp <= :endDateTime", Long.class);
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);
        return query.getSingleResult();
    }
}
