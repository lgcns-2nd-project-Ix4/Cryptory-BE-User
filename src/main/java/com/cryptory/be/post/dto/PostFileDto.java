package com.cryptory.be.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PostFileDto {
    private Long postFileId;

    private String originalFileName;
    private String storedDir;

    private String createdAt;

    public PostFileDto(Long postFileId, String originalFileName, String storedDir, String createdAt) {
        this.postFileId = postFileId;
        this.originalFileName = originalFileName;
        this.storedDir = storedDir;
        this.createdAt = createdAt;
    }
}
