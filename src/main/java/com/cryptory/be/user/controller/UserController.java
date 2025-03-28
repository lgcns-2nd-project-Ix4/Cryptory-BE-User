package com.cryptory.be.user.controller;

import com.cryptory.be.global.response.ApiResponse;
import com.cryptory.be.user.dto.UpdateUserDto;
import com.cryptory.be.user.dto.UserInfoDto;
import com.cryptory.be.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserInfoDto> getUser(Principal principal) {
//        log.info("principal: {}", principal);
        UserInfoDto userInfoDto = userService.getUser(principal.getName());
        return new ApiResponse<>(HttpStatus.OK, userInfoDto);
    }

    @DeleteMapping("/me")
    public ApiResponse<?> deleteUser(Principal principal) {
        userService.deleteUser(principal.getName());
        return new ApiResponse<>(HttpStatus.OK, "유저를 삭제했습니다.");
    }

    @PatchMapping("/me/nickname")
    public ApiResponse<?> updateNickname(Principal principal, @RequestBody UpdateUserDto updateUserDto) {
//        log.info("principal: {}", principal);
        userService.updateNickname(principal.getName(), updateUserDto.getNickname());
        return new ApiResponse<>(HttpStatus.OK, "유저 정보를 업데이트했습니다.");
    }

    @PatchMapping("/me/image")
    public ApiResponse<?> updateImage(Principal principal, @RequestPart MultipartFile file) {
        userService.updateImage(principal.getName(), file);
        return new ApiResponse<>(HttpStatus.OK, "프로필 이미지를 업데이트했습니다.");
    }
}
