package io.glory.pips.app.service.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class PrivacyInfoErrorCodeTest {

    @DisplayName("PrivacyInfoErrorCode 의 getStatusCode 는 406 을 반환 한다")
    @Test
    void when_getStatusCode_expect_NotAcceptable() throws Exception {
        // given
        HttpStatus expectedStatusCode = HttpStatus.NOT_ACCEPTABLE;

        // when
        for (PrivacyInfoErrorCode code : PrivacyInfoErrorCode.values()) {
            // then
            assertThat(code.getStatusCode()).isEqualTo(expectedStatusCode.value());
        }
    }

}