package io.glory.pips.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.glory.pips.app.service.exception.PrivacyInfoErrorCode;
import io.glory.pips.app.service.exception.PrivacyInfoException;
import io.glory.pips.data.constants.bank.BankCode;
import io.glory.pips.domain.entity.bank.BankAccount;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import io.glory.pips.domain.repository.bank.BankAccountRepository;
import io.glory.pips.domain.repository.privacy.PrivacyInfoRepository;
import io.glory.testsupport.integrated.IntegratedTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BankAccountServiceTest extends IntegratedTestSupport {

    @Autowired
    private BankAccountService    bankAccountService;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private PrivacyInfoRepository privacyInfoRepository;

    @AfterEach
    void tearDown() {
        bankAccountRepository.deleteAllInBatch();
        privacyInfoRepository.deleteAllInBatch();
    }

    @DisplayName("계좌 정보를 업데이트 한다")
    @Test
    void when_updateBankAccount_expect_DbUpdate() throws Exception {

        String accountNo = "1234567890";
        String holder = "홍길동";

        String newAccountNo = "0987654321";
        String newHolder = "김철수";

        PrivacyInfo privacyInfo = new PrivacyInfo("ownerId");
        BankAccount bankAccount = new BankAccount(BankCode.TEST_BANK, accountNo, holder, privacyInfo);

        PrivacyInfo pInfo = privacyInfoRepository.save(privacyInfo);
        BankAccount saved = bankAccountRepository.save(bankAccount);

        // when
        bankAccountService.updateBankAccount(pInfo.getId(), BankCode.TEST_BANK, newAccountNo, newHolder);

        // then
        BankAccount updated = bankAccountRepository.findById(saved.getId()).orElseThrow();
        assertThat(updated)
                .extracting(BankAccount::getAccountNo, BankAccount::getHolder)
                .containsExactly(newAccountNo, newHolder);
    }

    @DisplayName("계좌 정보를 업데이트시 계좌 정보가 없으면 DATA_NOT_FOUND 예외가 발생 한다")
    @Test
    void when_UpdateNonExisting_throw_DATA_NOT_FOUND() throws Exception {
        // given
        long nonExistingId = 1L;

        // when
        assertThatThrownBy(() -> bankAccountService.updateBankAccount(nonExistingId, null, "", ""))
                // then
                .isInstanceOf(PrivacyInfoException.class)
                .hasMessageContaining(PrivacyInfoErrorCode.DATA_NOT_FOUND.getMessage());
    }

}