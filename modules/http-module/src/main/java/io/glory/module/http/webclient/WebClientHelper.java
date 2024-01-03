package io.glory.module.http.webclient;

import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

import io.glory.mcore.code.ExternalErrorCode;
import io.glory.mcore.exceptions.BizException;
import io.glory.mcore.exceptions.BizRuntimeException;
import io.glory.module.http.ExternalHttpException;
import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.TimeoutException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
public class WebClientHelper {

    private WebClientHelper() {
    }

    public static <T> T call(Callable<T> function) throws ExternalHttpException {

        try {
            T response = function.call();
            if (response == null) {
                throw new ExternalHttpException(ExternalErrorCode.NO_RESPONSE);
            }
            log.debug("==> External Http Response = {}", response);
            return response;

        } catch (ExternalHttpException e) {
            throw e;

        } catch (WebClientRequestException e) {
            handleWebClientRequestException(e);
            throw new ExternalHttpException(ExternalErrorCode.REQUEST_ERROR, e);

        } catch (WebClientResponseException e) {
            handleWebClientResponseException(e);
            throw new ExternalHttpException(ExternalErrorCode.RESPONSE_ERROR, e);

        } catch (Exception e) {
            if (e.getCause() instanceof BizException subEx) {
                throw new ExternalHttpException(subEx.getCode(), e);
            } else if (e.getCause() instanceof BizRuntimeException subEx) {
                throw new ExternalHttpException(subEx.getCode(), e);
            }

            log.error("==> e: {}, message: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new ExternalHttpException(ExternalErrorCode.ERROR, e);
        }
    }

    private static void handleWebClientRequestException(WebClientRequestException e) throws ExternalHttpException {
        Throwable cause = e.getCause();
        log.error("==> e: {}, cause: {}, message: {}",
                e.getClass().getSimpleName(), cause.getClass().getSimpleName(), e.getMessage());

        if (cause instanceof ReadTimeoutException ex) {
            throw new ExternalHttpException(ExternalErrorCode.READ_TIME_OUT, ex);
        } else if (cause instanceof ConnectTimeoutException ex) {
            throw new ExternalHttpException(ExternalErrorCode.CONNECT_TIME_OUT, ex);
        } else if (cause instanceof TimeoutException ex) {
            throw new ExternalHttpException(ExternalErrorCode.TIME_OUT, ex);
        }
    }

    private static void handleWebClientResponseException(WebClientResponseException e) throws ExternalHttpException {
        log.error("==> e: {}, message: {}, status: {}, body: {}",
                e.getClass().getSimpleName(), e.getMessage(), e.getStatusCode(), e.getResponseBodyAsString());
    }

}
