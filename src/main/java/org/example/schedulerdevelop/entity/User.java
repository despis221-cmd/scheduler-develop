package org.example.schedulerdevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // 이메일 중복 가입 방지
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // 부분 수정 가능 - null이나 빈 문자열 전송 시 기존 값 유지
    public void update(String name, String email) {
        if (name != null && !name.isBlank()) this.name = name;
        if (email != null && !email.isBlank()) this.email = email;
    }
}
