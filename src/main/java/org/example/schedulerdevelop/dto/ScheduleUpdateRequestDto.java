package org.example.schedulerdevelop.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.example.schedulerdevelop.constants.ValidationConstraints;
import org.example.schedulerdevelop.constants.ValidationMessage;

@Getter
public class ScheduleUpdateRequestDto {
    @Size(max = ValidationConstraints.TITLE_MAX_LENGTH, message = ValidationMessage.TITLE_MAX_LENGTH)
    private String title;

    @Size(max = ValidationConstraints.CONTENT_MAX_LENGTH, message = ValidationMessage.CONTENT_MAX_LENGTH)
    private String content;
}
