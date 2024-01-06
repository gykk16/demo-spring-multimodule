package io.glory.pips.app.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.glory.pips.domain.query.PrivacyInfoAllDto;

/**
 * 개인정보 조회 V1 응답 Spec
 *
 * @param id           개인정보 id
 * @param dataUuid     개인정보 고유 uuid
 * @param personalData 개인 신상 정보
 * @param bankAccount  은행 계좌 정보
 * @param regDt        등록일시
 */
// @JsonInclude(JsonInclude.Include.NON_NULL)
public record PrivacyInfoV1Response(
        @JsonProperty("id") Long id,
        @JsonProperty("dataUuid") UUID dataUuid,
        @JsonProperty("personalData") PersonalDataV1Response personalData,
        @JsonProperty("bankAccount") BankAccountV1Response bankAccount,
        @JsonProperty("createdAt")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime regDt
) {

    public static PrivacyInfoV1Response of(PrivacyInfoAllDto privacyInfoAllDto) {
        return new PrivacyInfoV1Response(
                privacyInfoAllDto.getId(),
                privacyInfoAllDto.getDataUuid(),
                new PersonalDataV1Response(
                        privacyInfoAllDto.getName(),
                        privacyInfoAllDto.getMobileNo(),
                        privacyInfoAllDto.getPhoneNo(),
                        privacyInfoAllDto.getBirthDate()
                ),
                new BankAccountV1Response(
                        privacyInfoAllDto.getBankCode(),
                        privacyInfoAllDto.getAccountNo(),
                        privacyInfoAllDto.getHolder()
                ),
                privacyInfoAllDto.getRegDt()
        );
    }
}
