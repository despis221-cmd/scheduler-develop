package org.example.schedulerdevelop.constants;

public class ResponseMessage {
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGOUT_SUCCESS = "로그아웃되었습니다.";
    public static final String COMMENT_DELETED = "댓글이 삭제되었습니다.";
    private static final String DELETED_FORMAT = "%s이(가) 삭제되었습니다.";

    private ResponseMessage() {
    }

    public static String deleted(String name) {
        return String.format(DELETED_FORMAT, name);
    }
}
