package org.example.schedulerdevelop.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.schedulerdevelop.config.PasswordEncoder;
import org.example.schedulerdevelop.constants.ErrorMessage;
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
    private final PasswordEncoder passwordEncoder;
    public static final String SESSION_USER_ID = "userId";

    // 보안상 두 경우 모두 동일한 메시지로 처리하여 정보 노출 방지
    @Transactional(readOnly = true)
    public void login(AuthLoginRequestDto loginRequest, HttpSession session) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthFailException(ErrorMessage.AUTH_FAILED));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthFailException(ErrorMessage.AUTH_FAILED);
        }
        session.setAttribute(SESSION_USER_ID, user.getId());
    }

    // 로그아웃 시 모든 세션 정보를 제거하는 것이 보안상 안전
    public void logout(HttpSession session) {
        session.invalidate();
    }

    public Long getLoginUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute(SESSION_USER_ID);
        if (userId == null) {
            throw new AuthRequiredException(ErrorMessage.AUTH_REQUIRED);
        }
        return userId;
    }
}
