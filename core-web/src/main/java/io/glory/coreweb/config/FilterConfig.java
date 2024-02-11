package io.glory.coreweb.config;

import io.glory.coreweb.filter.ContentCachingFilter;
import io.glory.coreweb.filter.trace.TraceIdGeneratorTraceFilter;
import io.glory.coreweb.filter.trace.TraceKeyFilter;
import io.glory.coreweb.properties.WebAppProperties;
import io.glory.mcore.util.idgenerator.TraceIdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    @ConditionalOnMissingBean
    public ContentCachingFilter contentCatchingFilter() {
        return new ContentCachingFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public TraceKeyFilter traceKeyFilter(TraceIdGenerator traceIdGenerator) {
        return new TraceIdGeneratorTraceFilter(traceIdGenerator);
    }

    @Bean
    @ConditionalOnMissingBean
    public TraceIdGenerator traceIdGenerator(WebAppProperties properties) {
        return new TraceIdGenerator(properties.workerId(), properties.processId());
    }

}
