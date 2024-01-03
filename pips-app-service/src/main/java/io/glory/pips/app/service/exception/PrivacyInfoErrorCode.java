package io.glory.pips.app.service.exception;

import lombok.Getter;

import io.glory.mcore.code.ResponseCode;

@Getter
public enum PrivacyInfoErrorCode implements ResponseCode {

    DATA_NOT_FOUND(false, "PIE001", "조회된 데이터가 없습니다."),
    //
    ERROR(false, "PIE999", "오류."),
    ;

    private final boolean success;
    private final String  code;
    private final String  message;

    PrivacyInfoErrorCode(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return 406;
    }

}
