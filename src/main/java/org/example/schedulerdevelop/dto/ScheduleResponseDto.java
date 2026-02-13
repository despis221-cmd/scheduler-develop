package org.example.schedulerdevelop.dto;

import lombok.Getter;
import org.example.schedulerdevelop.entity.Comment;
import org.example.schedulerdevelop.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private Long commentCount;
    private String title;
    private String content;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comments;

    public ScheduleResponseDto(Schedule schedule, Long commentCount) { // 응답 전용 객체에 담아 캡슐화 유지
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.name = schedule.getUser().getName();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.commentCount = commentCount;
        this.comments = null;
    }

    public ScheduleResponseDto(Schedule schedule, List<Comment> commentList) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.name = schedule.getUser().getName();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.commentCount = (long) commentList.size();
        this.comments = commentList.stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    public ScheduleResponseDto(Schedule schedule) {
        this(schedule, (Long) null);
    }
}