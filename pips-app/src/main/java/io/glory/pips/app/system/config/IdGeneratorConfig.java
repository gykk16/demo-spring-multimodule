package io.glory.pips.app.system.config;

import io.glory.coreweb.properties.WebAppProperties;
import io.glory.mcore.util.idgenerator.TraceIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Bean
    public TraceIdGenerator traceIdGenerator(WebAppProperties properties) {
        return new TraceIdGenerator(properties.workerId(), properties.processId());
    }

}
