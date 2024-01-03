package io.glory.pips.app.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.glory.pips.data.constants.bank.BankCode;
import io.glory.pips.domain.query.PrivacyInfoAllDto;

/**
 * 개인정보 조회 V1 응답 Spec
 *
 * @param id        개인정보 id
 * @param dataUuid  개인정보 고유 uuid
 * @param name      이름
 * @param mobileNo  휴대폰 번호
 * @param phoneNo   전화번호
 * @param birthDate 생년월일
 * @param bankCode  은행코드
 * @param accountNo 계좌번호
 * @param holder    예금주
 * @param regDt     등록일시
 */
// @JsonInclude(JsonInclude.Include.NON_NULL)
public record PrivacyInfoV1Response(
        @JsonProperty("id") Long id,
        @JsonProperty("dataUuid") UUID dataUuid,
        @JsonProperty("name") String name,
        @JsonProperty("mobileNo") String mobileNo,
        @JsonProperty("phoneNo") String phoneNo,
        @JsonProperty("birthDate")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate birthDate,
        @JsonProperty("bankCode") BankCode bankCode,
        @JsonProperty("accountNo") String accountNo,
        @JsonProperty("holder") String holder,
        @JsonProperty("createdAt")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime regDt
) {

    public static PrivacyInfoV1Response of(PrivacyInfoAllDto privacyInfoAllDto) {
        return new PrivacyInfoV1Response(
                privacyInfoAllDto.getId(),
                privacyInfoAllDto.getDataUuid(),
                privacyInfoAllDto.getName(),
                privacyInfoAllDto.getMobileNo(),
                privacyInfoAllDto.getPhoneNo(),
                privacyInfoAllDto.getBirthDate(),
                privacyInfoAllDto.getBankCode(),
                privacyInfoAllDto.getAccountNo(),
                privacyInfoAllDto.getHolder(),
                privacyInfoAllDto.getRegDt()
        );
    }
}
