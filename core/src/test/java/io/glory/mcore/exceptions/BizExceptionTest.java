package io.glory.mcore.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import io.glory.mcore.code.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BizExceptionTest {

    @DisplayName("BizRException 은 Exception 을 상속 받아야 한다")
    @Test
    void isInstanceOfRuntimeException() throws Exception {
        // given
        BizException bre = new BizException(ErrorCode.ERROR);

        // when

        // then
        assertThat(bre).isInstanceOf(Exception.class);
    }

}