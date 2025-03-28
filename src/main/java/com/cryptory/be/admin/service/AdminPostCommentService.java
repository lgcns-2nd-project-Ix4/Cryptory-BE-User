package com.cryptory.be.admin.service;

import java.util.List;

/**
 * packageName    : com.cryptory.be.admin.service
 * fileName       : AdminPostCommentService
 * author         : 조영상
 * date           : 2/22/25
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/22/25         조영상        최초 생성
 */
public interface AdminPostCommentService {
    // 게시글 강제 삭제
    void deletePosts(List<Long> ids);

    // 댓글 강제 삭제
    void deleteComment(Long commentId);
}
