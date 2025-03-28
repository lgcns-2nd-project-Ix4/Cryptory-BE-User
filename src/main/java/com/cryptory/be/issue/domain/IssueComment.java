package com.cryptory.be.issue.domain;

import com.cryptory.be.chart.domain.Chart;
import com.cryptory.be.global.entity.BaseTimeEntity;
import com.cryptory.be.user.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "issue_comments")
public class IssueComment extends BaseTimeEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;
    
    @Builder
    public IssueComment(String content, User user, Issue issue) {
        this.content = content;
        this.user = user;
        this.issue = issue;

        this.isDeleted = false;
    }
    
    public void update(String content) {
    	this.content = content != null ? content : this.content;
    }
    
    public void delete() {
        this.isDeleted = true;
    }
    
    public boolean isNotDeleted() {
        return !this.isDeleted;
    }

}
