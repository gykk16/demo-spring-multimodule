package io.glory.module.http.openfeign;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

import feign.FeignException;
import feign.RetryableException;
import kr.co.smilecon.mcore.code.ExternalErrorCode;
import kr.co.smilecon.mcore.exception.BizException;
import kr.co.smilecon.mcore.exception.BizRuntimeException;
import kr.co.smilecon.module.http.ExternalHttpException;

@Slf4j
public class OpenFeignHelper {

    private OpenFeignHelper() {
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

        if (cause instanceof SocketTimeoutException ex && ex.getMessage().contains("Read timed out")) {
            throw new ExternalHttpException(ExternalErrorCode.READ_TIME_OUT, ex);
        } else if (cause instanceof SocketException ex && ex.getMessage().contains("connect")) {
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
