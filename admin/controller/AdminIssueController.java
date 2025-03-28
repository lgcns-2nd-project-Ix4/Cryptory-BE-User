package com.cryptory.be.admin.controller;

import com.cryptory.be.admin.dto.issue.*;
import com.cryptory.be.admin.service.AdminIssueService;
import com.cryptory.be.admin.service.AdminPostCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * packageName    : com.cryptory.be.admin.controller
 * fileName       : AdminIssueController
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminIssueController {

    private static final Logger log = LoggerFactory.getLogger(AdminIssueController.class);
    private final AdminIssueService adminIssueService;
    private final AdminPostCommentService adminPostCommentService; // 게시글/댓글 삭제를 위한 서비스

    // 특정 코인의 이슈 목록 조회
    @GetMapping("/coins/{coinId}/issues")
    public ResponseEntity<?> getIssueList(
            @PathVariable Long coinId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            List<IssueListResponseDto> issues = adminIssueService.getIssueList(coinId, page, size);
            return ResponseEntity.ok(issues);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }
    }

    // 특정 코인의 이슈 생성
    @PostMapping("/coins/{coinId}/issues")
    public ResponseEntity<?> createIssue(@PathVariable Long coinId, @Valid @RequestBody IssueCreateRequestDto requestDto) {
        try {
            Long newIssueId = adminIssueService.createIssue(coinId, requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("생성된 이슈의 ID :" + newIssueId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 이슈 상세 조회
    @GetMapping("/issues/{issueId}")
    public ResponseEntity<?> getIssueDetails(@PathVariable Long issueId) {
        try {
            IssueDetailResponseDto issue = adminIssueService.getIssueDetails(issueId);
            return ResponseEntity.ok(issue);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Issue id = " + issueId + "를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }
    }

    // 이슈 수정
    @PutMapping("/issues/{issueId}")
    public ResponseEntity<?> updateIssue(@PathVariable Long issueId, @RequestBody IssueUpdateRequestDto requestDto) {
        try {
            adminIssueService.updateIssue(issueId, requestDto);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Issue id = " + issueId + "를 찾을 수 없습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400 Bad Request, 에러 메시지
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다."+ e.getMessage());
        }
    }

    // 이슈 삭제 (일괄, 논리적 삭제)
    @PatchMapping("/issues")
    public ResponseEntity<?> deleteIssues(@RequestParam("ids") List<Long> ids, @RequestBody Map<String, Boolean> body) {
        try {
            if (body.containsKey("isDeleted") && body.get("isDeleted")) {
                adminIssueService.deleteIssues(ids);
                return ResponseEntity.ok("성공적으로 삭제되었습니다."); // 204 No Content
            } else {
                return ResponseEntity.badRequest().body("isDeleted 필드는 true여야 합니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }
    }

    // 이슈 코멘트 목록 조회
    @GetMapping("/issues/{issueId}/comments")
    public ResponseEntity<?> getIssueComments(
            @PathVariable Long issueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<IssueCommentListResponseDto> comments = adminIssueService.getIssueComments(issueId, page, size);
            return ResponseEntity.ok(comments);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }
    }

    // 이슈 코멘트 삭제 (논리적 삭제)
    @PatchMapping("/issue-comments/{commentId}")
    public ResponseEntity<?> deleteIssueComment(@PathVariable Long commentId, @RequestBody Map<String, Boolean> body) {
        try{
            if (body.containsKey("isDeleted") && body.get("isDeleted")) {
                adminIssueService.deleteIssueComment(commentId);
                return ResponseEntity.ok("성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("isDeleted 필드는 true여야 합니다.");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }
    }

    // 게시글 삭제 (일괄, 논리적 삭제)
    @PatchMapping("/posts")
    public ResponseEntity<?> deletePosts(@RequestParam("ids") List<Long> ids,  @RequestBody Map<String, Boolean> body) {
        try{
            if (body.containsKey("isDeleted") && body.get("isDeleted")) {
                adminPostCommentService.deletePosts(ids);
                return ResponseEntity.ok("성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("isDeleted 필드는 true여야 합니다.");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }
    }

    // 댓글 삭제 (논리적 삭제)
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @RequestBody Map<String, Boolean> body) {
        try{
            if (body.containsKey("isDeleted") && body.get("isDeleted")) {
                adminPostCommentService.deleteComment(commentId);
                return ResponseEntity.ok("성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("isDeleted 필드는 true여야 합니다.");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }
    }
}
