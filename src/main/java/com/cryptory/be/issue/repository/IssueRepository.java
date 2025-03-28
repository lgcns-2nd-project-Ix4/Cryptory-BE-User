package com.cryptory.be.issue.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.issue.domain.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

	List<Issue> findAllByCoinId(Long coinId);

	@Query("SELECT i FROM Issue i WHERE i.coin.id = :coinId AND i.isDeleted = false")
	Page<Issue> findByCoinIdAndIsDeletedFalse(@Param("coinId") Long coinId, Pageable pageable);

	// isDeleted 필드만 업데이트하는 논리적 삭제를 위해 JPQL 사용
	@Modifying
	@Query("UPDATE Issue i SET i.isDeleted = true WHERE i.id IN :ids")
	void softDeleteByIds(@Param("ids") List<Long> ids);
}
