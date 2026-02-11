package org.example.schedulerdevelop.repository;

import org.example.schedulerdevelop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository 상속으로 기본 CRUD 자동 제공
public interface UserRepository extends JpaRepository<User, Long> {
}