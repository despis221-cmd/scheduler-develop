package org.example.schedulerdevelop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.constants.ResponseMessage;
import org.example.schedulerdevelop.dto.UserCreateRequestDto;
import org.example.schedulerdevelop.dto.UserResponseDto;
import org.example.schedulerdevelop.dto.UserUpdateRequestDto;
import org.example.schedulerdevelop.service.AuthService;
import org.example.schedulerdevelop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    // 유저 생성 - 201 Created 반환
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto requestDto) {
        UserResponseDto responseDto = userService.saveUser(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // 단건 유저 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        UserResponseDto user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // 유저 수정 - 유저명, 이메일 수정 가능
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequestDto updateDto, HttpSession session) {
        Long loginUserId = authService.getLoginUserId(session);
        UserResponseDto updatedUser = userService.updateUser(userId, updateDto, loginUserId);
        return ResponseEntity.ok(updatedUser);
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userId, HttpSession session) {
        Long loginUserId = authService.getLoginUserId(session);
        String name = userService.deleteUser(userId, loginUserId);
        return ResponseEntity.ok(Map.of("message", ResponseMessage.deleted(name)));
    }
}
