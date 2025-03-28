package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.admin.AdminCreateRequestDto;
import com.cryptory.be.admin.dto.admin.AdminListResponseDto;
import com.cryptory.be.admin.dto.user.UserBlockRequestDto;
import com.cryptory.be.admin.dto.user.UserListResponseDto;
import org.springframework.data.domain.Page;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminUserService
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
public interface AdminUserService {
    // ******사용자 관련*********
    // 사용자 목록 조회
    Page<UserListResponseDto> getUserList(String keyword, int page, int size, String sort);

    // 사용자 차단/차단해제
    boolean blockUser(Long userId, UserBlockRequestDto requestDto);

    // *********관리자 관련********
    // 관리자 목록 조회
    Page<AdminListResponseDto> getAdminList(int page, int size, String sort);

    // 관리자 차단/차단해제
    boolean blockAdmin(Long userId, UserBlockRequestDto requestDto);

    // 관리자 생성
    Long createAdmin(AdminCreateRequestDto requestDto);
}
