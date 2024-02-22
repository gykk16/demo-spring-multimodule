package io.glory.module.http.openfeign;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import io.glory.mcore.code.ExternalErrorCode;
import io.glory.mcore.exceptions.BizException;
import io.glory.mcore.exceptions.BizRuntimeException;
import io.glory.module.http.ExternalHttpException;

@Slf4j
public class OpenFeignHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private OpenFeignHelper() {
    }

    public static <T> T call(Callable<T> function) throws BizException {

        try {
            T response = function.call();
            if (response == null) {
                throw new ExternalHttpException(ExternalErrorCode.NO_RESPONSE);
            }
            log.debug("==> External Http Response = {}", response);
            return response;

        } catch (BizException | BizRuntimeException e) {
            throw e;

        } catch (RetryableException e) {
            handleRetryableException(e);
            throw new ExternalHttpException(ExternalErrorCode.REQUEST_ERROR, e);

        } catch (FeignException.FeignClientException e) {
            handleFeignClientException(e);
            throw new ExternalHttpException(ExternalErrorCode.RESPONSE_ERROR, e, e.contentUTF8());

        } catch (FeignException.FeignServerException e) {
            handleFeignServerException(e);
            throw new ExternalHttpException(ExternalErrorCode.RESPONSE_ERROR, e, e.contentUTF8());

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

    public static <T> T toObject(Response response, Class<T> type) throws IOException, ExternalHttpException {
        if (response.body() == null) {
            throw new ExternalHttpException(ExternalErrorCode.NO_RESPONSE);
        }
        try (InputStream inputStream = response.body().asInputStream()) {
            return objectMapper.readValue(inputStream, type);
        }
    }

    private static void handleFeignServerException(FeignException.FeignServerException e) throws ExternalHttpException {
        log.error("==> e: {}, message: {}",
                e.getClass().getSimpleName(), e.getMessage());
    }

    private static void handleFeignClientException(FeignException.FeignClientException e) throws ExternalHttpException {
        log.error("==> e: {}, message: {}",
                e.getClass().getSimpleName(), e.getMessage());
    }

    private static void handleRetryableException(RetryableException e) throws ExternalHttpException {
        Throwable cause = getCause(e);

        if (cause instanceof SocketTimeoutException ex && ex.getMessage().toLowerCase().contains("read")) {
            throw new ExternalHttpException(ExternalErrorCode.READ_TIME_OUT, ex);
        } else if (cause instanceof SocketException ex && ex.getMessage().toLowerCase().contains("connect")) {
            throw new ExternalHttpException(ExternalErrorCode.CONNECT_TIME_OUT, ex);
        } else {
            throw new ExternalHttpException(ExternalErrorCode.TIME_OUT, e);
        }
    }

    private static Throwable getCause(Exception e) {
        Throwable cause = e.getCause();
        if (cause == null) {
            return e;
        }

        log.error("==> e: {}, cause: {}, message: {}",
                e.getClass().getSimpleName(), cause.getClass().getSimpleName(), e.getMessage());
        return cause;
    }

}
