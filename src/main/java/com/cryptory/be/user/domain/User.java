package com.cryptory.be.user.domain;

import com.cryptory.be.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // providerId는 인증 서버마다 다르고, 중복 위험이 있으므로(maybe) UUID로 unique 값
    private String userId;

    private String nickname;

    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    // 리소스 서버가 준 사용자의 고유 식별값
    private String providerId;

    // 리소스 서버 이름
    private String providerName;

    private boolean isDenied = false;

    @Column // 관리자용 패스워드
    private String password;

    public User(String nickname, String imageUrl, String providerId, String providerName) {
        this.role = Role.USER; // OAuth2 로그인 시 기본 역할은 USER
        this.isDenied = false;
        this.userId = UUID.randomUUID().toString();
        this.password = "";

        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.providerId = providerId;
        this.providerName = providerName;
    }

    public User(String userId, String password, String nickname) {
        this.role = Role.ADMIN; // 관리자 회원가입 시 기본 역할은 ADMIN
        this.isDenied = false;
        this.imageUrl = "";
        this.providerId = "";
        this.providerName = "";

        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }

    @Builder(builderClassName = "OAuth2UserBuilder", builderMethodName = "oauth2UserBuilder")
    public static User createOAuth2User(String nickname, String imageUrl, String providerId, String providerName) {
        return new User(nickname, imageUrl, providerId, providerName);
    }

    // 관리자 회원가입을 위한 빌더
    @Builder(builderClassName = "AdminUserBuilder", builderMethodName = "adminUserBuilder")
    public static User createAdminUser(String userId, String password, String nickname) {
        return new User(userId, password, nickname);
    }

    public void deny(boolean status) {
        this.isDenied = status;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
