package io.glory;

import io.glory.mcore.util.idgenerator.TraceIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@ConfigurationPropertiesScan
class TestConfiguration {

    @Primary
    @Bean
    public TraceIdGenerator traceIdGenerator() {
        return new TraceIdGenerator(1, 1);
    }

    @Test
    void contextLoads() {
    }

}
