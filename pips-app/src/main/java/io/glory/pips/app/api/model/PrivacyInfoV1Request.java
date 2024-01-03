package io.glory.pips.app.api.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import io.glory.pips.app.service.model.PrivacyInfoServiceRequest;
import io.glory.pips.data.constants.bank.BankCode;

/**
 * 개인정보 조회 V1 요청 Spec
 *
 * @param name      이름
 * @param mobileNo  휴대폰 번호
 * @param phoneNo   전화번호
 * @param birthDate 생년월일
 * @param bankCode  은행코드
 * @param accountNo 계좌번호
 * @param holder    예금주
 */
@JsonIgnoreProperties
public record PrivacyInfoV1Request(
        @JsonAlias("name") String name,
        @JsonAlias("mobileNo") String mobileNo,
        @JsonAlias("phoneNo") String phoneNo,
        @JsonAlias("birthDate")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate birthDate,
        @JsonAlias("bankCode") BankCode bankCode,
        @JsonAlias("accountNo") String accountNo,
        @JsonAlias("holder") String holder
) {

    public PrivacyInfoServiceRequest toServiceRequest() {
        return new PrivacyInfoServiceRequest(name, mobileNo, phoneNo, birthDate, bankCode, accountNo, holder);
    }
}
