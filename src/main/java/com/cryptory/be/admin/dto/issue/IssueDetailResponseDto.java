package com.cryptory.be.admin.dto.issue;

/**
 * packageName    : com.cryptory.be.admin.dto.issue
 * fileName       : IssueDetailResponseDto
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
public class IssueDetailResponseDto { //이슈 단건
    private Long issueId;
    private LocalDate date;
    private String title;
    private String content;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String newsTitle; //유지
    private String source; //유지
    private String type;
    private Boolean isDeleted;// 추가
}
