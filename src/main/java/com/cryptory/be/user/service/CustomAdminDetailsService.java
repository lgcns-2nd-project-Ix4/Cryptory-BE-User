package com.cryptory.be.user.service;

import com.cryptory.be.user.domain.Role;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.dto.PrincipalUserDetails;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomAdminDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("CustomAdminDetailsService.loadUserByUsername: {}", username);
        log.info("userId: {}", username);

        User admin = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find Admin User"));

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new UsernameNotFoundException("Only Admin can login to access the service");
        }
        log.info("Returning PrincipalUserDetails for user: {}", username); // 반환 타입 로그
        return new PrincipalUserDetails(admin);
    }
}
