package io.glory.pips.app.system.aop;

import static io.glory.coreweb.HttpServletUtil.getRequestBody;
import static io.glory.coreweb.HttpServletUtil.getRequestParams;
import static io.glory.coreweb.WebAppConst.getMdcStartTime;
import static io.glory.coreweb.WebAppConst.getMdcTraceKey;
import static io.glory.mcore.util.string.StringUtil.substringN;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import io.glory.coreweb.HttpServletUtil;
import io.glory.coreweb.IpAddrUtil;
import io.glory.coreweb.aop.apidblog.ApiDbLog;
import io.glory.coreweb.aop.apidblog.ApiDbLogAspect;
import io.glory.pips.domain.entity.ApiReqResLog;
import io.glory.pips.domain.repository.ApiReqResLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class ApiDbLogAspectV1 extends ApiDbLogAspect {

    private final ApiReqResLogRepository apiReqResLogRepository;

    @Around("@annotation(apiDbLog)")
    public Object insertApiReqResLog(ProceedingJoinPoint joinPoint, ApiDbLog apiDbLog) throws Throwable {

        try {
            Object proceed = joinPoint.proceed();
            saveApiReqResLog(joinPoint, apiDbLog, proceed);

            return proceed;

        } catch (Exception e) {
            saveExceptionApiReqResLog(joinPoint, apiDbLog, e);
            throw e;
        }
    }

    @Override
    protected void buildAndSaveApiReqResLog(ProceedingJoinPoint joinPoint, ApiDbLog apiDbLog, boolean success,
            int httpStatus, String resCode, String resMsg, String exception, String rootCause) throws IOException {

        HttpServletRequest request = HttpServletUtil.getRequest();
        String method = request.getMethod();
        String path = request.getRequestURI();

        long requestId = Long.parseLong(getMdcTraceKey().orElseThrow());
        String serverIP = IpAddrUtil.getServerIP();
        String clientIP = IpAddrUtil.getClientIP();

        long requestEndTime = System.currentTimeMillis();
        long elapsedMs = requestEndTime - getMdcStartTime();

        ApiReqResLog apiReqResLog = ApiReqResLog.builder()
                .requestId(requestId)
                .serverIp(serverIP)
                .clientIp(clientIP)
                // request
                .method(method)
                .path(path)
                .reqHeaders(null)
                .reqParams(substringN(getRequestParams(), 1_000))
                .reqBody(substringN(getRequestBody(), 2_000))
                .reqKeyValue(null)
                // response
                .success(success)
                .httpStatus(httpStatus)
                .resCode(resCode)
                .resMsg(resMsg)
                // exception
                .exception(exception)
                .rootCause(rootCause)
                // etc
                .elapsedMs(elapsedMs)
                .build();

        apiReqResLogRepository.save(apiReqResLog);
    }

}
