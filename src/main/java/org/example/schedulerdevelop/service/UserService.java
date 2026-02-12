package org.example.schedulerdevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.dto.UserCreateRequestDto;
import org.example.schedulerdevelop.dto.UserResponseDto;
import org.example.schedulerdevelop.dto.UserUpdateRequestDto;
import org.example.schedulerdevelop.entity.User;
import org.example.schedulerdevelop.exception.DuplicateEmailException;
import org.example.schedulerdevelop.exception.UserNotFoundException;
import org.example.schedulerdevelop.constants.ErrorMessage;
import org.example.schedulerdevelop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 엔티티 생성을 User 생성자에 위임해 캡슐화 유지
    @Transactional
    public UserResponseDto saveUser(UserCreateRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateEmailException(ErrorMessage.EMAIL_ALREADY_EXISTS);
        }
        User user = new User(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
        return new UserResponseDto(userRepository.save(user));
    }

    // 전체 조회 - readOnly로 불필요한 스냅샷 생성 방지
    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .toList();
    }

    // 단건 조회 - 공통 조회 메서드로 중복 제거
    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        return new UserResponseDto(findUserById(id));
    }

    // 유저명, 이메일 수정 - 더티 체킹으로 별도 save 호출 없이 자동 반영
    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto updateDto) {
        User user = findUserById(id);
        if (updateDto.getEmail() != null && !updateDto.getEmail().isBlank()) {
            if (!user.getEmail().equals(updateDto.getEmail()) && userRepository.existsByEmail(updateDto.getEmail())) {
                throw new DuplicateEmailException(ErrorMessage.EMAIL_ALREADY_EXISTS);
            }
        }
        user.update(updateDto.getName(), updateDto.getEmail());
        return new UserResponseDto(user);
    }

    // 유저 삭제 - 삭제 전 유저명을 미리 저장해 응답에 활용
    @Transactional
    public String deleteUser(Long id) {
        User user = findUserById(id);
        String name = user.getName();
        userRepository.delete(user);
        return name;
    }

    // 공통 조회 메서드 - ScheduleService에서도 유저 조회 시 재사용
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }
}
