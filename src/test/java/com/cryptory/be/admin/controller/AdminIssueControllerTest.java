package com.cryptory.be.admin.controller;

import com.cryptory.be.admin.dto.IssueCreateRequestDto;
import com.cryptory.be.admin.service.AdminIssueService;
import com.cryptory.be.user.domain.Role;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * packageName    : com.cryptory.be.admin.controller
 * fileName       : AdminIssueControllerTest
 * author         : 조영상
 * date           : 2/25/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/25/25         조영상        최초 생성
 */
@SpringBootTest // 변경: SpringBootTest 사용
@AutoConfigureMockMvc
@Transactional // 각 테스트 메서드 실행 후 롤백
class AdminIssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository; // MockBean 대신 실제 UserRepository 사용

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean

    @BeforeEach
    void setUp() {
        // 관리자 사용자 추가 (DataInitializer 대신 여기서 직접 추가)
        if (userRepository.findByUserId("admin").isEmpty()) {
            User adminUser = new User("admin1", "1234", "admin1");
            userRepository.save(adminUser);
        }
    }

    @Test
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "customAdminDetailsService")
    void createIssue_success() throws Exception {
        // Given
        Long coinId = 1L;
        IssueCreateRequestDto requestDto = new IssueCreateRequestDto();
        requestDto.setTitle("Test Issue");
        requestDto.setSummaryContent("This is a test issue.");
        requestDto.setNewsTitle("Test News Title");
        requestDto.setSource("Test Source");
        requestDto.setDate("2025-02-11");

        String url = String.format("/api/v1/admin/coins/%d/issues", coinId);

        // When & Then
        ResultActions resultActions = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("생성된 이슈의 ID :")));
    }
}
