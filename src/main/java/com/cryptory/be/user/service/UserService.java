package com.cryptory.be.user.service;

import com.cryptory.be.global.util.FileUtils;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.dto.UserInfoDto;
import com.cryptory.be.user.exception.UserErrorCode;
import com.cryptory.be.user.exception.UserException;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final FileUtils fileUtils;

    // todo 환경 변수로 이동해야함
    private final static String DOMAIN = "http://localhost:8080";
    private final static String IMAGE_PATH = "/attach/files";

    // todo: 에러 처리
    public UserInfoDto getUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXIST_USER));

        return modelMapper.map(user, UserInfoDto.class);
    }

    @Transactional
    public void deleteUser(String userId) {
        userRepository.deleteByUserId(userId);
    }

    @Transactional
    public void updateNickname(String userId, String nickname) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXIST_USER));

        user.updateNickname(nickname);
    }

    @Transactional
    public void updateImage(String userId, MultipartFile file) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_EXIST_USER));

        // 우선 게시글 이미지 업로드와 동일하게 처리(위치 동일하게)
        String imageUrl = DOMAIN + IMAGE_PATH + fileUtils.saveFile(file);

        user.updateImage(imageUrl);
    }
}
