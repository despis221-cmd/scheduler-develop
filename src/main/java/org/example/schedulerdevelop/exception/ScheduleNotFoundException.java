package org.example.schedulerdevelop.exception;

// RuntimeException 상속으로 트랜잭션 롤백 자동 처리
public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
