package io.glory.module.http.webclient;

import static io.glory.mcore.code.ExternalErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.concurrent.Callable;

import io.glory.module.http.ExternalHttpException;
import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

class WebClientHelperTest {

    @DisplayName("WebClientHelper call 성공")
    @Test
    void when_call_expect_Success() throws Exception {
        // given
        Callable<String> callable = mock(Callable.class);
        when(callable.call()).thenReturn("Success");

        // when
        String response = WebClientHelper.call(callable);

        // then
        assertThat(response).isEqualTo("Success");
    }

    @DisplayName("WebClientHelper call 응답 결과가 null 이면 ExternalIssueException 예외를 던진다")
    @Test
    void when_callReturnNull_expect_ExternalClientException() throws Exception {
        // given
        Callable<String> callable = mock(Callable.class);
        when(callable.call()).thenReturn(null);

        // when
        assertThatThrownBy(() -> WebClientHelper.call(callable))
                // then
                .isInstanceOf(ExternalHttpException.class)
                .hasMessageContaining(NO_RESPONSE.getMessage());
    }

    @DisplayName("WebClientHelper call 중 read timeout 발생시 ExternalIssueException 예외를 던진다")
    @Test
    void when_ReadTimeout_expect_ExternalClientException() throws Exception {
        // given
        Callable<String> callable = mock(Callable.class);
        ReadTimeoutException readTimeoutException = new ReadTimeoutException();
        WebClientRequestException requestException = new WebClientRequestException(
                readTimeoutException, HttpMethod.GET, new URI("http://localhost"), new HttpHeaders()
        );
        when(callable.call()).thenThrow(requestException);

        // when
        assertThatThrownBy(() -> WebClientHelper.call(callable))
                // then
                .isInstanceOf(ExternalHttpException.class)
                .hasMessageContaining(READ_TIME_OUT.getMessage());
    }

    @DisplayName("WebClientHelper call 중 connect timeout 발생시 ExternalIssueException 예외를 던진다")
    @Test
    void when_ConnectTimeout_expect_ExternalClientException() throws Exception {
        // given
        Callable<String> callable = mock(Callable.class);
        ConnectTimeoutException connectTimeoutException = new ConnectTimeoutException();
        WebClientRequestException requestException = new WebClientRequestException(
                connectTimeoutException, HttpMethod.GET, new URI("http://localhost"), new HttpHeaders()
        );
        when(callable.call()).thenThrow(requestException);

        // when
        assertThatThrownBy(() -> WebClientHelper.call(callable))
                // then
                .isInstanceOf(ExternalHttpException.class)
                .hasMessageContaining(CONNECT_TIME_OUT.getMessage());
    }

    @DisplayName("WebClientHelper call 중 WebClientRequestException 예외 발생시 ExternalIssueException 예외를 던진다")
    @Test
    void when_WebClientRequestException_expect_ExternalClientException() throws Exception {
        // given
        Callable<String> callable = mock(Callable.class);
        WebClientRequestException requestException = new WebClientRequestException(
                new Exception(), HttpMethod.GET, new URI("http://localhost"), new HttpHeaders()
        );
        when(callable.call()).thenThrow(requestException);

        // when
        assertThatThrownBy(() -> WebClientHelper.call(callable))
                // then
                .isInstanceOf(ExternalHttpException.class)
                .hasMessageContaining(REQUEST_ERROR.getMessage());
    }

    @DisplayName("WebClientHelper call 중 WebClientResponseException 예외 발생시 ExternalIssueException 예외를 던진다")
    @Test
    void when_WebClientResponseException_expect_ExternalClientException() throws Exception {
        // given
        Callable<String> callable = mock(Callable.class);
        WebClientResponseException requestException = new WebClientResponseException(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", new HttpHeaders(), new byte[0], null
        );

        when(callable.call()).thenThrow(requestException);

        // when
        assertThatThrownBy(() -> WebClientHelper.call(callable))
                // then
                .isInstanceOf(ExternalHttpException.class)
                .hasMessageContaining(RESPONSE_ERROR.getMessage());
    }

    @DisplayName("WebClientHelper call 중 예외 발생시 ExternalIssueException 예외를 던진다")
    @Test
    void when_Exception_expect_ExternalClientException() throws Exception {
        // given
        Callable<String> callable = mock(Callable.class);
        when(callable.call()).thenThrow(new Exception());

        // when
        assertThatThrownBy(() -> WebClientHelper.call(callable))
                // then
                .isInstanceOf(ExternalHttpException.class)
                .hasMessageContaining(ERROR.getMessage());
    }

}