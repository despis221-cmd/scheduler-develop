package org.example.schedulerdevelop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.example.schedulerdevelop.constants.ValidationConstraints;
import org.example.schedulerdevelop.constants.ValidationMessage;

// 중복이나 기능별로 나눠두는 게 더 좋다고 하셔서 분리
@Getter
public class UserUpdateRequestDto {
    @Size(max = ValidationConstraints.NAME_MAX_LENGTH, message = ValidationMessage.NAME_MAX_LENGTH)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$", message = ValidationMessage.NAME_PATTERN)
    private String name;

    // 이메일 형식 자동 검증
    @Email(message = ValidationMessage.EMAIL_INVALID)
    private String email;
}
