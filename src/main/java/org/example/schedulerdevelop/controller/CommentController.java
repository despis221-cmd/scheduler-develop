package org.example.schedulerdevelop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.constants.ResponseMessage;
import org.example.schedulerdevelop.dto.CommentCreateRequestDto;
import org.example.schedulerdevelop.dto.CommentResponseDto;
import org.example.schedulerdevelop.dto.CommentUpdateRequestDto;
import org.example.schedulerdevelop.service.AuthService;
import org.example.schedulerdevelop.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {
    private final CommentService commentService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentCreateRequestDto requestDto, HttpSession session) {
        Long loginUserId = authService.getLoginUserId(session);
        CommentResponseDto responseDto = commentService.saveComment(requestDto, loginUserId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getCommentsBySchedule(@PathVariable Long scheduleId) {
        List<CommentResponseDto> comments = commentService.getCommentsByScheduleId(scheduleId);
        return ResponseEntity.ok(comments);
    }


    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long scheduleId, @PathVariable Long commentId, @Valid @RequestBody CommentUpdateRequestDto requestDto, HttpSession session) {
        Long loginUserId = authService.getLoginUserId(session);
        CommentResponseDto updated = commentService.updateComment(commentId, requestDto.getContent(), loginUserId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long scheduleId, @PathVariable Long commentId, HttpSession session) {
        Long loginUserId = authService.getLoginUserId(session);
        commentService.deleteComment(commentId, loginUserId);
        return ResponseEntity.ok(Map.of("message", ResponseMessage.COMMENT_DELETED));
    }

}