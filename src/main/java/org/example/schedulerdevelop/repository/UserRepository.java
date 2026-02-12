package org.example.schedulerdevelop.repository;

import org.example.schedulerdevelop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository 상속으로 기본 CRUD 자동 제공
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}