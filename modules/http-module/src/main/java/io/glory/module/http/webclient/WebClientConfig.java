package io.glory.module.http.webclient;

import static io.glory.mcore.MdcConst.REQUEST_TRACE_KEY;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Configuration
@Slf4j
public class WebClientConfig {

    private static final String USER_AGENT = "Sc/http-client-w";

    ExchangeFilterFunction mdcFilter = (clientRequest, nextFilter) -> {
        String requestTraceKey = Optional.ofNullable(MDC.get(REQUEST_TRACE_KEY)).orElse("");
        return nextFilter.exchange(clientRequest).contextWrite(ctx -> ctx.put(REQUEST_TRACE_KEY, requestTraceKey));
    };

    @Bean
    @ConditionalOnMissingBean
    @SuppressWarnings("deprecation")
    public WebClient webClient(HttpClient defaultHttpClient) {
        return WebClient.builder()
                .filter(mdcFilter)
                .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .clientConnector(new ReactorClientHttpConnector(defaultHttpClient))
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpClient defaultHttpClient(ConnectionProvider provider) {
        return HttpClient.create(provider)
                .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL, UTF_8)
                // .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.SIMPLE, UTF_8)
                .responseTimeout(Duration.ofSeconds(5))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1_000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(EpollChannelOption.TCP_KEEPIDLE, 100)
                .option(EpollChannelOption.TCP_KEEPINTVL, 20)
                .option(EpollChannelOption.TCP_KEEPCNT, 3)
                .doOnConnected(conn -> conn
                        .addHandlerFirst(new ReadTimeoutHandler(3, TimeUnit.SECONDS))
                        .addHandlerFirst(new WriteTimeoutHandler(3, TimeUnit.SECONDS))
                );
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionProvider connectionProvider() {
        return ConnectionProvider.builder("http-pool")
                .maxConnections(50) // 최대 connection pool 의 갯수
                .pendingAcquireMaxCount(-1) // 커넥션 풀에서 커넥션을 가져오기 위한 대기 queue 크기 (-1: no limit)
                .pendingAcquireTimeout(Duration.ofMillis(0)) // 커넥션 풀에서 커넥션을 얻기 위해 기다리는 최대 시간
                .maxIdleTime(Duration.ofSeconds(60)) // 커넥션 풀에서 idle 상태의 커넥션을 유지하는 시간
                .build();
    }

}
