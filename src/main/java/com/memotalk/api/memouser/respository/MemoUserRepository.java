package com.memotalk.api.memouser.respository;

import com.memotalk.api.memouser.entity.MemoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoUserRepository extends JpaRepository<MemoUser, Long> {
    boolean existsByEmail(String email);

    Optional<MemoUser> findByEmail(String email);
}
