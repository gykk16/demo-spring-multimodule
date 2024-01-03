package io.glory.mcore.code;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SuccessCodeTest {

    @DisplayName("성공 코드는 S00000 이다")
    @Test
    void when_SUCCESS_expect_S00000() throws Exception {
        assertThat(SuccessCode.SUCCESS.getCode()).isEqualTo("S00000");
        assertThat(SuccessCode.SUCCESS.getStatusCode()).isEqualTo(200);
        assertThat(SuccessCode.SUCCESS.isSuccess()).isTrue();
    }

    @DisplayName("생성 완료 코드는 S00001 이다")
    @Test
    void when_CREATED_expect_S00001() throws Exception {
        assertThat(SuccessCode.CREATED.getCode()).isEqualTo("S00001");
        assertThat(SuccessCode.CREATED.getStatusCode()).isEqualTo(201);
        assertThat(SuccessCode.CREATED.isSuccess()).isTrue();
    }

    @DisplayName("접수 완료 코드는 S00002 이다")
    @Test
    void when_ACCEPTED_expect_S00002() throws Exception {
        assertThat(SuccessCode.ACCEPTED.getCode()).isEqualTo("S00002");
        assertThat(SuccessCode.ACCEPTED.getStatusCode()).isEqualTo(202);
        assertThat(SuccessCode.ACCEPTED.isSuccess()).isTrue();
    }

}