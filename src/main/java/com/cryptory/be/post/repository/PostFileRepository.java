package com.cryptory.be.post.repository;

import com.cryptory.be.post.domain.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostFileRepository extends JpaRepository<PostFile, Long> {
    List<PostFile> findAllByPostId(Long postId);
}
