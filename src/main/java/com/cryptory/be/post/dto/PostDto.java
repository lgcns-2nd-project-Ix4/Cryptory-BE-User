package com.cryptory.be.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    private Long id;
    private String title;
    private String nickname;
    private String createdAt;

    public PostDto(Long id, String title, String nickname, String createdAt) {
        this.id = id;
        this.title = title;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}
