package io.glory.mcore.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponNoParserTest {

    @DisplayName("쿠폰번호에 구분자를 추가한다 - 경계 테스트")
    @Test
    void when_addDelimiter_expect_CouponNoWithDelimiter() throws Exception {
        // given
        final String couponNo1 = "1";
        final String couponNo4 = "1234";
        final String couponNo5 = "12345";
        final String couponNo6 = "123456";
        final String couponNo9 = "123456789";
        final String couponNo12 = "123456789012";
        final String couponNo14 = "12345678901234";
        final String couponNo15 = "123456789012345";
        final String couponNo16 = "1234567890123456";
        final String couponNo17 = "12345678901234567";
        final String couponNo20 = "12345678901234567890";

        // when
        String result1 = CouponNoParser.addDelimiter(couponNo1);
        String result4 = CouponNoParser.addDelimiter(couponNo4);
        String result5 = CouponNoParser.addDelimiter(couponNo5);
        String result6 = CouponNoParser.addDelimiter(couponNo6);
        String result9 = CouponNoParser.addDelimiter(couponNo9);
        String result12 = CouponNoParser.addDelimiter(couponNo12);
        String result14 = CouponNoParser.addDelimiter(couponNo14);
        String result15 = CouponNoParser.addDelimiter(couponNo15);
        String result16 = CouponNoParser.addDelimiter(couponNo16);
        String result17 = CouponNoParser.addDelimiter(couponNo17);
        String result20 = CouponNoParser.addDelimiter(couponNo20);

        // then
        assertThat(result1).isEqualTo("1");
        assertThat(result4).isEqualTo("1234");
        assertThat(result5).isEqualTo("12345");
        assertThat(result6).isEqualTo("123-456");
        assertThat(result9).isEqualTo("123-456-789");
        assertThat(result12).isEqualTo("1234-5678-9012");
        assertThat(result14).isEqualTo("1234-5678-9012-34");
        assertThat(result15).isEqualTo("12345-67890-12345");
        assertThat(result16).isEqualTo("1234-5678-9012-3456");
        assertThat(result17).isEqualTo("12345-67890-12345-67");
        assertThat(result20).isEqualTo("12345-67890-12345-67890");
    }

}