package io.glory.mcore.code;

import java.io.Serializable;

public interface ResponseCode extends Serializable {

    int getStatusCode();

    boolean isSuccess();

    String getCode();

    String getMessage();

    default String getCodeAndMessage() {
        return "[" + getCode() + "] " + getMessage();
    }

}
