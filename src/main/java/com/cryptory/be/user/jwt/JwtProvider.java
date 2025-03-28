package com.cryptory.be.user.jwt;

import com.cryptory.be.user.exception.UserException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.cryptory.be.user.exception.UserErrorCode.INVALID_SIGNATURE;
import static com.cryptory.be.user.exception.UserErrorCode.INVALID_TOKEN;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    String secret;

    @Value("${jwt.expiration.access}")
    private Long ACCESS_TOKEN_EXPIRE_TIME;

    private SecretKey secretKey;

    // 해당 클래스 빈을 초기화한 뒤, 딱 한 번 호출되는 메서드
    @PostConstruct
    protected void initSecretKey() {
        this.secretKey = new SecretKeySpec(secret.getBytes(UTF_8),
                Jwts.SIG.HS512.key().build().getAlgorithm());
    }

    // accessToken 생성
    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME, "access");
    }

//    public String generateRefreshToken(Authentication authentication) {
//        return generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME, "refresh");
//    }

    private String generateToken(Authentication authentication, Long expirationMs, String category) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("category", category)
                .claim("authorities", authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey)
                .compact();
    }

    // todo: 검증 예외처리 필요
    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (SecurityException e) {
            throw new UserException(INVALID_SIGNATURE);
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            throw new UserException(INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtException("Invalid JWT token");
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("authorities").toString());

        User userPrincipal = new User(claims.getSubject(), "", authorities);
//        log.debug("User: {}", userPrincipal);

        // credentials: token
        return new UsernamePasswordAuthenticationToken(userPrincipal, token, authorities);
    }
}

