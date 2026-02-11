package org.example.schedulerdevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.dto.ScheduleCreateRequestDto;
import org.example.schedulerdevelop.dto.ScheduleResponseDto;
import org.example.schedulerdevelop.dto.ScheduleUpdateRequestDto;
import org.example.schedulerdevelop.entity.Schedule;
import org.example.schedulerdevelop.exception.ScheduleNotFoundException;
import org.example.schedulerdevelop.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 엔티티 생성을 DTO에서 하지 않고 Schedule 생성자에 위임해 캡슐화 유지
    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleCreateRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    // 더티 체킹으로 별도 save 호출 없이 자동 반영
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto updateDto) {
        Schedule schedule = findScheduleById(id);
        schedule.update(updateDto.getTitle());
        return new ScheduleResponseDto(schedule);
    }

    // 삭제 전 제목을 미리 저장해 응답에 활용
    @Transactional
    public String deleteSchedule(Long id) {
        Schedule schedule = findScheduleById(id);
        String title = schedule.getTitle();
        scheduleRepository.delete(schedule);
        return title;
    }

    // 공통 조회 메서드로 중복 제거
    private Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new ScheduleNotFoundException("선택한 일정이 존재하지 않습니다.")
        );
    }
}
