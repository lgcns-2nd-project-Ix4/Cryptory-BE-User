package com.cryptory.be.issue.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class IssueDetailDto {
    private String title;
    private String summaryContent;
    private String newsTitle;
    private String source;

    // 뉴스가 총 두 개 있어야 하는데, 현재 영상님이 하나의 필드만 넣으신 상태라 이대로 진행하겠습니다.
    public IssueDetailDto(String title, String content, String newsTitle, String source) {
        this.title = title;
        this.summaryContent = content;
        this.newsTitle = newsTitle;
        this.source = source;
    }
}
