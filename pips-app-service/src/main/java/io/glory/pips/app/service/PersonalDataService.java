package io.glory.pips.app.service;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;

import io.glory.pips.app.service.exception.PrivacyInfoErrorCode;
import io.glory.pips.app.service.exception.PrivacyInfoException;
import io.glory.pips.domain.repository.personal.PersonalDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonalDataService {

    private final PersonalDataRepository personalDataRepository;

    @Transactional
    public void updatePersonalData(Long pInfoId, String name, LocalDate birthDate, String mobileNo, String phoneNo) {
        personalDataRepository.fetchByPrivacyInfoId(pInfoId)
                .ifPresentOrElse(
                        personalData -> personalData.updatePersonalData(name, birthDate, mobileNo, phoneNo),
                        () -> {
                            throw new PrivacyInfoException(PrivacyInfoErrorCode.DATA_NOT_FOUND);
                        });
    }

}
