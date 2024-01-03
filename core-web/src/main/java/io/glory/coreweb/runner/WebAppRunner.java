package io.glory.coreweb.runner;

import java.util.Arrays;

import io.glory.coreweb.IpAddrUtil;
import io.glory.coreweb.properties.WebAppProperties;
import io.glory.mcore.util.log.LogLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

@Order(Ordered.LOWEST_PRECEDENCE)
@Configuration
public class WebAppRunner {

    private static final Logger log = LoggerFactory.getLogger(WebAppRunner.class);

    @Value("${info.app.version:}")
    private String appVersion;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Value("${server.port:}")
    private String port;

    @Bean
    CommandLineRunner run(WebAppProperties webAppProperties, Environment env) {
        return args -> {
            log.info(LogLine.LOG_LINE);
            log.warn("# ==> 모든 웹 어플리케이션은 WorkerId 와 ProcessId 를 할당 받아야 합니다.");
            log.info("# ==> webAppProperties = {}", webAppProperties);
            log.info("# ==> Active Profiles = {} , {}", activeProfile, Arrays.stream(env.getActiveProfiles()).toList());
            log.info("# ==> App Version = {}", appVersion);
            log.info("# ==> ServerIp = {} , Port = {}", IpAddrUtil.getServerIP(), port);
            log.info(LogLine.LOG_LINE);
        };
    }

}
