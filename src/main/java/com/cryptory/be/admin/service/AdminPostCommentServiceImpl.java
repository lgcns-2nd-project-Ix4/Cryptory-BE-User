package com.cryptory.be.admin.service;

import com.cryptory.be.post.domain.Post;
import com.cryptory.be.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminPostCommentServiceImpl
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminPostCommentServiceImpl implements AdminPostCommentService {
    private final PostRepository postRepository;
    //private final CommentRepository commentRepository;

    @Override
    public void deletePosts(List<Long> ids) {
        List<Post> posts = postRepository.findAllByIdsAndNotDeleted(ids);
        posts.forEach(Post::delete);
    }

    // TODO. Comment 엔티티와 리포지토리 생성 후 개발
    @Override
    public void deleteComment(Long commentId) {

    }
}
