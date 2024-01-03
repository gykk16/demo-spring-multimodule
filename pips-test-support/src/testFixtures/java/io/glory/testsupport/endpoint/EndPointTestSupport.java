package io.glory.testsupport.endpoint;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

public abstract class EndPointTestSupport {

    protected final RestClient client;
    private final   String     baseUrl;

    protected EndPointTestSupport(String baseUrl) {
        this(baseUrl, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }

    protected EndPointTestSupport(String baseUrl, String contentType) {
        this.baseUrl = baseUrl;
        this.client = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, contentType)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    protected ResponseEntity<String> callGet(String uri) {
        return client.get()
                .uri(uri)
                .retrieve()
                .toEntity(String.class);
    }

    protected ResponseEntity<String> callPost(String uri, MultiValueMap<String, String> paramMap) {
        return client.post()
                .uri(uri)
                .body(paramMap)
                .retrieve()
                .toEntity(String.class);
    }

}
