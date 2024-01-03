package io.glory.module.socket.client;

import static io.glory.mcore.code.ExternalErrorCode.*;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

import io.glory.mcore.exceptions.BizException;
import io.glory.mcore.exceptions.BizRuntimeException;
import io.glory.module.socket.ExternalSocketException;

@Slf4j
public class SocketClientHelper {

    private SocketClientHelper() {
    }

    public static String call(Callable<String> function) throws ExternalSocketException {

        try {
            String response = function.call();
            if (response == null) {
                throw new ExternalSocketException(NO_RESPONSE);
            }
            log.debug("==> External Socket Response = {}", response);
            return response;

        } catch (ExternalSocketException e) {
            throw e;

        } catch (SocketException e) {
            throw new ExternalSocketException(REQUEST_ERROR, e);

        } catch (SocketTimeoutException e) {
            if (e.getMessage().contains("Read timed out")) {
                throw new ExternalSocketException(READ_TIME_OUT, e);
            }
            throw new ExternalSocketException(TIME_OUT, e);

        } catch (Exception e) {
            if (e.getCause() instanceof BizException subEx) {
                throw new ExternalSocketException(subEx.getCode(), e);
            } else if (e.getCause() instanceof BizRuntimeException subEx) {
                throw new ExternalSocketException(subEx.getCode(), e);
            }

            log.error("==> e: {}, message: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new ExternalSocketException(ERROR, e);
        }
    }

}
