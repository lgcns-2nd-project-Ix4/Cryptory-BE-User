package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.issue.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminIssueService
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
public interface AdminIssueService {
    // 이슈 목록 조회
    List<IssueListResponseDto> getIssueList(Long coinId, int page, int size);

    // 이슈 생성
    Long createIssue(Long coinId, IssueCreateRequestDto requestDto);

    // 이슈 상세 조회
    IssueDetailResponseDto getIssueDetails(Long issueId);

    /// 이슈 수정
    void updateIssue(Long issueId, IssueUpdateRequestDto requestDto);

    // 이슈 삭제
    void deleteIssues(List<Long> ids);

    // 토론방 댓글 목록 조회
    Page<IssueCommentListResponseDto> getIssueComments(Long issueId, int page, int size);

    // 토론방 댓글 삭제
    void deleteIssueComment(Long commentId);
}
