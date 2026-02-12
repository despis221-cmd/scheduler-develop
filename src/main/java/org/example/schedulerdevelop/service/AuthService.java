package org.example.schedulerdevelop.service;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.dto.AuthLoginRequestDto;
import org.example.schedulerdevelop.entity.User;
import org.example.schedulerdevelop.exception.AuthFailException;
import org.example.schedulerdevelop.exception.AuthRequiredException;
import org.example.schedulerdevelop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    public static final String SESSION_USER_ID = "userId";

    @Transactional(readOnly = true)
    public Long login(AuthLoginRequestDto loginRequest, HttpSession session) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthFailException("이메일 또는 비밀번호가 일치하지 않습니다."));
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new AuthFailException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        session.setAttribute(SESSION_USER_ID, user.getId());
        return user.getId();
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public Long getLoginUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute(SESSION_USER_ID);
        if (userId == null) {
            throw new AuthRequiredException("로그인이 필요합니다.");
        }
        return userId;
    }
}
