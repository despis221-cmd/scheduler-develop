package org.example.schedulerdevelop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.dto.UserCreateRequestDto;
import org.example.schedulerdevelop.dto.UserResponseDto;
import org.example.schedulerdevelop.dto.UserUpdateRequestDto;
import org.example.schedulerdevelop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 유저 생성 - 201 Created 반환
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(requestDto));
    }

    // 전체 유저 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // 단건 유저 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    // 유저 수정 - 유저명, 이메일 수정 가능
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequestDto updateDto) {
        return ResponseEntity.ok(userService.updateUser(userId, updateDto));
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        String name = userService.deleteUser(userId);
        return ResponseEntity.ok(name + "이(가) 삭제되었습니다.");
    }
}
