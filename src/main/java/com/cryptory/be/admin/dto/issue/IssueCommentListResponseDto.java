package com.cryptory.be.admin.dto.issue;

/**
 * packageName    : com.cryptory.be.admin.dto.issue
 * fileName       : IssueCommentListResponseDto
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
@Jacksonized
public class IssueCommentListResponseDto { //이슈 코멘트
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private boolean isDeleted;
    private Long userId; // 또는 String userNickname
    private String userNickname;
}
