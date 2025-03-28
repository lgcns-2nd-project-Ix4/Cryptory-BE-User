package com.cryptory.be.user.repository;

import com.cryptory.be.user.domain.Role;
import com.cryptory.be.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    @Query("SELECT u FROM User u WHERE u.providerId = :providerId AND u.providerName = :providerName")
    Optional<User> findByProviderIdAndProviderName(@Param("providerId") String providerId, @Param("providerName") String providerName);

    void deleteByUserId(String userId);

    // userId (String 타입 UUID)에 keyword가 포함된 User 검색
    Page<User> findByUserIdContaining(String keyword, Pageable pageable);

    // 닉네임(nickname)에 keyword가 포함된 User 검색 (페이징)
    Page<User> findByNicknameContaining(String keyword, Pageable pageable);

    // role이 "ADMIN"인 User 조회 (페이징)
    Page<User> findByRole(Role role, Pageable pageable);

    Optional<User> findFirstByRole(Role role);
}
