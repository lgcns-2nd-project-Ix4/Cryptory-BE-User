package com.cryptory.be.post.domain;

import com.cryptory.be.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_files")
public class PostFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalFilename;  // 원본 파일명

    @Column(nullable = false)
    private String storedFilename;  // 저장 파일명

    @Column(nullable = false)
    private String storedDir;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // todo: enum 파일 타입 변경
    private String fileType;

    @Builder
    public PostFile(String originalFilename, String storedFilename, String storedDir, Post post, String fileType) {
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
        this.storedDir = storedDir;
        this.post = post;
        this.fileType = fileType;
    }
}
