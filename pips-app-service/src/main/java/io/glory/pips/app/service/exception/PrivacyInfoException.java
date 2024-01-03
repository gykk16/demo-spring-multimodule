package io.glory.pips.app.service.exception;

import io.glory.mcore.code.ResponseCode;
import io.glory.mcore.exceptions.BizRuntimeException;

public class PrivacyInfoException extends BizRuntimeException {

    public PrivacyInfoException(ResponseCode code) {
        this(code, null);
    }

    public PrivacyInfoException(ResponseCode code, Throwable cause) {
        super(code, cause);
    }

}
