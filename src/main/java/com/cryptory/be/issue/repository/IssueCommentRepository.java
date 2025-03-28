package com.cryptory.be.issue.repository;

import java.util.List;

import com.cryptory.be.issue.domain.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cryptory.be.issue.domain.IssueComment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueCommentRepository extends JpaRepository<IssueComment, Long> {
	List<IssueComment> findAllByIssueId(Long issueId);

	Page<IssueComment> findByIssueAndIsDeletedFalse(Issue issue, Pageable pageable);

	@Query("SELECT ic FROM IssueComment ic WHERE ic.issue.id = :issueId AND ic.isDeleted = false")
	Page<IssueComment> findCommentsByIssueId(@Param("issueId") Long issueId, Pageable pageable);

	@Modifying
	@Query("UPDATE IssueComment ic SET ic.isDeleted = true WHERE ic.id = :id")
	void softDeleteById(@Param("id")Long id);
}
