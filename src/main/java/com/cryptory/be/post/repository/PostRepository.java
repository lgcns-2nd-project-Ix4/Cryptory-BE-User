package com.cryptory.be.post.repository;

import com.cryptory.be.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCoinId(Long coinId, Pageable pageable);

    long countByCoinId(Long coinId);

    //ID 목록에 해당하는 Post 중에서 isDeleted가 false인 Post만 조회
    @Query("SELECT p FROM Post p WHERE p.id IN :ids AND p.isDeleted = false ")
    List<Post> findAllByIdsAndNotDeleted(@Param("ids") List<Long> ids);
}
