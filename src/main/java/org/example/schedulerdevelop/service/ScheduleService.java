package org.example.schedulerdevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.constants.ErrorMessage;
import org.example.schedulerdevelop.dto.ScheduleCreateRequestDto;
import org.example.schedulerdevelop.dto.ScheduleResponseDto;
import org.example.schedulerdevelop.dto.ScheduleUpdateRequestDto;
import org.example.schedulerdevelop.entity.Comment;
import org.example.schedulerdevelop.entity.Schedule;
import org.example.schedulerdevelop.entity.User;
import org.example.schedulerdevelop.exception.AuthorizationException;
import org.example.schedulerdevelop.exception.ScheduleNotFoundException;
import org.example.schedulerdevelop.repository.CommentRepository;
import org.example.schedulerdevelop.repository.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.schedulerdevelop.constants.ValidationConstraints.DEFAULT_PAGE_SIZE;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    // 엔티티 생성을 Schedule 생성자에 위임해 캡슐화 유지
    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleCreateRequestDto requestDto, Long loginUserId) {
        User user = userService.findUserById(loginUserId);
        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContent(), user);
        return new ScheduleResponseDto(scheduleRepository.save(schedule));
    }

    @Transactional(readOnly = true)
    public Page<ScheduleResponseDto> getSchedulesWithPaging(int page, int size) {
        if (size <= 0 || size > 100) size = DEFAULT_PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        Page<Schedule> schedulePage = scheduleRepository.findAllByOrderByModifiedAtDesc(pageable);

        return schedulePage.map(schedule -> {
            Long commentCount = commentRepository.countByScheduleId(schedule.getId());
            return new ScheduleResponseDto(schedule, commentCount);
        });
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = findScheduleById(id);
        List<Comment> comments = commentRepository.findAllByScheduleId(id);
        return new ScheduleResponseDto(schedule, comments);
    }

    // 더티 체킹으로 별도 save 호출 없이 자동 반영
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto updateDto, Long loginUserId) {
        Schedule schedule = findScheduleById(id);
        checkScheduleOwnership(schedule, loginUserId);
        schedule.update(updateDto.getTitle());
        return new ScheduleResponseDto(schedule);
    }


    // 삭제 전 제목을 미리 저장해 응답에 활용
    @Transactional
    public String deleteSchedule(Long id, Long loginUserId) {
        Schedule schedule = findScheduleById(id);
        checkScheduleOwnership(schedule, loginUserId);
        String title = schedule.getTitle();
        scheduleRepository.delete(schedule);
        return title;
    }

    // 공통 조회 메서드로 중복 제거
    private Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new ScheduleNotFoundException(ErrorMessage.SCHEDULE_NOT_FOUND)
        );
    }

    // 일정 소유권 검증 메서드
    private void checkScheduleOwnership(Schedule schedule, Long loginUserId) {
        if (!schedule.getUser().getId().equals(loginUserId)) {
            throw new AuthorizationException(ErrorMessage.AUTHORIZATION_FAILED);
        }
    }
}
