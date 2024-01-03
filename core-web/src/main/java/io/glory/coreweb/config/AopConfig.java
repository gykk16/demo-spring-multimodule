package io.glory.coreweb.config;

import io.glory.coreweb.aop.logtrace.LogTrace;
import io.glory.coreweb.aop.logtrace.LogTraceAspect;
import io.glory.coreweb.aop.logtrace.ThreadLocalLogTrace;
import io.glory.coreweb.aop.security.SecuredIpAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public SecuredIpAspect securedIpAspect() {
        return new SecuredIpAspect();
    }

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

}
