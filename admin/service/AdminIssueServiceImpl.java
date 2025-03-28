package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.issue.*;
import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.chart.repository.ChartRepository;
import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.coin.repository.CoinRepository;
import com.cryptory.be.issue.domain.Issue;
import com.cryptory.be.issue.domain.IssueComment;
import com.cryptory.be.issue.repository.IssueCommentRepository;
import com.cryptory.be.issue.repository.IssueRepository;
import com.cryptory.be.user.domain.Role;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.dto.PrincipalUserDetails;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminIssueServiceImpl
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminIssueServiceImpl implements AdminIssueService {

    private final CoinRepository coinRepository;
    private final IssueRepository issueRepository;
    private final IssueCommentRepository issueCommentRepository;
    private final ChartRepository chartRepository;
    private final UserRepository userRepository;

    @Override
    public List<IssueListResponseDto> getIssueList(Long coinId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 특정 코인에 대한 이슈만 조회, 삭제되지 않은 ISSUE만 조회
        Page<Issue> issues = issueRepository.findByCoinIdAndIsDeletedFalse(coinId, pageable);
        issues.forEach(issue -> log.info("issue {}", issue));

        return issues.stream().map(issue -> new IssueListResponseDto(issue.getId(), issue.getDate(), issue.getTitle(), issue.getUser().getNickname(), issue.getCreatedAt(), issue.getUpdatedAt())).toList();
    }

    @Transactional
    @Override
    public Long createIssue(Long coinId, IssueCreateRequestDto requestDto) {
        Coin coin = coinRepository.findById(coinId)
                .orElseThrow(() -> new NoSuchElementException("해당 코인을 찾을 수 없습니다. ID: " + coinId));
        log.info("coin {}", coin);
        String dateStr = requestDto.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "%";

        // date와 coinId로 chart 조회
        Optional<Chart> chart = chartRepository.findByDateAndCoinId(dateStr, coinId);
                //.orElseThrow(() -> new NoSuchElementException("해당 날짜와 코인에 대한 차트를 찾을 수 없습니다. Date: " + requestDto.getDate() + ", Coin ID: " + coinId));
        log.info("chart {}", chart);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUserId(authentication.getName());
        log.info("authentication {}", authentication);
        log.info("user {}", user);
        // 캐스팅
        //log.info("principalDetails {}", principalDetails);
        //Optional<User> user = userRepository.findFirstByRole(Role.ADMIN);
        //user.orElseGet(() -> new User("admin_2", "1234", "admin_2"))
        Issue newIssue = Issue.builder()
                .date(requestDto.getDate())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .newsTitle(requestDto.getNewsTitle())
                .source(requestDto.getSource())
                .type("MANUAL") // 관리자가 생성 이슈니까
                .user(user.get()) // 작성자 설정 (관리자)
                .coin(coin)      // coin 설정
                .chart(chart.get()) // chart 설정
                .requestCount(0L)
                .isDeleted(false)
                .build();

        log.info("newIssue {}", newIssue);

        // issueRepository 생성 필요, 현재 기준 없음
        return issueRepository.save(newIssue).getId();
    }

    @Override
    public IssueDetailResponseDto getIssueDetails(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 이슈를 찾을 수 없습니다. ID: " + issueId));

        return convertToIssueDetailResponseDto(issue);
    }

    @Transactional
    @Override
    public void updateIssue(Long issueId, IssueUpdateRequestDto requestDto) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 이슈를 찾을 수 없습니다. ID: " + issueId));
        try {
            issue.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getNewsTitle(), requestDto.getSource());
        } catch (IllegalArgumentException e) {
            // 유효성 검사 실패
            throw e;
        }
    }

    @Transactional
    @Override
    public void deleteIssues(List<Long> ids) {
        List<Issue> issues = issueRepository.findAllById(ids);
        issues.forEach(Issue::delete);
        //issueRepository.softDeleteByIds(ids);
    }

    @Override
    public Page<IssueCommentListResponseDto> getIssueComments(Long issueId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IssueComment> comments = issueCommentRepository.findCommentsByIssueId(issueId, pageable);
        return comments.map(this::convertToIssueCommentListResponseDto);
    }

    @Transactional
    @Override
    public void deleteIssueComment(Long commentId) {
        IssueComment comment = issueCommentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("해당 토론방 댓글을 찾을 수 없습니다. ID: " + commentId));
        comment.delete();
    }


    // Issue -> IssueListResponseDto 변환
    private IssueListResponseDto convertToIssueListResponseDto(Issue issue) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUserDetails principalDetails = (PrincipalUserDetails) authentication.getPrincipal();
        User adminUser = principalDetails.getUser();

        return IssueListResponseDto.builder()
                .issueId(issue.getId())
                .date(issue.getDate())
                .title(issue.getTitle())
                .createdBy(adminUser.getNickname())
                .createdAt(issue.getCreatedAt())
                .updatedAt(issue.getUpdatedAt())
                .build();
    }

    // Issue -> IssueDetailResponseDto 변환
    private IssueDetailResponseDto convertToIssueDetailResponseDto(Issue issue) {
        return IssueDetailResponseDto.builder()
                .issueId(issue.getId())
                .date(issue.getDate())
                .title(issue.getTitle())
                .content(issue.getContent())
                .createdBy(issue.getUser().getNickname())
                .createdAt(issue.getCreatedAt())
                .updatedAt(issue.getUpdatedAt())
                .newsTitle(issue.getNewsTitle())
                .source(issue.getSource())
                .type(issue.getType())
                .isDeleted(issue.isDeleted())
                .build();
    }

    // IssueComment -> IssueCommentListResponseDto 변환
    private IssueCommentListResponseDto convertToIssueCommentListResponseDto(IssueComment comment) {
        return IssueCommentListResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .isDeleted(comment.isDeleted())
                .userId(comment.getUser().getId())
                .userNickname(comment.getUser().getNickname())
                .build();
    }
}
