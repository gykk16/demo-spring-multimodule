package io.glory.mcore.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PhoneUtilTest {

    @DisplayName("유효한 번호이면 true 를 반환 한다")
    @Test
    void when_IsValidNo_expect_true() throws Exception {
        // given
        String[] validNos = {"010-1234-1234", "010.1234.1234", "01012341234",
                "02-1234-1234", "02.1234.1234", "0212341234", "031-1234-1234"};

        // when
        for (String validNo : validNos) {
            boolean result = PhoneUtil.isValidNo(validNo);
            // then
            assertThat(result).isTrue();
        }
    }

    @DisplayName("유효한 휴대폰 번호이면 true 를 반환 한다")
    @Test
    void when_IsValidMobileNo_expect_true() {
        // given
        String validMobileNo1 = "010-1234-1234";
        String validMobileNo2 = "010.1234.1234";
        String validMobileNo3 = "01012341234";

        // when
        boolean result1 = PhoneUtil.isValidMobileNo(validMobileNo1);
        boolean result2 = PhoneUtil.isValidMobileNo(validMobileNo2);
        boolean result3 = PhoneUtil.isValidMobileNo(validMobileNo3);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

    @DisplayName("유효한 번호이면 true 를 반환 한다")
    @Test
    void when_IsValidPhoneNo_expect_true() {
        // given
        String validPhoneNo1 = "02-1234-1234";
        String validPhoneNo2 = "02.1234.1234";
        String validPhoneNo3 = "0212341234";
        String validPhoneNo4 = "031-1234-1234";

        // when
        boolean result1 = PhoneUtil.isValidPhoneNo(validPhoneNo1);
        boolean result2 = PhoneUtil.isValidPhoneNo(validPhoneNo2);
        boolean result3 = PhoneUtil.isValidPhoneNo(validPhoneNo3);
        boolean result4 = PhoneUtil.isValidPhoneNo(validPhoneNo4);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
        assertThat(result4).isTrue();
    }

    @DisplayName("번호의 구분자를 제거 한다")
    @Test
    void when_removeDelimiter_expect_PhoneNoWithoutDelimiter() {
        // given
        String phoneNoWithDelimiters = "123-456-7890";

        // when
        String result = PhoneUtil.removeDelimiter(phoneNoWithDelimiters);

        // then
        assertThat(result).isEqualTo("1234567890");
    }

}