package io.glory.coreweb.filter.trace;

import static io.glory.coreweb.WebAppConst.FILTER_EXCLUDE_PATH;
import static io.glory.coreweb.WebAppConst.REQUEST_ID_HEADER;
import static io.glory.coreweb.WebAppConst.getMdcStartTime;
import static io.glory.mcore.MdcConst.REQUEST_START_TIME;
import static io.glory.mcore.MdcConst.REQUEST_TRACE_KEY;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.glory.coreweb.IpAddrUtil;
import io.glory.mcore.util.log.LogLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "TraceKeyFilter", urlPatterns = "/**")
public abstract class TraceKeyFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TraceKeyFilter.class);

    private static final long WARN_PROCESS_TIME_MS = 2_000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            MDC.put(REQUEST_START_TIME, String.valueOf(System.currentTimeMillis()));
            String requestKey = generateTraceKey();
            MDC.put(REQUEST_TRACE_KEY, requestKey);

            log.info(
                    "# REQ START #######################################################################################################");

            logDefaultParameters(request);
            response.setHeader(REQUEST_ID_HEADER, requestKey);

            // ==============================
            // doFilter
            // ==============================
            filterChain.doFilter(request, response);

            long requestEndTime = System.currentTimeMillis();
            long requestProcessTime = requestEndTime - getMdcStartTime();
            log.info("# Request process time = {}ms", requestProcessTime);

            if (requestProcessTime >= WARN_PROCESS_TIME_MS) {
                log.warn("# Request process time over {}ms", WARN_PROCESS_TIME_MS);
            }

        } finally {
            log.info(
                    "# REQ END   #######################################################################################################");
            MDC.clear();
        }
    }

    protected abstract String generateTraceKey();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return Arrays.stream(FILTER_EXCLUDE_PATH)
                .anyMatch(exclusion -> path.startsWith(exclusion) || path.endsWith(exclusion));
    }

    /**
     * 요청 기본 정보 로그 출력
     *
     * @param request request
     */
    private void logDefaultParameters(HttpServletRequest request) {

        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String contentType = request.getContentType();
        String referer = request.getHeader(HttpHeaders.REFERER);
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String clientIP = IpAddrUtil.getClientIP(request);

        log.info("# RequestURI = {} , {}", method, requestURI);
        if (StringUtils.hasText(referer)) {
            log.info("# Referer = {}", referer);
        }
        log.info("# Content-Type = {} , Accept = {} , User-Agent = {}", contentType, accept, userAgent);
        if (StringUtils.hasText(authorization)) {
            log.info("# Authorization = {}", authorization);
        }
        log.info("# ServerIp = {} , ClientIp = {}", IpAddrUtil.getServerIP(), clientIP);
        log.info(LogLine.LOG_LINE);
    }

}
