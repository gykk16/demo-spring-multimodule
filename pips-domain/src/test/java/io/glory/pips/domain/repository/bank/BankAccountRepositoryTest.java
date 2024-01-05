package io.glory.pips.domain.repository.bank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;

import io.glory.pips.data.constants.bank.BankCode;
import io.glory.pips.domain.entity.bank.BankAccount;
import io.glory.pips.domain.entity.personal.PersonalData;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import io.glory.pips.domain.repository.privacy.PrivacyInfoRepository;
import io.glory.testsupport.integrated.IntegratedTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BankAccountRepositoryTest extends IntegratedTestSupport {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private PrivacyInfoRepository privacyInfoRepository;

    @AfterEach
    void tearDown() {
        bankAccountRepository.deleteAllInBatch();
        privacyInfoRepository.deleteAllInBatch();
    }

    @DisplayName("개인정보 id 로 은행 계좌 정보를 조회 한다")
    @Test
    void fetchByPrivacyInfoId() throws Exception {
        // given
        String accountNo = "1234567890";
        String holder = "홍길동";

        PrivacyInfo privacyInfo = new PrivacyInfo("ownerId");
        BankAccount bankAccount = new BankAccount(BankCode.TEST_BANK, accountNo, holder, privacyInfo);
        PrivacyInfo savedPrivacyInfo = privacyInfoRepository.save(privacyInfo);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        // when
        BankAccount fetched = bankAccountRepository.fetchByPrivacyInfoId(savedPrivacyInfo.getId()).orElseThrow();

        // then
        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(savedBankAccount.getId());
        assertThat(fetched.getBankCode()).isEqualTo(BankCode.TEST_BANK);
        assertThat(fetched.getAccountNo()).isEqualTo(accountNo);
        assertThat(fetched.getHolder()).isEqualTo(holder);

        assertThat(fetched.getPrivacyInfo()).isNotNull();
        assertThat(fetched.getPrivacyInfo().getId()).isEqualTo(savedPrivacyInfo.getId());
        assertThat(fetched.getPrivacyInfo().getDataUuid()).isEqualTo(savedPrivacyInfo.getDataUuid());
    }

    @DisplayName("개인정보 id 로 은행 계좌 정보를 조회 한다 - SoftDelete 된 정보는 조회 되지 않는다")
    @Test
    void when_fetchByPrivacyInfoId_expect_OnlyNonDeleted() throws Exception {
        // given
        String accountNo = "1234567890";
        String holder = "홍길동";

        PrivacyInfo privacyInfo = new PrivacyInfo("ownerId");
        BankAccount bankAccount = new BankAccount(BankCode.TEST_BANK, accountNo, holder, privacyInfo);
        PrivacyInfo savedPrivacyInfo = privacyInfoRepository.save(privacyInfo);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        // when
        assertThatCode(() -> bankAccountRepository.fetchByPrivacyInfoId(savedPrivacyInfo.getId()))
                // then
                .isNull();
    }

}