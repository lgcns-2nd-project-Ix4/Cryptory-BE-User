package com.cryptory.be.admin.dto.user;

/**
 * packageName    : com.cryptory.be.admin.dto.user
 * fileName       : UserListResponseDto
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

@Getter
@Builder
@AllArgsConstructor
@Jacksonized
public class UserListResponseDto {
    private Long userId;
    private String nickname;
    private String providerId; // 카카오 ID
    private String role;
    private boolean isDenied; // 차단 여부
}
