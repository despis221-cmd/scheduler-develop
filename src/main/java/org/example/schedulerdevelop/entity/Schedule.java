package org.example.schedulerdevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.schedulerdevelop.dto.ScheduleCreateRequestDto;

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

    //TODO 추후 수정
    @Column(nullable = false)
    private String name;

    // Controller, Service가 필드를 직접 조작하지 않도록 캡슐화
    public Schedule(ScheduleCreateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.name = requestDto.getName();
    }

    public void update(String title) {
        this.title = title;
    }
}
