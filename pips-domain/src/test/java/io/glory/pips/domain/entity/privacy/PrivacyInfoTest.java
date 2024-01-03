package io.glory.pips.domain.entity.privacy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PrivacyInfoTest {

    @DisplayName("개인정보 객체 생성시 개인정보 고유 uuid 가 생성 된다")
    @Test
    void when_init_expect_NewUUID() throws Exception {
        // given

        // when
        PrivacyInfo privacyInfo = new PrivacyInfo("ownerId");

        // then
        assertThat(privacyInfo.getDataUuid()).isNotNull();
    }

}