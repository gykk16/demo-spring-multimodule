package io.glory.pips.domain.query;

import static io.glory.pips.domain.entity.bank.QBankAccount.bankAccount;
import static io.glory.pips.domain.entity.personal.QPersonalData.personalData;
import static io.glory.pips.domain.entity.privacy.QPrivacyInfo.privacyInfo;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PrivacyInfoQueryRepository {

    private final JPAQueryFactory factory;

    public List<PrivacyInfoAllDto> fetchPrivacyInfos(List<Long> ids) {
        return factory
                .select(new QPrivacyInfoAllDto(
                        privacyInfo.id,
                        privacyInfo.dataUuid,
                        personalData.name,
                        personalData.mobileNo,
                        personalData.phoneNo,
                        personalData.birthDate,
                        bankAccount.bankCode,
                        bankAccount.accountNo,
                        bankAccount.holder,
                        privacyInfo.regDt
                ))
                .from(privacyInfo)
                .leftJoin(personalData).on(personalData.privacyInfo.id.eq(privacyInfo.id))
                .leftJoin(bankAccount).on(bankAccount.privacyInfo.id.eq(privacyInfo.id))
                .where(privacyInfo.id.in(ids))
                .fetch();
    }

}
