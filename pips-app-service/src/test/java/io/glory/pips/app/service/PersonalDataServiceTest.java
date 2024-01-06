package io.glory.pips.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import io.glory.pips.app.service.exception.PrivacyInfoErrorCode;
import io.glory.pips.app.service.exception.PrivacyInfoException;
import io.glory.pips.domain.entity.personal.PersonalData;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import io.glory.pips.domain.repository.personal.PersonalDataRepository;
import io.glory.pips.domain.repository.privacy.PrivacyInfoRepository;
import io.glory.testsupport.integrated.IntegratedTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PersonalDataServiceTest extends IntegratedTestSupport {

    private static final String    NAME       = "홍길동";
    private static final String    MOBILE_NO  = "01012345678";
    private static final String    PHONE_NO   = "021234567";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 1, 1);

    @Autowired
    private PersonalDataService    personalDataService;
    @Autowired
    private PersonalDataRepository personalDataRepository;
    @Autowired
    private PrivacyInfoRepository  privacyInfoRepository;

    @AfterEach
    void tearDown() {
        personalDataRepository.deleteAllInBatch();
        privacyInfoRepository.deleteAllInBatch();
    }

    @DisplayName("개인 식별 정보를 저장 한다")
    @Test
    void when_save_expect_DbInsert() throws Exception {
        // given
        PrivacyInfo privacyInfo = new PrivacyInfo("ownerId");
        PrivacyInfo pInfo = privacyInfoRepository.save(privacyInfo);

        // when
        long savedId = personalDataService.save(NAME, BIRTH_DATE, MOBILE_NO, PHONE_NO, pInfo);

        // then
        assertThatCode(() -> personalDataRepository.findById(savedId).orElseThrow())
                .doesNotThrowAnyException();
    }

    @DisplayName("개인 식별 정보를 업데이트 한다")
    @Test
    void when_updatePersonalData_expect_DbUpdate() throws Exception {
        // given
        String newName = "김말자";
        String newMobileNo = "01000000000";
        String newPhoneNo = "0200000000";
        LocalDate newBirthDate = LocalDate.of(2000, 1, 1);

        PrivacyInfo privacyInfo = new PrivacyInfo("ownerId");
        PersonalData personalData = PersonalData.builder()
                .name(NAME)
                .mobileNo(MOBILE_NO)
                .phoneNo(PHONE_NO)
                .birthDate(BIRTH_DATE)
                .privacyInfo(privacyInfo)
                .build();

        PrivacyInfo pInfo = privacyInfoRepository.save(privacyInfo);
        PersonalData saved = personalDataRepository.save(personalData);

        // when
        personalDataService.update(pInfo.getId(), newName, newBirthDate, newMobileNo, newPhoneNo);

        // then
        PersonalData updated = personalDataRepository.findById(saved.getId()).orElseThrow();
        assertThat(updated)
                .extracting("name", "birthDate", "mobileNo", "phoneNo")
                .containsExactly(newName, newBirthDate, newMobileNo, newPhoneNo);
    }

    @DisplayName("개인 식별 정보를 업데이트시 개인 식별 정보가 없으면 DATA_NOT_FOUND 예외가 발생 한다")
    @Test
    void when_UpdateNonExisting_throw_DATA_NOT_FOUND() throws Exception {
        // given
        long nonExistingId = 1L;

        // when
        assertThatThrownBy(() -> personalDataService.update(nonExistingId, "", null, "", ""))
                // then
                .isInstanceOf(PrivacyInfoException.class)
                .hasMessageContaining(PrivacyInfoErrorCode.DATA_NOT_FOUND.getMessage());
    }

}