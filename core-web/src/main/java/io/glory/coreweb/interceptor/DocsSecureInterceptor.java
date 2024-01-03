package io.glory.coreweb.interceptor;

import static io.glory.coreweb.WebAppConst.INTERNAL_IPS;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.glory.coreweb.IpAddrUtil;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * static docs 보안 인터셉터
 * <p>
 * 지정된 IP 외의 IP 로 접근 시 요청을 거부 한다
 */
public class DocsSecureInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String clientIP = IpAddrUtil.getClientIP();
        if (INTERNAL_IPS.contains(clientIP)) {
            return true;
        }

        throw new NoResourceFoundException(HttpMethod.valueOf(request.getMethod()), request.getRequestURI());
    }

}
