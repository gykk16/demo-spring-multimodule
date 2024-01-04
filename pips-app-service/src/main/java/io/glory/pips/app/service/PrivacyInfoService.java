package io.glory.pips.app.service;

import static io.glory.pips.app.service.exception.PrivacyInfoErrorCode.DATA_NOT_FOUND;
import static io.glory.pips.app.service.exception.PrivacyInfoErrorCode.UPDATE_ERROR;

import java.util.List;

import lombok.RequiredArgsConstructor;

import io.glory.pips.app.service.exception.PrivacyInfoException;
import io.glory.pips.app.service.model.PrivacyInfoServiceRequest;
import io.glory.pips.domain.entity.bank.BankAccount;
import io.glory.pips.domain.entity.personal.PersonalData;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import io.glory.pips.domain.query.PrivacyInfoAllDto;
import io.glory.pips.domain.query.PrivacyInfoQueryRepository;
import io.glory.pips.domain.repository.bank.BankAccountRepository;
import io.glory.pips.domain.repository.personal.PersonalDataRepository;
import io.glory.pips.domain.repository.privacy.PrivacyInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrivacyInfoService {

    private final PersonalDataService personalDataService;
    private final BankAccountService  bankAccountService;

    private final PrivacyInfoRepository  privacyInfoRepository;
    private final PersonalDataRepository personalDataRepository;
    private final BankAccountRepository  bankAccountRepository;

    private final PrivacyInfoQueryRepository privacyInfoQueryRepository;

    public PrivacyInfoAllDto fetchPrivacyInfo(long id) {

        return privacyInfoQueryRepository.fetchPrivacyInfos(List.of(id))
                .stream()
                .findFirst()
                .orElseThrow(() -> new PrivacyInfoException(DATA_NOT_FOUND));
    }

    public List<PrivacyInfoAllDto> fetchPrivacyInfos(List<Long> ids) {

        List<PrivacyInfoAllDto> dtoList = privacyInfoQueryRepository.fetchPrivacyInfos(ids);
        if (dtoList.isEmpty()) {
            throw new PrivacyInfoException(DATA_NOT_FOUND);
        }
        return dtoList;
    }

    @Transactional
    public Long savePrivacyInfo(PrivacyInfoServiceRequest request) {

        PrivacyInfo privacyInfo = new PrivacyInfo("ownerId");
        PersonalData personalData = request.toPersonalDataEntity(privacyInfo);
        BankAccount bankAccount = request.toBankAccountEntity(privacyInfo);

        PrivacyInfo saved = privacyInfoRepository.save(privacyInfo);
        personalDataRepository.save(personalData);
        bankAccountRepository.save(bankAccount);

        return saved.getId();
    }

    @Transactional
    public Long updatePrivacyInfo(Long pInfoId, PrivacyInfoServiceRequest req) {

        try {
            personalDataService.updatePersonalData(pInfoId, req.name(), req.birthDate(), req.mobileNo(), req.phoneNo());
            bankAccountService.updateBankAccount(pInfoId, req.bankCode(), req.accountNo(), req.holder());
        } catch (PrivacyInfoException e) {
            throw e;
        } catch (Exception e) {
            throw new PrivacyInfoException(UPDATE_ERROR, e);
        }

        return pInfoId;
    }

}
