package io.glory.pips.app.service.model;

import java.time.LocalDate;

import io.glory.pips.data.constants.bank.BankCode;
import io.glory.pips.domain.entity.bank.BankAccount;
import io.glory.pips.domain.entity.personal.PersonalData;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;

public record PrivacyInfoServiceRequest(
        String name,
        String mobileNo,
        String phoneNo,
        LocalDate birthDate,
        BankCode bankCode,
        String accountNo,
        String holder
) {

    public PersonalData toPersonalDataEntity(PrivacyInfo privacyInfo) {
        return PersonalData.builder()
                .name(name)
                .mobileNo(mobileNo)
                .phoneNo(phoneNo)
                .birthDate(birthDate)
                .privacyInfo(privacyInfo)
                .build();
    }

    public BankAccount toBankAccountEntity(PrivacyInfo privacyInfo) {
        return new BankAccount(bankCode, accountNo, holder, privacyInfo);
    }

}
