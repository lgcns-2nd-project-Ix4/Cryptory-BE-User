package com.cryptory.be.infra.client;

import com.cryptory.be.admin.dto.coin.CoinDetailResponseDto;
import com.cryptory.be.admin.dto.coin.CoinListResponseDto;
import com.cryptory.be.admin.dto.issue.IssueCreateRequestDto;
import com.cryptory.be.admin.dto.issue.IssueDetailResponseDto;
import com.cryptory.be.admin.dto.issue.IssueListResponseDto;
import com.cryptory.be.admin.dto.issue.IssueUpdateRequestDto;
import com.cryptory.be.global.dto.PagedResult;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.cryptory.be.user.infra.client
 * fileName       : CoinServiceClient
 * author         : 조영상
 * date           : 4/1/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 4/1/25         조영상        최초 생성
 */
@FeignClient(name = "coin-service")
public interface CoinServiceClient {

    @GetMapping("/api/v1/coins")
    PagedResult<CoinListResponseDto> getCoinList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false) String sort);

    @GetMapping("/api/v1/coins/{coinId}") // coin-service의 상세 조회 API 경로
    CoinDetailResponseDto getCoinDetails(@PathVariable("coinId") Long coinId);

    @PatchMapping("/api/v1/coins/{coinId}/display")
    ResponseEntity<Void> toggleCoinDisplay(@PathVariable("coinId") Long coinId, @RequestParam("isDisplayed") boolean isDisplayed);

    @GetMapping("/api/v1/admin/coins/{coinId}/issues") // coin-service 경로와 일치
    Page<IssueListResponseDto> getIssueList( // Page 반환 타입
                                             @PathVariable("coinId") Long coinId,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size);

    @PostMapping("/api/v1/admin/coins/{coinId}/issues")
    ResponseEntity<Void> createIssue(
            @PathVariable("coinId") Long coinId,
            @RequestBody @Valid IssueCreateRequestDto requestDto,
            @RequestHeader("X-Admin-User-Id") String adminUserId);

    @GetMapping("/api/v1/admin/issues/{issueId}")
    IssueDetailResponseDto getIssueDetails(@PathVariable("issueId") Long issueId);

    @PutMapping("/api/v1/admin/issues/{issueId}")
    ResponseEntity<Void> updateIssue(@PathVariable("issueId") Long issueId,
                                     @RequestBody @Valid IssueUpdateRequestDto requestDto);

    @DeleteMapping("/api/v1/admin/issues")
    ResponseEntity<Void> deleteIssues(@RequestParam("ids") List<Long> ids);
}

