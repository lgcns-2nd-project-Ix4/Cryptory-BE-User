package com.cryptory.be.user.handler;

import com.cryptory.be.user.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String access = jwtProvider.generateAccessToken(authentication);
        log.info("생성된 access token: {}", access);

        Cookie cookie = new Cookie("accessToken", access);
//        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(10);     // 쿠키 유효기간 10초

        response.addCookie(cookie);

        String redirectUrl = "http://localhost:3000/oauth2/callback";
        response.sendRedirect(redirectUrl);
    }
}