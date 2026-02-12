package org.example.schedulerdevelop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.dto.ScheduleCreateRequestDto;
import org.example.schedulerdevelop.dto.ScheduleResponseDto;
import org.example.schedulerdevelop.dto.ScheduleUpdateRequestDto;
import org.example.schedulerdevelop.service.AuthService;
import org.example.schedulerdevelop.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final AuthService authService;

    // 일정 생성 API - 201 Created 반환 (200보다 REST 의미에 더 부합)
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleCreateRequestDto requestDto, HttpSession session) {
        Long loginUserId = authService.getLoginUserId(session);
        ScheduleResponseDto responseDto = scheduleService.saveSchedule(requestDto, loginUserId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // 전체 일정 조회 API
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules() {
        List<ScheduleResponseDto> schedules = scheduleService.getSchedules();
        return ResponseEntity.ok(schedules);
    }

    // 선택 일정 조회 API
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId) {
        ScheduleResponseDto schedule = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.ok(schedule);
    }

    // 일정 수정 API - 삭제된 일정의 제목을 응답에 포함해 사용자 피드백 제공
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long scheduleId, @Valid @RequestBody ScheduleUpdateRequestDto updateDto, HttpSession session) {
        Long loginUserId = authService.getLoginUserId(session);
        ScheduleResponseDto updated = scheduleService.updateSchedule(scheduleId, updateDto, loginUserId);
        return ResponseEntity.ok(updated);
    }

    // 일정 삭제 API
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Map<String, String>> deleteSchedule(@PathVariable Long scheduleId, HttpSession session) {
        Long loginUserId = authService.getLoginUserId(session);
        String title = scheduleService.deleteSchedule(scheduleId, loginUserId);
        String message = title + "이(가) 삭제되었습니다.";
        return ResponseEntity.ok(Map.of("message", message));
    }
}
