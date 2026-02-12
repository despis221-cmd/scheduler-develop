package org.example.schedulerdevelop.dto;

import lombok.Getter;
import org.example.schedulerdevelop.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final Long scheduleId;
    private final Long userId;
    private final String userName;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.scheduleId = comment.getSchedule().getId();
        this.userId = comment.getUser().getId();
        this.userName = comment.getUser().getName();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
