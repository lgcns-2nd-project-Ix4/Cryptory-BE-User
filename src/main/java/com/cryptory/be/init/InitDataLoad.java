package com.cryptory.be.init;


import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.repository.UserRepository;
import com.cryptory.be.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDataLoad {

    private final UserRepository userRepository;


    private final BCryptPasswordEncoder passwordEncoder;

    // 애플리케이션 시작 시 자동 db 저장
    @PostConstruct
    public void fetchInitialData() {

        User admin = User.createAdminUser("admin", passwordEncoder.encode("1234"), "관리자");
        userRepository.save(admin);

    }
}
