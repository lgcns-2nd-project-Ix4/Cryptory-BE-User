package com.cryptory.be.admin.dto.issue;

/**
 * packageName    : com.cryptory.be.admin.dto.issue
 * fileName       : IssueCreateRequestDto
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
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@Jacksonized
@ToString
public class IssueCreateRequestDto { //이슈 등록
    private LocalDate date;
    private String title;
    private String content;
    private String newsTitle;
    private String source;
}
