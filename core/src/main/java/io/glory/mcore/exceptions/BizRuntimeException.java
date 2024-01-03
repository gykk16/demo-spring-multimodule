package io.glory.mcore.exceptions;

import lombok.Getter;

import io.glory.mcore.code.ResponseCode;

@Getter
public class BizRuntimeException extends RuntimeException {

    private final ResponseCode code;
    private final String       errorMessage;
    private final boolean      printStackTrace;
    private final boolean      saveDb;

    public BizRuntimeException(ResponseCode code) {
        this(code, null);
    }

    public BizRuntimeException(ResponseCode code, Throwable cause) {
        this(code, code.getMessage(), cause);
    }

    public BizRuntimeException(ResponseCode code, String errorMessage, Throwable cause) {
        this(code, errorMessage, true, cause);
    }

    public BizRuntimeException(ResponseCode code, String errorMessage, boolean printStackTrace, Throwable cause) {
        this(code, errorMessage, printStackTrace, false, cause);
    }

    public BizRuntimeException(ResponseCode code, String errorMessage, boolean printStackTrace, boolean saveDb,
            Throwable cause) {
        super(code.getCodeAndMessage(), cause);
        this.code = code;
        this.errorMessage = errorMessage;
        this.printStackTrace = printStackTrace;
        this.saveDb = saveDb;
    }

}
