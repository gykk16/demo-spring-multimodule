package io.glory.module.http.openfeign;

import org.springframework.context.annotation.Configuration;

/**
 * openFeign 기본 설정은 application.yml 에서 적용 함.
 * <p>
 * 기본 적용 헤더 :
 * <li>User-Agent: http-client-of</li>
 * <li>Content-Type: application/json</li>
 * <li>Accept: application/json</li>
 *
 * </p>
 */
@Configuration
public class OpenFeignConfig {

}
