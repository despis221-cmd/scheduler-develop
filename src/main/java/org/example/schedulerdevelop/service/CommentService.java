package org.example.schedulerdevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.constants.ErrorMessage;
import org.example.schedulerdevelop.dto.CommentCreateRequestDto;
import org.example.schedulerdevelop.dto.CommentResponseDto;
import org.example.schedulerdevelop.entity.Comment;
import org.example.schedulerdevelop.entity.Schedule;
import org.example.schedulerdevelop.entity.User;
import org.example.schedulerdevelop.exception.CommentNotFoundException;
import org.example.schedulerdevelop.exception.ScheduleNotFoundException;
import org.example.schedulerdevelop.repository.CommentRepository;
import org.example.schedulerdevelop.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    @Transactional
    public CommentResponseDto saveComment(CommentCreateRequestDto requestDto) {
        User user = userService.findUserById(requestDto.getUserId());
        Schedule schedule = findScheduleById(requestDto.getScheduleId());
        Comment comment = new Comment(requestDto.getContent(), schedule, user);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByScheduleId(Long scheduleId) {
        findScheduleById(scheduleId);
        return commentRepository.findAllByScheduleId(scheduleId)
                .stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getComment(Long id) {
        return new CommentResponseDto(findCommentById(id));
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, String content) {
        Comment comment = findCommentById(id);
        comment.update(content);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = findCommentById(id);
        commentRepository.delete(comment);
    }

    private Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException(ErrorMessage.COMMENT_NOT_FOUND)
        );
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException(ErrorMessage.SCHEDULE_NOT_FOUND)
        );
    }
}
