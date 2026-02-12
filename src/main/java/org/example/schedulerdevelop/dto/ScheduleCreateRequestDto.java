package org.example.schedulerdevelop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.example.schedulerdevelop.constants.ValidationConstraints;
import org.example.schedulerdevelop.constants.ValidationMessage;

@Getter
public class ScheduleCreateRequestDto {

    @NotBlank(message = ValidationMessage.TITLE_REQUIRED)
    @Size(max = ValidationConstraints.TITLE_MAX_LENGTH, message = ValidationMessage.TITLE_MAX_LENGTH)
    private String title;

    @NotBlank(message = ValidationMessage.CONTENT_REQUIRED)
    @Size(max = ValidationConstraints.CONTENT_MAX_LENGTH, message = ValidationMessage.CONTENT_MAX_LENGTH)
    private String content;
}
