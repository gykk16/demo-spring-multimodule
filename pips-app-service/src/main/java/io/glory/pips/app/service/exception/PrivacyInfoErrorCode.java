package io.glory.pips.app.service.exception;

import lombok.Getter;

import io.glory.mcore.code.ResponseCode;

@Getter
public enum PrivacyInfoErrorCode implements ResponseCode {

    DATA_NOT_FOUND(false, "PIE001", "조회된 데이터가 없습니다."),
    //
    SAVE_ERROR(false, "PIE101", "저장 중 오류가 발생했습니다."),
    //
    UPDATE_ERROR(false, "PIE201", "수정 중 오류가 발생했습니다."),
    //
    DELETE_ERROR(false, "PIE301", "수정 중 오류가 발생했습니다."),
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
