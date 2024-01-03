package io.glory.mcore.util.cipher.exception;

import io.glory.mcore.code.ResponseCode;
import io.glory.mcore.exceptions.BizRuntimeException;

public class CipherException extends BizRuntimeException {

    public CipherException(ResponseCode code, Throwable cause) {
        super(code, cause);
    }

}
