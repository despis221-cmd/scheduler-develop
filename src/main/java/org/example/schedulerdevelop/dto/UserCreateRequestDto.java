package org.example.schedulerdevelop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.example.schedulerdevelop.constants.ValidationConstraints;
import org.example.schedulerdevelop.constants.ValidationMessage;

@Getter
public class UserCreateRequestDto {

    @NotBlank(message = ValidationMessage.NAME_REQUIRED)
    @Size(max = ValidationConstraints.NAME_MAX_LENGTH, message = ValidationMessage.NAME_MAX_LENGTH)
    private String name;

    // 이메일 형식 자동 검증
    @NotBlank(message = ValidationMessage.EMAIL_REQUIRED)
    @Email(message = ValidationMessage.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ValidationMessage.PASSWORD_REQUIRED)
    @Size(min = ValidationConstraints.PASSWORD_MIN_LENGTH, message = ValidationMessage.PASSWORD_MIN_LENGTH)
    private String password;
}
