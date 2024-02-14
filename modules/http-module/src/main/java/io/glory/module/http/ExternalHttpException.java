package io.glory.module.http;

import lombok.Getter;

import io.glory.mcore.code.ResponseCode;
import io.glory.mcore.exceptions.BizException;

@Getter
public class ExternalHttpException extends BizException {

    private String body;

    public ExternalHttpException(ResponseCode code) {
        this(code, null);
    }

    public ExternalHttpException(ResponseCode code, Throwable cause) {
        super(code, cause);
    }

    public ExternalHttpException(ResponseCode code, Throwable cause, String body) {
        super(code, cause);
        this.body = body;
    }

}