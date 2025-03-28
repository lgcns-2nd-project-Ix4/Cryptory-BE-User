package com.cryptory.be.user.jwt;

import com.cryptory.be.user.exception.UserErrorCode;
import com.cryptory.be.user.exception.UserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final int AUTHORIZATION_HEADER_BEGIN_INDEX = 7;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.info("JwtFilter 실행됨: {}", request.getRequestURI());

        String accessToken = request.getHeader("Authorization");

        log.info("access token: {}", accessToken);

        if(accessToken == null || accessToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        accessToken = accessToken.substring(AUTHORIZATION_HEADER_BEGIN_INDEX);

        validateTokens(response, accessToken);

        setAuthentication(accessToken);

        filterChain.doFilter(request, response);
    }

    private void validateTokens(HttpServletResponse response, String accessToken) {
        if(!jwtProvider.validateToken(accessToken)) {
            // refresh token 구현 안 해서 access token 만료시 처리해야됨
            throw new UserException(UserErrorCode.TOKEN_EXPIRED);
        }
    }

    private void setAuthentication(String accessToken) {
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        log.info("최종 SecurityContext Authentication: {}", authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
