package com.cryptory.be.admin.service;

import com.cryptory.be.admin.dto.admin.AdminCreateRequestDto;
import com.cryptory.be.admin.dto.admin.AdminListResponseDto;
import com.cryptory.be.admin.dto.user.UserBlockRequestDto;
import com.cryptory.be.admin.dto.user.UserListResponseDto;
import com.cryptory.be.user.domain.Role;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminUserServiceImpl
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserServiceImpl implements AdminUserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Page<UserListResponseDto> getUserList(String keyword, int page, int size, String sort) {
        Sort sorting = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<User> users;

        if (keyword == null || keyword.isBlank()) {
            users = userRepository.findAll(pageable); // 모든 사용자 조회 (페이징)
        } else {
            users = userRepository.findByNicknameContaining(keyword, pageable); // 닉네임 검색 (페이징)
        }

        return users.map(this::convertToUserListResponseDto);
    }

    @Transactional
    @Override
    public boolean blockUser(Long userId, UserBlockRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다 ID: " + userId));

        user.deny(requestDto.isDenied());
        return user.isDenied();
    }

    @Override
    public Page<AdminListResponseDto> getAdminList(int page, int size, String sort) {
        Sort sorting = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<User> admins = userRepository.findByRole(Role.ADMIN, pageable);
        return admins.map(this::convertToAdminListResponseDto);
    }

    @Transactional
    @Override
    public boolean blockAdmin(Long userId, UserBlockRequestDto requestDto) {
        return blockUser(userId, requestDto);
    }

    // 관리자 생성 코드 -> 일단 암호화 적용했는데 이게 맞나??
    @Transactional
    @Override
    public Long createAdmin(AdminCreateRequestDto requestDto) {
        // ID 중복 확인
        if(userRepository.findByUserId(requestDto.getUserId()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 ID입니다: " +  requestDto.getUserId());
        }

        // 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // User 객체 생성
        User newAdmin = User.createAdminUser(requestDto.getUserId(), encodedPassword, requestDto.getNickname());

        try{
            User saveAdmin = userRepository.save(newAdmin);
            return saveAdmin.getId();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("데이터 베이스 저장 중 오류가 발생했습니다.", e);
        }
    }

    private UserListResponseDto convertToUserListResponseDto(User user) {
        return UserListResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .providerId(user.getProviderId())
                .role(user.getRole().getValue()) // Enum -> String
                .isDenied(user.isDenied())
                .build();
    }

    // User -> AdminListResponseDto 변환
    private AdminListResponseDto convertToAdminListResponseDto(User user) {
        return AdminListResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .role(user.getRole().getValue()) // Enum -> String
                .isDenied(user.isDenied())
                .build();
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isEmpty()) {
            return Sort.by("createdAt").descending(); // 기본 정렬 (생성일 내림차순)
        }

        String[] parts = sort.split(",");
        String property = parts[0];
        Sort.Direction direction = Sort.Direction.ASC;
        if (parts.length > 1 && "desc".equalsIgnoreCase(parts[1])) {
            direction = Sort.Direction.DESC;
        }

        return Sort.by(direction, property);
    }

}
