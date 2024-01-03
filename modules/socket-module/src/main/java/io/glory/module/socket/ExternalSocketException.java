package io.glory.module.socket;

import lombok.Getter;

import io.glory.mcore.code.ResponseCode;
import io.glory.mcore.exceptions.BizException;

@Getter
public class ExternalSocketException extends BizException {

    public ExternalSocketException(ResponseCode code) {
        this(code, null);
    }

    public ExternalSocketException(ResponseCode code, Throwable cause) {
        super(code, cause);
    }

}