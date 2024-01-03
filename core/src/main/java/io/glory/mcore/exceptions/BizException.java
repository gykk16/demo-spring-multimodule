package io.glory.mcore.exceptions;

import lombok.Getter;

import io.glory.mcore.code.ResponseCode;

@Getter
public class BizException extends Exception {

    private final ResponseCode code;
    private final String       errorMessage;
    private final boolean      printStackTrace;
    private final boolean      saveDb;

    public BizException(ResponseCode code) {
        this(code, null);
    }

    public BizException(ResponseCode code, Throwable cause) {
        this(code, code.getMessage(), cause);
    }

    public BizException(ResponseCode code, String errorMessage, Throwable cause) {
        this(code, errorMessage, true, cause);
    }

    public BizException(ResponseCode code, String errorMessage, boolean printStackTrace, Throwable cause) {
        this(code, errorMessage, printStackTrace, false, cause);
    }

    public BizException(ResponseCode code, String errorMessage, boolean printStackTrace, boolean saveDb,
            Throwable cause) {
        super(code.getCodeAndMessage(), cause);
        this.code = code;
        this.errorMessage = errorMessage;
        this.printStackTrace = printStackTrace;
        this.saveDb = saveDb;
    }

}
