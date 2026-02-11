package org.example.schedulerdevelop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

// 중복이나 기능별로 나눠두는 게 더 좋다고 하셔서 분리
@Getter
public class UserUpdateRequestDto {

    @Size(max = 30, message = "유저명은 30자 이내여야 합니다.")
    private String name;

    // 이메일 형식 자동 검증
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
}
