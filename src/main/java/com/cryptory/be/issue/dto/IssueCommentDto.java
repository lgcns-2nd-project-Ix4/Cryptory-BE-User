package com.cryptory.be.issue.dto;

import lombok.Data;

@Data
public class IssueCommentDto {
	
	private Long id;
    private String content;
    private String nickname;
    private String createdAt;
    
    public IssueCommentDto(Long id, String content, String nickname, String createdAt) {
        this.id = id;
        this.content = content;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
    
}
