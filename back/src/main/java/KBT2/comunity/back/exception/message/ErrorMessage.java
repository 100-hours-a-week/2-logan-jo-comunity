package KBT2.comunity.back.exception.message;

public class ErrorMessage {
    public final static String INVALID_REQUEST = "잘못된 요청입니다.";
    public final static String UNAUTHORIZED = "인증되지 않은 사용자입니다.";
    public final static String FORBIDDEN = "권한이 없습니다.";
    public final static String NOT_FOUND = "찾을 수 없는 리소스입니다.";
    public final static String INTERNAL_SERVER_ERROR = "서버 내부 오류가 발생했습니다.";
    public final static String CONFLICT = "이미 존재하는 리소스입니다.";

    // Token Error
    public final static String Token_NOT_FOUND = "토큰이 존재하지 않습니다.";
    public final static String INVALID_TOKEN = "유효하지 않은 토큰입니다.";

    public final static String EMAIL_ALREADY_EXISTS = "이미 존재하는 이메일입니다.";
    public final static String NICKNAME_ALREADY_EXISTS = "이미 존재하는 닉네임입니다.";
    public final static String EMAIL_NOT_FOUND = "존재하지 않는 이메일입니다.";
    public final static String USER_NOT_FOUND = "존재하지 않는 유저입니다.";
}
