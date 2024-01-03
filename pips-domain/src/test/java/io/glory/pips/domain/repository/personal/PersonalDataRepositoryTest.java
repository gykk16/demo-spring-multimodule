package io.glory.pips.domain.repository.personal;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import io.glory.pips.domain.entity.personal.PersonalData;
import io.glory.pips.domain.entity.privacy.PrivacyInfo;
import io.glory.pips.domain.repository.privacy.PrivacyInfoRepository;
import io.glory.testsupport.integrated.IntegratedTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PersonalDataRepositoryTest extends IntegratedTestSupport {

    @Autowired
    private PersonalDataRepository personalDataRepository;
    @Autowired
    private PrivacyInfoRepository  privacyInfoRepository;

    @AfterEach
    void tearDown() {
        personalDataRepository.deleteAllInBatch();
        privacyInfoRepository.deleteAllInBatch();
    }

    @DisplayName("개인정보 id 로 개인 식별 정보를 조회 한다")
    @Test
    void fetchByPrivacyInfoId() throws Exception {
        // given
        String name = "홍길동";
        String mobileNo = "01012345678";
        String phoneNo = "021234567";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);

        PrivacyInfo privacyInfo = new PrivacyInfo("ownerId");
        PersonalData personalData = PersonalData.builder()
                .name(name)
                .mobileNo(mobileNo)
                .phoneNo(phoneNo)
                .birthDate(birthDate)
                .privacyInfo(privacyInfo)
                .build();

        PrivacyInfo savedPrivacyInfo = privacyInfoRepository.save(privacyInfo);
        PersonalData savedPersonalData = personalDataRepository.save(personalData);

        // when
        PersonalData fetched = personalDataRepository.fetchByPrivacyInfoId(savedPrivacyInfo.getId()).orElseThrow();

        // then
        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(savedPersonalData.getId());
        assertThat(fetched.getName()).isEqualTo(name);
        assertThat(fetched.getMobileNo()).isEqualTo(mobileNo);
        assertThat(fetched.getPhoneNo()).isEqualTo(phoneNo);
        assertThat(fetched.getBirthDate()).isEqualTo(birthDate);

        assertThat(fetched.getPrivacyInfo()).isNotNull();
        assertThat(fetched.getPrivacyInfo().getId()).isEqualTo(savedPrivacyInfo.getId());
        assertThat(fetched.getPrivacyInfo().getDataUuid()).isEqualTo(savedPrivacyInfo.getDataUuid());
    }

}