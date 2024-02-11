package io.glory.coreweb.config;

import io.glory.coreweb.interceptor.DocsSecureInterceptor;
import io.glory.coreweb.interceptor.LogInterceptor;
import io.glory.coreweb.interceptor.LogResponseBodyInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterceptorConfig {

    @Bean
    @ConditionalOnMissingBean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public LogResponseBodyInterceptor logResponseBodyInterceptor() {
        return new LogResponseBodyInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public DocsSecureInterceptor docsSecureInterceptor() {
        return new DocsSecureInterceptor();
    }

}
