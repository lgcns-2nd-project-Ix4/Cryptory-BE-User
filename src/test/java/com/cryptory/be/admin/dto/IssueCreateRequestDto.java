package com.cryptory.be.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.cryptory.be.admin.dto
 * fileName       : IssueCreateRequestDto
 * author         : 조영상
 * date           : 2/25/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/25/25         조영상        최초 생성
 */
@Setter
@Getter
public class IssueCreateRequestDto {
    @NotBlank(message = "제목은 필수입니다.") // 빈 문자열, null, 공백만 있는 문자열 불가
    @Size(max = 255, message = "제목은 255자를 초과할 수 없습니다.")
    private String title;

    @NotBlank(message = "요약 내용은 필수입니다.")
    @Size(max = 1000, message = "요약 내용은 1000자를 초과할 수 없습니다.")
    private String summaryContent;

    @NotBlank(message = "뉴스 제목은 필수입니다.")
    @Size(max = 255, message = "뉴스 제목은 255자를 초과할 수 없습니다.")
    private String newsTitle;

    @NotBlank(message = "출처는 필수입니다.")
    @Size(max = 255, message = "출처는 255자를 초과할 수 없습니다.")
    private String source;

    @NotBlank(message="날짜를 입력해주세요")
    private String date;
}
