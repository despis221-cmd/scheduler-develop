package org.example.schedulerdevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    // 단방향 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // Controller, Service가 필드를 직접 조작하지 않도록 캡슐화
    public Schedule(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    // 일정 수정 시 내용은 수정 안 함 (이전 과제 연결)
    public void update(String title) {
        if (title != null && !title.isBlank()) this.title = title;
    }
}
