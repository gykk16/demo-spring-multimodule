package io.glory.pips.domain.config;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    /**
     * JPA AuditorAware 설정
     */
    @Bean
    @ConditionalOnMissingBean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("unknown");
    }

}
