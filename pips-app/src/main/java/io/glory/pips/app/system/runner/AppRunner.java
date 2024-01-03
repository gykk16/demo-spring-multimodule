package io.glory.pips.app.system.runner;

import static io.glory.mcore.util.log.LogLine.LOG_LINE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.zaxxer.hikari.HikariDataSource;
import io.glory.coreweb.filter.trace.TraceKeyFilter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppRunner implements ApplicationRunner {

    private final HikariDataSource dataSource;
    private final TraceKeyFilter   traceKeyFilter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logJdbcInfo();
        logTraceKeyFilterInfo();
    }

    private void logJdbcInfo() {
        log.info(LOG_LINE);
        log.info("# ==> DataSource JdbcUrl = {}", dataSource.getJdbcUrl());
        log.info("# ==> DataSource Username = {}", dataSource.getUsername());
        log.info("# ==> DataSource MinimumIdle = {}", dataSource.getMinimumIdle());
        log.info("# ==> DataSource MaximumPoolSize = {}", dataSource.getMaximumPoolSize());
        log.info("# ==> DataSource MaxLifetime = {}", dataSource.getMaxLifetime());
        log.info("# ==> DataSource ConnectionTimeout = {}", dataSource.getConnectionTimeout());
    }

    private void logTraceKeyFilterInfo() {
        log.info(LOG_LINE);
        log.info("# ==> traceKeyFilter = {}", traceKeyFilter.getClass().getName());
    }

}
