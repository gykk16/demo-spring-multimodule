package io.glory.coreweb.response;

import static org.assertj.core.api.Assertions.assertThat;

import io.glory.mcore.code.SuccessCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ApiResponseEntityTest {

    @DisplayName("ApiResponseEntity.ofSuccess")
    @Test
    void ofSuccess() throws Exception {
        // given

        // when
        ResponseEntity<ApiResponseEntity<Object>> ofSuccess = ApiResponseEntity.ofSuccess();

        // then
        assertThat(ofSuccess).isNotNull();
        assertThat(ofSuccess.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(ofSuccess.getBody()).isNotNull();
        assertThat(ofSuccess.getBody().getResponseDt()).isNotNull();
        assertThat(ofSuccess.getBody())
                .extracting("success", "code", "message")
                .containsExactly(true, SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage());
    }

}