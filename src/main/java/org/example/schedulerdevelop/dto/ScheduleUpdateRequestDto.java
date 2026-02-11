package org.example.schedulerdevelop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleUpdateRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 30, message = "제목은 30자 이내여야 합니다.")
    private String title;
}
