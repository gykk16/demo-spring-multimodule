package io.glory.pips.app.system.config;


import static io.glory.coreweb.WebAppConst.INTERCEPTOR_EXCLUDE_PATH;
import static io.glory.coreweb.WebAppConst.getMdcAccessId;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import io.glory.coreweb.IpAddrUtil;
import io.glory.coreweb.filter.trace.TraceIdGeneratorTraceFilter;
import io.glory.coreweb.filter.trace.TraceKeyFilter;
import io.glory.coreweb.interceptor.DocsSecureInterceptor;
import io.glory.coreweb.interceptor.LogInterceptor;
import io.glory.coreweb.interceptor.LogResponseBodyInterceptor;
import io.glory.mcore.util.idgenerator.TraceIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${info.app.alias:}")
    private String appAlias;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(docsSecureInterceptor())
                .order(1)
                .addPathPatterns("/docs/**");

        registry.addInterceptor(logInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(INTERCEPTOR_EXCLUDE_PATH);

        registry.addInterceptor(logResponseBodyInterceptor())
                .order(3)
                .addPathPatterns("/**")
                .excludePathPatterns(INTERCEPTOR_EXCLUDE_PATH);
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Bean
    public LogResponseBodyInterceptor logResponseBodyInterceptor() {
        return new LogResponseBodyInterceptor();
    }

    @Bean
    public DocsSecureInterceptor docsSecureInterceptor() {
        return new DocsSecureInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public TraceKeyFilter traceKeyFilter(TraceIdGenerator traceIdGenerator) {
        return new TraceIdGeneratorTraceFilter(traceIdGenerator);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(getMdcAccessId().orElse(appAlias) + "-" + IpAddrUtil.getServerIpLastOctet());
    }

}
