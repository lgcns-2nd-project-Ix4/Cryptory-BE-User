package com.cryptory.be.admin.repository;

import com.cryptory.be.admin.dto.dashboard.AdminDashboardResponseDTO;
import com.cryptory.be.admin.dto.dashboard.Statistics;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.cryptory.be.admin.repository
 * fileName       : AdminDashboardRepositoryImpl
 * author         : 조영상
 * date           : 2/26/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/26/25         조영상        최초 생성
 */
@Repository
public class AdminDashboardRepositoryImpl implements AdminDashboardRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    public Statistics getNewUserStatistics(LocalDate startDate, LocalDate endDate) {
        Long total = countNewUsers(startDate, endDate);
        Long daily = countNewUsers(LocalDate.now(), LocalDate.now());
        Long weekly = countNewUsers(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), LocalDate.now());
        Long monthly = countNewUsers(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalDate.now());

        return Statistics.builder()
                .total(total)
                .daily(daily)
                .weekly(weekly)
                .monthly(monthly)
                .build();
    }

    @Override
    public Map<String, Statistics> getApiCallStatisticsByCoin(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : LocalDateTime.now();

        // 코인별 전체 API 호출 횟수
        TypedQuery<Object[]> totalQuery = em.createQuery(
                "SELECT i.coin.code, COUNT(i) FROM Issue i WHERE i.createdAt >= :startDateTime AND i.createdAt <= :endDateTime GROUP BY i.coin.code", Object[].class); // Tuple 대신 Object[] 사용
        totalQuery.setParameter("startDateTime", startDateTime);
        totalQuery.setParameter("endDateTime", endDateTime);
        List<Object[]> totalResults = totalQuery.getResultList();

        Map<String, Statistics> statisticsMap = new HashMap<>();

        for (Object[] result : totalResults) {
            String coinCode = (String) result[0];
            Long totalCount = (Long) result[1];

            Long daily = countIssuesByCoinCode(coinCode, LocalDate.now(), LocalDate.now());
            Long weekly = countIssuesByCoinCode(coinCode, LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), LocalDate.now());
            Long monthly = countIssuesByCoinCode(coinCode, LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalDate.now());


            statisticsMap.put(coinCode, Statistics.builder()
                    .total(totalCount)
                    .daily(daily)
                    .weekly(weekly)
                    .monthly(monthly)
                    .build());
        }

        return statisticsMap;

    }

    // 신규 사용자 수 카운트
    private Long countNewUsers(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : LocalDateTime.now();

        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.createdAt >= :startDateTime AND u.createdAt <= :endDateTime AND u.providerId IS NOT NULL AND u.providerName IS NOT NULL", Long.class);
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);
        return query.getSingleResult();
    }
    @Override
    public Long countNewUsersAfterDate(LocalDate startDate) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.createdAt >= :startDate AND u.providerId IS NOT NULL AND u.providerName IS NOT NULL", Long.class);
        query.setParameter("startDate", startDate.atStartOfDay());
        return query.getSingleResult();
    }

    @Override
    public Long countNewUsersBetweenDates(LocalDate startDate, LocalDate endDate) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.createdAt >= :startDate AND u.createdAt <= :endDate AND u.providerId IS NOT NULL AND u.providerName IS NOT NULL", Long.class);
        query.setParameter("startDate", startDate.atStartOfDay());
        query.setParameter("endDate", endDate.atTime(23, 59, 59));
        return query.getSingleResult();
    }

    // 코인별 API 호출 횟수 카운트
    private Long countIssuesByCoinCode(String coinCode, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : LocalDateTime.now();

        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(i) FROM Issue i JOIN i.coin c WHERE c.code = :coinCode AND i.createdAt >= :startDateTime AND i.createdAt <= :endDateTime", Long.class);
        query.setParameter("coinCode", coinCode);
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);
        return query.getSingleResult();
    }
    @Override
    public Long countIssuesByCoinCodeAfterDate(String coinCode, LocalDate startDate){
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(i) FROM Issue i JOIN i.coin c WHERE c.code = :coinCode AND i.createdAt >= :startDate", Long.class);
        query.setParameter("coinCode", coinCode);
        query.setParameter("startDate", startDate.atStartOfDay());
        return query.getSingleResult();
    }
    @Override
    public Long countIssuesByCoinCodeBetweenDates(String coinCode, LocalDate startDate, LocalDate endDate){
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(i) FROM Issue i JOIN i.coin c WHERE c.code = :coinCode AND i.createdAt BETWEEN :startDate AND :endDate", Long.class);
        query.setParameter("coinCode", coinCode);
        query.setParameter("startDate", startDate.atStartOfDay());
        query.setParameter("endDate", endDate.atTime(23, 59, 59));
        return query.getSingleResult();
    }
}
