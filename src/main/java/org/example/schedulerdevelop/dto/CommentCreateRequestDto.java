package org.example.schedulerdevelop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.example.schedulerdevelop.constants.ValidationMessage;

@Getter
public class CommentCreateRequestDto {
    @NotBlank(message = ValidationMessage.COMMENT_CONTENT_REQUIRED)
    private String content;
}
