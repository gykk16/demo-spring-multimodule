package io.glory.pips.domain.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import io.glory.pips.data.constants.bank.BankCode;
import io.glory.pips.domain.entity.bank.BankAccount;
import io.glory.pips.domain.entity.personal.PersonalData;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import io.glory.pips.domain.repository.bank.BankAccountRepository;
import io.glory.pips.domain.repository.personal.PersonalDataRepository;
import io.glory.pips.domain.repository.privacy.PrivacyInfoRepository;
import io.glory.testsupport.integrated.IntegratedTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PrivacyInfoQueryRepositoryTest extends IntegratedTestSupport {

    @Autowired
    private PrivacyInfoQueryRepository privacyInfoQueryRepository;

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

    @DisplayName("개인정보 id 로 개인 식별 정보를 조회 한다")
    @Test
    void fetchPrivacyInfos() throws Exception {
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
        PrivacyInfoAllDto fetched = privacyInfoQueryRepository.fetchPrivacyInfos(List.of(savedInfo.getId()))
                .stream().findFirst().orElseThrow();

        // then
        System.out.println("==> fetched = " + fetched);
        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(savedInfo.getId());
        assertThat(fetched.getDataUuid()).isNotNull();
        assertThat(fetched.getName()).isEqualTo(name);
        assertThat(fetched.getMobileNo()).isEqualTo(mobileNo);
        assertThat(fetched.getPhoneNo()).isEqualTo(phoneNo);
        assertThat(fetched.getBirthDate()).isEqualTo(birthDate);
        assertThat(fetched.getBankCode()).isEqualTo(BankCode.TEST_BANK);
        assertThat(fetched.getAccountNo()).isEqualTo(accountNo);
        assertThat(fetched.getHolder()).isEqualTo(name);
        assertThat(fetched.getRegDt()).isNotNull();

    }

}