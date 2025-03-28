package com.cryptory.be.admin.repository;

import com.cryptory.be.admin.domain.TrafficLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.cryptory.be.admin.repository
 * fileName       : TrafficLogRepository
 * author         : 조영상
 * date           : 2/24/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/24/25         조영상        최초 생성
 */
public interface TrafficLogRepository extends JpaRepository<TrafficLog, Long>,  TrafficLogRepositoryCustom{
    //
}
