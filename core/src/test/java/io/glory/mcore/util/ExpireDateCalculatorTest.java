package io.glory.mcore.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExpireDateCalculatorTest {

    @DisplayName("유효기간이 오늘 이면 유효한 날짜다")
    @Test
    void when_ExpireDateIsToday_expect_false() throws Exception {
        // given
        LocalDate expireDate = LocalDate.now();

        // when
        boolean expired = ExpireDateCalculator.isExpired(expireDate);

        // then
        assertThat(expired).isFalse();
    }

    @DisplayName("유효기간이 내일 이면 유효한 날짜다")
    @Test
    void when_ExpireDateIsTomorrow_expect_false() throws Exception {
        // given
        LocalDate expireDate = LocalDate.now().plusDays(1);

        // when
        boolean expired = ExpireDateCalculator.isExpired(expireDate);

        // then
        assertThat(expired).isFalse();
    }

    @DisplayName("유효기간이 어제 이면 만료된 날짜다")
    @Test
    void when_ExpireDateIsTomorrow_expect_true() throws Exception {
        // given
        LocalDate expireDate = LocalDate.now().minusDays(1);

        // when
        boolean expired = ExpireDateCalculator.isExpired(expireDate);

        // then
        assertThat(expired).isTrue();
    }

    @DisplayName("유효기간 만료 일수 계산 - 유효기간이 오늘 이면 0을 반환 한다")
    @Test
    void when_ExpireDateIsToday_expect_Zero() throws Exception {
        // given
        LocalDate expireDate = LocalDate.now();

        // when
        int expiredDays = ExpireDateCalculator.expiredDays(expireDate);

        // then
        assertThat(expiredDays).isZero();
    }

    @DisplayName("유효기간 만료 일수 계산 - 유효기간이 내일 까지 이면 -1을 반환 한다")
    @Test
    void when_ExpireDateIsTomorrow_expect_MinusOne() throws Exception {
        // given
        LocalDate expireDate = LocalDate.now().plusDays(1);

        // when
        int expiredDays = ExpireDateCalculator.expiredDays(expireDate);

        // then
        assertThat(expiredDays).isEqualTo(-1);
    }

    @DisplayName("유효기간 만료 일수 계산 - 유효기간이 하루 지났으면 1을 반환 한다")
    @Test
    void when_ExpireDateIsYesterday_expect_One() throws Exception {
        // given
        LocalDate expireDate = LocalDate.now().minusDays(1);

        // when
        int expiredDays = ExpireDateCalculator.expiredDays(expireDate);

        // then
        assertThat(expiredDays).isEqualTo(1);
    }

    @DisplayName("유효기간 까지의 일수를 반환 한다 - 유효기간이 오늘 이면 0을 반환 한다")
    @Test
    void when_ExpireDateIsToday_expect_Zero2() throws Exception {
        // given
        LocalDate expireDate = LocalDate.now();

        // when
        int daysToExpireDate = ExpireDateCalculator.daysToExpireDate(expireDate);

        // then
        assertThat(daysToExpireDate).isZero();
    }

    @DisplayName("유효기간 까지의 일수를 반환 한다 - 유효기간이 내일 이면 1을 반환 한다")
    @Test
    void when_ExpireDateIsTomorrow_expect_One2() throws Exception {
        // given
        LocalDate expireDate = LocalDate.now().plusDays(1);

        // when
        int daysToExpireDate = ExpireDateCalculator.daysToExpireDate(expireDate);

        // then
        assertThat(daysToExpireDate).isOne();
    }

    @DisplayName("유효기간 까지의 일수를 반환 한다 - 유효기간이 어제 이면 -1을 반환 한다")
    @Test
    void when_ExpireDateIsYesterday_expect_MinusOne2() throws Exception {
        // given
        LocalDate expireDate = LocalDate.now().minusDays(1);

        // when
        int daysToExpireDate = ExpireDateCalculator.daysToExpireDate(expireDate);

        // then
        assertThat(daysToExpireDate).isEqualTo(-1);
    }

}