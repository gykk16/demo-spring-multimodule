package io.glory.coreweb.response;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 표준 Collection 응답 객체
 *
 * @param totalCount 전체 데이터 수
 * @param content    데이터 목록
 * @param <T>        데이터 타입
 */
public record CollectionResObj<T>(
        @JsonProperty("totalCount")
        int totalCount,
        @JsonProperty("content")
        Collection<T> content
) {

    /**
     * 표준 Collection 응답 객체 생성
     *
     * @param content 데이터 목록
     */
    public CollectionResObj(Collection<T> content) {
        this(content.size(), content);
    }

}
