package com.cryptory.be.admin.dto.issue;

/**
 * packageName    : com.cryptory.be.admin.dto.issue
 * fileName       : IssueListResponseDto
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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Jacksonized
public class IssueListResponseDto { //이슈 목록
    private Long issueId;
    private LocalDate date;
    private String title;
    private String createdBy; // 또는 String createdBy (닉네임)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
