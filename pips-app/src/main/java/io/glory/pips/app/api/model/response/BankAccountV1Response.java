package io.glory.pips.app.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.glory.pips.data.constants.bank.BankCode;

/**
 * 은행 계좌 정보 V1 응답 Spec
 * <pre>
 * {
 *   "bankCode": "BOK",
 *   "accountNo": "1234567890",
 *   "holder": "홍길동"
 * }
 *
 * @param bankCode  은행 코드
 * @param accountNo 계좌 번호
 * @param holder    예금주
 */
public record BankAccountV1Response(
        @JsonProperty("bankCode") BankCode bankCode,
        @JsonProperty("accountNo") String accountNo,
        @JsonProperty("holder") String holder
) {

}
