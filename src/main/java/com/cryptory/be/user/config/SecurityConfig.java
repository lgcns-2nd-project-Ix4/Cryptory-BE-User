package com.cryptory.be.user.config;

import com.cryptory.be.user.handler.OAuth2FailureHandler;
import com.cryptory.be.user.handler.OAuth2SuccessHandler;
import com.cryptory.be.user.jwt.CustomLoginFilter;
import com.cryptory.be.user.jwt.JwtExceptionFilter;
import com.cryptory.be.user.jwt.JwtFilter;
import com.cryptory.be.user.jwt.JwtProvider;
import com.cryptory.be.user.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    private final JwtFilter jwtFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtProvider jwtProvider;

    private final AuthenticationConfiguration authenticationConfiguration;


    private final String[] whitelist = {
            "/api/v1/admin/users/admins",
            "/", "/admin/login",
            "/oauth2/callback",
            "/attach/files/**",
            "/css/**", "/error",
            "/h2-console/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .cors(cors -> cors.configurationSource(setCorsConfigurationSource()))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 경로에 admin 붙으면 관리자 페이지라고 가정
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers(whitelist).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/coins",
                                "/api/v1/coins/*",
                                "/api/v1/coins/*/news",
                                "/api/v1/coins/*/issues/*",
                                "/api/v1/coins/*/issues/*/comments",
                                "/api/v1/coins/*/posts",
                                "/api/v1/coins/*/posts/*",
                                "/api/v1/news"
                        ).permitAll()
                        .anyRequest().authenticated())

                // ---> H2 콘솔 프레임 허용 설정 추가 <---
                .headers(headers -> headers
                    .frameOptions(frameOptions -> frameOptions.sameOrigin()) // 같은 출처의 프레임 로드 허용
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, jwtFilter.getClass())

                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                )

                // 관리자 로그인하기 위함
                .addFilterAt(new CustomLoginFilter(authenticationManager(authenticationConfiguration), jwtProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource setCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 프론트엔드 주소 (배포 시 변경)
        configuration.setAllowedMethods(List.of("POST", "GET", "PUT", "PATCH", "DELETE")); // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(List.of("*")); // 모든 요청 헤더 허용

        // 클라이언트에서 Authorization 헤더를 읽을 수 있도록 설정
        configuration.setExposedHeaders(List.of("Authorization"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}