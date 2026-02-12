package org.example.schedulerdevelop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.example.schedulerdevelop.constants.ValidationMessage;

@Getter
public class AuthLoginRequestDto {
    @NotBlank(message = ValidationMessage.EMAIL_REQUIRED)
    @Email(message = ValidationMessage.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ValidationMessage.PASSWORD_REQUIRED)
    private String password;
}
