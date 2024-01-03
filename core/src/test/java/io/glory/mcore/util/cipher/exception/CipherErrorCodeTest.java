package io.glory.mcore.util.cipher.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CipherErrorCodeTest {

    @DisplayName("CipherErrorCodeTest 는 HTTP 상태 코드가 406 이다")
    @Test
    void when_getStatusCode_expect_NotAcceptable() throws Exception {
        // given
        int expectedHttpStatus = 406;

        // when
        Arrays.stream(CipherErrorCode.values()).forEach(orderErrorCode ->
                // then
                assertThat(orderErrorCode.getStatusCode()).isEqualTo(expectedHttpStatus)
        );
    }

}