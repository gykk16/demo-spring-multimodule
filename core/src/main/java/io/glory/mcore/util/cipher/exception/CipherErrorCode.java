package io.glory.mcore.util.cipher.exception;

import lombok.Getter;

import io.glory.mcore.code.ResponseCode;

@Getter
public enum CipherErrorCode implements ResponseCode {

    ENCRYPT_ERROR(false, "CIP101", "암호화 중 오류가 발생 했습니다."),
    DECRYPT_ERROR(false, "CIP201", "복호화 중 오류가 발생 했습니다."),
    CIPHER_ERROR(false, "CIP999", "암호화 오류가 발생 했습니다."),
    ;

    private final boolean success;
    private final String  code;
    private final String  message;

    CipherErrorCode(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return 406;
    }
}
