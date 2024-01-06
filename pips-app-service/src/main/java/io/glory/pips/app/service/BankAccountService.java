package io.glory.pips.app.service;

import lombok.RequiredArgsConstructor;

import io.glory.pips.app.service.exception.PrivacyInfoErrorCode;
import io.glory.pips.app.service.exception.PrivacyInfoException;
import io.glory.pips.data.constants.bank.BankCode;
import io.glory.pips.domain.entity.bank.BankAccount;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import io.glory.pips.domain.repository.bank.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public long save(BankCode bankCode, String accountNo, String holder, PrivacyInfo privacyInfo) {
        BankAccount saved = bankAccountRepository.save(new BankAccount(bankCode, accountNo, holder, privacyInfo));
        return saved.getId();
    }

    @Transactional
    public void update(Long pInfoId, BankCode bankCode, String accountNo, String holder) {
        bankAccountRepository.fetchByPrivacyInfoId(pInfoId)
                .ifPresentOrElse(
                        bankAccount -> bankAccount.updateBankAccount(bankCode, accountNo, holder),
                        () -> {
                            throw new PrivacyInfoException(PrivacyInfoErrorCode.DATA_NOT_FOUND);
                        }
                );
    }

}
