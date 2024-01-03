package io.glory.module.http.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * WebClient 를 이용하여 HttpClient 를 생성하는 클래스
 *
 * <ul>
 * <li><a href="https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-http-interface">Spring HTTP Interface</a></li>
 * <li><a href="https://www.baeldung.com/spring-6-http-interface">HTTP Interface in Spring 6 (Baeldung)</a></li>
 * </ul>
 */
public class HttpClientGenerator {

    private HttpClientGenerator() {
    }

    /**
     * WebClient 를 이용하여 HttpClient 를 생성 한다
     *
     * @param clazz     HttpClient Interface
     * @param webClient WebClient
     * @param <T>       HttpClient Interface
     * @return HttpClient
     */
    public static <T> T generate(Class<T> clazz, WebClient webClient) {
        return createClient(clazz, webClient);
    }

    /**
     * WebClient 를 이용하여 HttpClient 를 생성 한다
     * <p>
     * baseUrl 은 WebClient 의 baseUrl 을 override 한다
     *
     * @param clazz     HttpClient Interface
     * @param webClient WebClient
     * @param baseUrl   baseUrl
     * @param <T>       HttpClient Interface
     * @return HttpClient
     */
    public static <T> T mutateAndGenerate(Class<T> clazz, WebClient webClient, String baseUrl) {
        WebClient client = webClient.mutate()
                .baseUrl(baseUrl)
                .build();

        return createClient(clazz, client);
    }

    private static <T> T createClient(Class<T> clazz, WebClient client) {
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client))
                .build()
                .createClient(clazz);
    }

}
