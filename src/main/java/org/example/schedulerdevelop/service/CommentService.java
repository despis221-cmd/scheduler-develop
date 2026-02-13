package org.example.schedulerdevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.constants.ErrorMessage;
import org.example.schedulerdevelop.dto.CommentCreateRequestDto;
import org.example.schedulerdevelop.dto.CommentResponseDto;
import org.example.schedulerdevelop.entity.Comment;
import org.example.schedulerdevelop.entity.Schedule;
import org.example.schedulerdevelop.entity.User;
import org.example.schedulerdevelop.exception.AuthorizationException;
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
    public CommentResponseDto saveComment(CommentCreateRequestDto requestDto, Long loginUserId) {
        User user = userService.findUserById(loginUserId); // 데이터 무결성
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

    // 댓글 임의 수정 방지
    @Transactional
    public CommentResponseDto updateComment(Long id, String content, Long loginUserId) {
        Comment comment = findCommentById(id);
        validateCommentOwner(comment, loginUserId);
        comment.update(content);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long id, Long loginUserId) {
        Comment comment = findCommentById(id);
        validateCommentOwner(comment, loginUserId);
        commentRepository.delete(comment);
    }

    // 댓글 조회 시 일관된 예외 처리
    private Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException(ErrorMessage.COMMENT_NOT_FOUND)
        );
    }

    // 단순 존재 여부 확인만 필요하므로 Repository 직접 사용이 더 간단
    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException(ErrorMessage.SCHEDULE_NOT_FOUND)
        );
    }

    private void validateCommentOwner(Comment comment, Long loginUserId) {
        if (!comment.getUser().getId().equals(loginUserId)) {
            throw new AuthorizationException(ErrorMessage.AUTHORIZATION_FAILED);
        }
    }
}
