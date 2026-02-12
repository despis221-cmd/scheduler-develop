package org.example.schedulerdevelop.constants;

public class ErrorMessage {
    public static final String AUTH_REQUIRED = "로그인이 필요합니다.";
    public static final String AUTH_FAILED = "이메일 또는 비밀번호가 일치하지 않습니다.";
    public static final String AUTHORIZATION_FAILED = "본인의 일정만 수정/삭제할 수 있습니다.";

    public static final String SCHEDULE_NOT_FOUND = "선택한 일정이 존재하지 않습니다.";
    public static final String USER_NOT_FOUND = "선택한 유저가 존재하지 않습니다.";
    public static final String COMMENT_NOT_FOUND = "선택한 댓글이 존재하지 않습니다.";

    public static final String EMAIL_ALREADY_EXISTS = "이미 사용 중인 이메일입니다.";

    private ErrorMessage() {
    }
}
