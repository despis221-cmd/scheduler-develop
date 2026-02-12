package org.example.schedulerdevelop.constants;

public class ValidationMessage {

    public static final String NAME_REQUIRED = "유저명은 필수입니다.";
    public static final String NAME_MAX_LENGTH = "유저명은 " + ValidationConstraints.NAME_MAX_LENGTH + "자 이내여야 합니다.";

    public static final String EMAIL_REQUIRED = "이메일은 필수입니다.";
    public static final String EMAIL_INVALID = "이메일 형식이 올바르지 않습니다.";

    public static final String PASSWORD_REQUIRED = "비밀번호는 필수입니다.";
    public static final String PASSWORD_MIN_LENGTH = "비밀번호는 최소 " + ValidationConstraints.PASSWORD_MIN_LENGTH + "글자 이상이어야 합니다.";

    public static final String TITLE_REQUIRED = "제목은 필수입니다.";
    public static final String TITLE_MAX_LENGTH = "제목은 " + ValidationConstraints.TITLE_MAX_LENGTH + "자 이내여야 합니다.";

    public static final String CONTENT_REQUIRED = "내용은 필수입니다.";
    public static final String CONTENT_MAX_LENGTH = "내용은 " + ValidationConstraints.CONTENT_MAX_LENGTH + "자 이내여야 합니다.";

    private ValidationMessage() {
    }
}
