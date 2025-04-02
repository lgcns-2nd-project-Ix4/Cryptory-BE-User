package com.cryptory.be.global.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * packageName    : com.cryptory.be.global.dto
 * fileName       : PagedResult
 * author         : 조영상
 * date           : 4/1/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 4/1/25         조영상        최초 생성
 */
@Data
@NoArgsConstructor
public class PagedResult<T> {
    private List<T> content;       // 실제 데이터 목록
    private int totalPages;      // 전체 페이지 수
    private long totalElements;    // 전체 데이터 개수
    private int size;            // 현재 페이지 크기
    private int number;          // 현재 페이지 번호 (0부터 시작)
    private boolean last;          // 마지막 페이지 여부
    private boolean first;         // 첫 페이지 여부
    private boolean empty;
}
