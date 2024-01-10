package io.glory.pips.app.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import io.glory.pips.app.api.model.request.PrivacyInfoV1Request;
import io.glory.pips.app.service.model.PrivacyInfoServiceRequest;
import io.glory.pips.data.constants.bank.BankCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PrivacyInfoV1RequestTest {

    @DisplayName("PrivacyInfoV1Request 를 PrivacyInfoServiceRequest 으로 변환 한다")
    @Test
    void when_toServiceRequest_expect_PrivacyInfoServiceRequest() throws Exception {
        // given
        String name = "홍길동";
        String mobileNo = "01012345678";
        String phoneNo = "021234567";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        BankCode bankCode = BankCode.TEST_BANK;
        String accountNo = "1234567890";
        String holder = "홍길동";

        PrivacyInfoV1Request privacyInfoV1Request = new PrivacyInfoV1Request(name, mobileNo, phoneNo, birthDate,
                bankCode, accountNo, holder);

        // when
        PrivacyInfoServiceRequest privacyInfoServiceRequest = privacyInfoV1Request.toServiceRequest();

        // then
        assertThat(privacyInfoServiceRequest).isNotNull()
                .extracting("name", "mobileNo", "phoneNo", "birthDate", "bankCode", "accountNo", "holder")
                .containsExactly(name, mobileNo, phoneNo, birthDate, bankCode, accountNo, holder);
    }

}