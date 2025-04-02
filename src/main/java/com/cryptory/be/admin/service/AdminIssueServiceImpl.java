package com.cryptory.be.admin.service; // user-service

import com.cryptory.be.admin.dto.issue.*;
import com.cryptory.be.infra.client.CoinServiceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminIssueServiceImpl implements AdminIssueService {

    private final CoinServiceClient coinServiceClient;

    @Override
    public List<IssueListResponseDto> getIssueList(Long coinId, int page, int size) {
        try {
            log.info("관리자: 이슈 목록 조회 요청 (coinId: {}, page: {}, size: {})", coinId, page, size);
            // Feign 호출 (Page 객체 반환 가정)
            Page<IssueListResponseDto> resultPage = coinServiceClient.getIssueList(coinId, page, size);
            log.info("관리자: 이슈 목록 조회 성공 (coinId: {})", coinId);
            return resultPage.getContent();
        } catch (FeignException e) {
            log.error("이슈 목록 조회 중 코인 서비스 통신 오류: status={}, coinId={}", e.status(), coinId, e);
            throw handleFeignException("이슈 목록 조회", e);
        } catch (Exception e) {
            log.error("이슈 목록 조회 중 예기치 않은 오류 (coinId: {}): {}", coinId, e.getMessage(), e);
            throw new RuntimeException("이슈 목록 조회 중 예기치 않은 오류가 발생했습니다.", e);
        }
    }

    @Override
    public Long createIssue(Long coinId, IssueCreateRequestDto requestDto) {
        String adminUserId = getCurrentAdminUserId();
        log.info("관리자: 이슈 생성 요청 (coinId: {}, adminUserId: {})", coinId, adminUserId);

        try {
            // Feign 호출 시 adminUserId를 헤더로 전달
            ResponseEntity<Void> response = coinServiceClient.createIssue(coinId, requestDto, adminUserId);
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("이슈 생성 실패: 코인 서비스 응답 코드 {}", response.getStatusCode());
                throw handleResponseError("이슈 생성", response.getStatusCode().value(), null);
            }
            log.info("관리자: 이슈 생성 요청 성공 (coinId: {})", coinId);
            // 생성된 ID 반환 로직 (Location 헤더 파싱 등) 필요 시 추가, 여기서는 0L 임시 반환
            // String location = response.getHeaders().getFirst("Location");
            // return parseIdFromLocation(location);
            return 0L;
        } catch (FeignException e) {
            log.error("이슈 생성 중 코인 서비스 통신 오류: status={}, coinId={}", e.status(), coinId, e);
            throw handleFeignException("이슈 생성", e);
        } catch (Exception e) {
            log.error("이슈 생성 중 예기치 않은 오류 (coinId: {}): {}", coinId, e.getMessage(), e);
            throw new RuntimeException("이슈 생성 중 예기치 않은 오류가 발생했습니다.", e);
        }
    }

    @Override
    public IssueDetailResponseDto getIssueDetails(Long issueId) {
        try {
            log.info("관리자: 이슈 상세 조회 요청 (issueId: {})", issueId);
            IssueDetailResponseDto details = coinServiceClient.getIssueDetails(issueId);
            if (details == null) {
                throw new NoSuchElementException("이슈 상세 정보 응답이 없습니다. (ID: " + issueId + ")");
            }
            log.info("관리자: 이슈 상세 조회 성공 (issueId: {})", issueId);
            return details;
        } catch (FeignException.NotFound e) {
            log.warn("이슈 상세 조회 실패 (404 - Not Found): issueId={}", issueId);
            throw new NoSuchElementException("이슈 상세 정보를 찾을 수 없습니다. (ID: " + issueId + ")");
        } catch (FeignException e) {
            log.error("이슈 상세 조회 중 코인 서비스 통신 오류: status={}, issueId={}", e.status(), issueId, e);
            throw handleFeignException("이슈 상세 조회", e);
        } catch (Exception e) {
            log.error("이슈 상세 조회 중 예기치 않은 오류 (issueId: {}): {}", issueId, e.getMessage(), e);
            throw new RuntimeException("이슈 상세 조회 중 예기치 않은 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void updateIssue(Long issueId, IssueUpdateRequestDto requestDto) {
        try {
            log.info("관리자: 이슈 수정 요청 (issueId: {})", issueId);
            ResponseEntity<Void> response = coinServiceClient.updateIssue(issueId, requestDto);
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("이슈 수정 실패: 코인 서비스 응답 코드 {}", response.getStatusCode());
                throw handleResponseError("이슈 수정", response.getStatusCode().value(), issueId);
            }
            log.info("관리자: 이슈 수정 요청 성공 (issueId: {})", issueId);
        } catch (FeignException e) {
            log.error("이슈 수정 중 코인 서비스 통신 오류: status={}, issueId={}", e.status(), issueId, e);
            throw handleFeignException("이슈 수정", e);
        } catch (Exception e) {
            log.error("이슈 수정 중 예기치 않은 오류 (issueId: {}): {}", issueId, e.getMessage(), e);
            throw new RuntimeException("이슈 수정 중 예기치 않은 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void deleteIssues(List<Long> ids) {
        try {
            log.info("관리자: 이슈 삭제 요청 (ids: {})", ids);
            ResponseEntity<Void> response = coinServiceClient.deleteIssues(ids);
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("이슈 삭제 실패: 코인 서비스 응답 코드 {}", response.getStatusCode());
                throw handleResponseError("이슈 삭제", response.getStatusCode().value(), null);
            }
            log.info("관리자: 이슈 삭제 요청 성공 (ids: {})", ids);
        } catch (FeignException e) {
            log.error("이슈 삭제 중 코인 서비스 통신 오류: status={}, ids={}", e.status(), ids, e);
            throw handleFeignException("이슈 삭제", e);
        } catch (Exception e) {
            log.error("이슈 삭제 중 예기치 않은 오류 (ids: {}): {}", ids, e.getMessage(), e);
            throw new RuntimeException("이슈 삭제 중 예기치 않은 오류가 발생했습니다.", e);
        }
    }

    // 현재 인증된 관리자 ID (String 타입 UUID 가정) 반환
    private String getCurrentAdminUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("관리자 인증 정보를 찾을 수 없습니다."); // Security 관련 예외 사용 권장
        }
        // PrincipalUserDetails의 getUsername()이 User 엔티티의 userId(String UUID)를 반환한다고 가정
        return authentication.getName();
    }

    // Feign 예외 처리를 위한 공통 헬퍼 메서드 (예시)
    private RuntimeException handleFeignException(String operation, FeignException e) {
        String message = operation + " 중 코인 서비스와 통신에 실패했습니다.";
        if (e.status() == 404) {
            return new NoSuchElementException(message + " (요청 대상을 찾을 수 없음)");
        } else if (e.status() == 400) {
            return new IllegalArgumentException(message + " (잘못된 요청)");
        } else if (e.status() >= 500) {
            return new RuntimeException(message + " (코인 서비스 내부 오류 - " + e.status() + ")", e);
        }
        return new RuntimeException(message + " (오류코드: " + e.status() + ")", e);
    }

    // 응답 코드 기반 예외 처리 헬퍼 메서드 (예시)
    private RuntimeException handleResponseError(String operation, int statusCode, Long resourceId) {
        String resourceInfo = (resourceId != null) ? " (ID: " + resourceId + ")" : "";
        String baseMessage = operation + " 실패: ";
        if (statusCode == 404) {
            return new NoSuchElementException(baseMessage + "코인 서비스에서 해당 리소스를 찾지 못했습니다." + resourceInfo);
        } else if (statusCode == 400) {
            return new IllegalArgumentException(baseMessage + "잘못된 요청 또는 비즈니스 규칙 위반입니다.");
        } else {
            return new RuntimeException(baseMessage + "코인 서비스 오류 발생 (상태 코드: " + statusCode + ")");
        }
    }
}