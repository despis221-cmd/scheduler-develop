package org.example.schedulerdevelop.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray()); // BCrypt의 cost factor는 계산 비용을 결정, MIN_COST(4) - 빠른 응답을 위해 사용
    }

    // BCrypt는 단방향 암호화이므로 복호화 불가능
    // BCrypt.verifyer() - salt를 자동으로 추출하여 올바르게 비교
    // boolean 타입으로 명확하게 성공/실패 표현
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}