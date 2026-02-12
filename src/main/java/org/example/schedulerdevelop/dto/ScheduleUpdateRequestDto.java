package org.example.schedulerdevelop.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleUpdateRequestDto {
    @Size(max = 30, message = "제목은 30자 이내여야 합니다.")
    private String title;

    @Size(max = 200, message = "내용은 200자 이내여야 합니다.")
    private String content;
}
