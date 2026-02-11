package org.example.schedulerdevelop.exception;

// RuntimeException 상속으로 트랜잭션 롤백 자동 처리
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
