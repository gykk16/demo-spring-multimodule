package io.glory.pips.domain.repository.privacy;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;

import io.glory.pips.data.constants.bank.BankCode;
import io.glory.pips.domain.entity.bank.BankAccount;
import io.glory.pips.domain.entity.personal.PersonalData;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import io.glory.pips.domain.repository.bank.BankAccountRepository;
import io.glory.pips.domain.repository.personal.PersonalDataRepository;
import io.glory.testsupport.integrated.IntegratedTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PrivacyInfoRepositoryTest extends IntegratedTestSupport {

    @Autowired
    private BankAccountRepository  bankAccountRepository;
    @Autowired
    private PersonalDataRepository personalDataRepository;
    @Autowired
    private PrivacyInfoRepository  privacyInfoRepository;

    @AfterEach
    void tearDown() {
        bankAccountRepository.deleteAllInBatch();
        personalDataRepository.deleteAllInBatch();
        privacyInfoRepository.deleteAllInBatch();
    }

    @DisplayName("개인정보 id 를 삭제 하면 Db 에서 SoftDelete 된다")
    @Test
    void when_delete_expect_SoftDelete() throws Exception {
        // given
        String name = "홍길동";
        String mobileNo = "01012345678";
        String phoneNo = "021234567";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String accountNo = "1234567890";

        PrivacyInfo privacyInfo = new PrivacyInfo("ownerId");
        BankAccount bankAccount = new BankAccount(BankCode.TEST_BANK, accountNo, name, privacyInfo);
        PersonalData personalData = PersonalData.builder()
                .name(name)
                .mobileNo(mobileNo)
                .phoneNo(phoneNo)
                .birthDate(birthDate)
                .privacyInfo(privacyInfo)
                .build();
        PrivacyInfo savedInfo = privacyInfoRepository.save(privacyInfo);
        bankAccountRepository.save(bankAccount);
        personalDataRepository.save(personalData);

        // when
        privacyInfoRepository.deleteById(savedInfo.getId());

        // then
        assertThatCode(() -> privacyInfoRepository.findById(savedInfo.getId())).isNull();
    }

}