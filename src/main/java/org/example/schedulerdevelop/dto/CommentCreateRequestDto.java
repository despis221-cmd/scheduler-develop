package org.example.schedulerdevelop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.example.schedulerdevelop.constants.ValidationMessage;

@Getter
public class CommentCreateRequestDto {
    @NotBlank(message = ValidationMessage.COMMENT_CONTENT_REQUIRED)
    private String content;

    @NotNull(message = ValidationMessage.SCHEDULE_ID_REQUIRED)
    private Long scheduleId;

    @NotNull(message = ValidationMessage.USER_ID_REQUIRED)
    private Long userId;
}
