package io.glory.pips.app.api.model.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

/**
 * 개인 신상 정보 V1 응답 Spec
 * <pre>
 * {
 *   "name": "홍길동",
 *   "mobileNo": "01012345678",
 *   "phoneNo": "0212345678",
 *   "birthDate": "2000-01-01"
 * }
 * </pre>
 *
 * @param name      이름
 * @param mobileNo  휴대폰 번호
 * @param phoneNo   전화번호
 * @param birthDate 생년월일
 */
public record PersonalDataV1Response(
        @JsonProperty("name") String name,
        @JsonProperty("mobileNo") String mobileNo,
        @JsonProperty("phoneNo") String phoneNo,
        @JsonProperty("birthDate")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate birthDate
) {

}
