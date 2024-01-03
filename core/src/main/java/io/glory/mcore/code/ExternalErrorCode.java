package io.glory.mcore.code;

import lombok.Getter;

/**
 * 공통 에러 코드
 */
@Getter
public enum ExternalErrorCode implements ResponseCode {

    NO_RESPONSE(false, "EXT001", "응답 내용이 없습니다."),
    AUTH_ERROR(false, "EXT002", "인증 오류."),
    //
    TIME_OUT(false, "EXT100", "타임 아웃."),
    CONNECT_TIME_OUT(false, "EXT101", "Connect 타임 아웃."),
    READ_TIME_OUT(false, "EXT102", "Read 타임 아웃."),
    //
    REQUEST_ERROR(false, "EXT901", "외부 요청 오류 - Request"),
    RESPONSE_ERROR(false, "EXT902", "외부 요청 오류 - Response"),
    ERROR(false, "EXT999", "외부 요청 중 오류."),
    ;

    private final boolean success;
    private final String  code;
    private final String  message;

    ExternalErrorCode(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return 406;
    }

}
