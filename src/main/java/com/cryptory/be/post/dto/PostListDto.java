package com.cryptory.be.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListDto {

    private List<PostDto> posts;
    private long totalItems;
    private int totalPages;

    public PostListDto(List<PostDto> posts, long totalItems, int totalPages) {
        this.posts = posts;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
