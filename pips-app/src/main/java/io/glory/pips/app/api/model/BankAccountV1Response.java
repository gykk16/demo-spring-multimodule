package io.glory.pips.app.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.glory.pips.data.constants.bank.BankCode;

public record BankAccountV1Response(
        @JsonProperty("bankCode") BankCode bankCode,
        @JsonProperty("accountNo") String accountNo,
        @JsonProperty("holder") String holder
) {

}
