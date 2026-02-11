package org.example.schedulerdevelop.repository;

import org.example.schedulerdevelop.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository 상속으로 기본 CRUD 자동 제공
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByModifiedAtDesc(); // 수정일 기준 내림차순
}
