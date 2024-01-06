package io.glory.pips.app.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import io.glory.pips.data.constants.bank.BankCode;
import io.glory.pips.domain.query.PrivacyInfoAllDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PrivacyInfoV1ResponseTest {

    @DisplayName("PrivacyInfoAllDto 로 PrivacyInfoV1Response 를 생성 한다")
    @Test
    void when_ofPrivacyInfoAllDto_expect_PrivacyInfoV1Response() throws Exception {
        // given
        long id = 1L;
        UUID dataUuid = UUID.randomUUID();
        String name = "홍길동";
        String mobileNo = "01012345678";
        String phoneNo = "021234567";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        BankCode bankCode = BankCode.TEST_BANK;
        String accountNo = "1234567890";
        String holder = "홍길동";
        LocalDateTime regDt = LocalDateTime.now();

        PrivacyInfoAllDto privacyInfoAllDto = PrivacyInfoAllDto.builder()
                .id(id)
                .dataUuid(dataUuid)
                .name(name)
                .mobileNo(mobileNo)
                .phoneNo(phoneNo)
                .birthDate(birthDate)
                .bankCode(bankCode)
                .accountNo(accountNo)
                .holder(holder)
                .regDt(regDt)
                .build();

        // when
        PrivacyInfoV1Response privacyInfoV1Response = PrivacyInfoV1Response.of(privacyInfoAllDto);

        // then
        assertThat(privacyInfoV1Response).isNotNull()
                .extracting("id", "dataUuid", "regDt", "personalData", "bankAccount")
                .contains(id, dataUuid, regDt,
                        new PersonalDataV1Response(name, mobileNo, phoneNo, birthDate),
                        new BankAccountV1Response(bankCode, accountNo, holder)
                );
    }

}