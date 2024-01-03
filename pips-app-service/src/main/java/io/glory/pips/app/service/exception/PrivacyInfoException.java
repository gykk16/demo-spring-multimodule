package io.glory.pips.app.service.exception;

import io.glory.mcore.code.ResponseCode;
import io.glory.mcore.exceptions.BizRuntimeException;

public class PrivacyInfoException extends BizRuntimeException {

    public PrivacyInfoException(ResponseCode code) {
        this(code, null);
    }

    public PrivacyInfoException(ResponseCode code, Throwable cause) {
        this(code, false, cause);
    }

    public PrivacyInfoException(ResponseCode code, boolean printStackTrace, Throwable cause) {
        super(code, code.getMessage(), printStackTrace, cause);
    }

}
