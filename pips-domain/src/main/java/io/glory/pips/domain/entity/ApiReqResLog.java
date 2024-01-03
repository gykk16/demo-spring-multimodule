package io.glory.pips.domain.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.glory.pips.domain.entity.base.BaseEntity;
import org.hibernate.annotations.Comment;
import org.hibernate.type.YesNoConverter;
import org.springframework.data.domain.Persistable;

/**
 * API 요청 응답 로그
 * <p>
 * api_req_res_log
 * </p>
 * <ul>
 *     <li>API 요청/응답 로그를 저장하는 테이블로 로그용 으로만 활용 할 수 있다. </li>
 * </ul>
 */
@Entity
@Table(name = "api_req_res_log")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class ApiReqResLog extends BaseEntity implements Persistable<Long> {

    @Id
    @Column(name = "request_id")
    @Comment("요청 ID")
    private Long requestId;

    @Column(name = "server_ip")
    @Comment("서버 IP")
    private String serverIp;

    @Column(name = "client_ip")
    @Comment("클라이언트 IP")
    private String clientIp;

    @Column(name = "method")
    @Comment("HTTP Method")
    private String method;

    @Column(name = "req_path")
    @Comment("요청 URL")
    private String path;

    @Column(name = "req_headers")
    @Comment("요청 헤더")
    private String reqHeaders;

    @Column(name = "req_params")
    @Comment("요청 파라미터")
    private String reqParams;

    @Column(name = "req_body")
    @Comment("요청 바디")
    private String reqBody;

    @Column(name = "req_key_value")
    @Comment("요청 key 값")
    private String reqKeyValue;

    @Column(name = "success")
    @Convert(converter = YesNoConverter.class)
    @Comment("성공 여부")
    private Boolean success;

    @Column(name = "http_status")
    @Comment("HTTP 상태 코드")
    private Integer httpStatus;

    @Column(name = "res_code")
    @Comment("응답 코드")
    private String resCode;

    @Column(name = "res_msg")
    @Comment("응답 메시지")
    private String resMsg;

    @Column(name = "exception_name")
    @Comment("예외")
    private String exception;

    @Column(name = "root_cause")
    @Comment("예외 root cause")
    private String rootCause;

    @Column(name = "elapsed_time_ms")
    @Comment("소요 시간 (ms)")
    private Long elapsedMs;

    @Builder
    protected ApiReqResLog(Long requestId, String serverIp, String clientIp, String method, String path,
            String reqHeaders, String reqParams, String reqBody, String reqKeyValue, Boolean success,
            Integer httpStatus, String resCode, String resMsg, String exception, String rootCause, Long elapsedMs) {
        this.requestId = requestId;
        this.serverIp = serverIp;
        this.clientIp = clientIp;
        this.method = method;
        this.path = path;
        this.reqHeaders = reqHeaders;
        this.reqParams = reqParams;
        this.reqBody = reqBody;
        this.reqKeyValue = reqKeyValue;
        this.success = success;
        this.httpStatus = httpStatus;
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.exception = exception;
        this.rootCause = rootCause;
        this.elapsedMs = elapsedMs;
    }

    @Override
    public Long getId() {
        return this.requestId;
    }

    @Override
    public boolean isNew() {
        return regDt == null;
    }

}
