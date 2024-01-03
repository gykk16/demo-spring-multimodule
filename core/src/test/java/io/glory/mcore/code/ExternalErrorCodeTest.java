package io.glory.mcore.code;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExternalErrorCodeTest {

    @DisplayName("ExternalErrorCode 의 getStatusCode 는 406 을 반환 한다")
    @Test
    void when_getStatusCode_expect_NotAcceptable() throws Exception {
        // given
        int expectedStatusCode = 406;

        // when
        for (ExternalErrorCode code : ExternalErrorCode.values()) {
            // then
            assertThat(code.getStatusCode()).isEqualTo(expectedStatusCode);
        }
    }

}