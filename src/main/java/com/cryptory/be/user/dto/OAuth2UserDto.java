package com.cryptory.be.user.dto;

import com.cryptory.be.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Data
@Builder
public class OAuth2UserDto {

    private String nickname;
    private String imageUrl;
    private String providerId;
    private String providerName;

    public static OAuth2UserDto of(Map<String, Object> attributes, String registrationId) {
        if (registrationId.equalsIgnoreCase("kakao")) {
            return ofKakao(attributes, registrationId);
        }

        return null;
    }

    private static OAuth2UserDto ofKakao(Map<String, Object> attributes, String registrationId) {
        String providerId = String.valueOf(attributes.get("id"));

        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        return OAuth2UserDto.builder()
                .nickname((String) properties.get("nickname"))
                .imageUrl((String) properties.get("profile_image"))
                .providerId(providerId)
                .providerName(registrationId.toUpperCase())
                .build();
    }

    public User toUser() {
        return User.createOAuth2User(
                nickname,
                imageUrl,
                providerId,
                providerName
        );
    }
}

