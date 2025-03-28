package com.cryptory.be.issue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateIssueCommentDto {
	
	@NotBlank
    private String content;
}
