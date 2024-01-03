package io.glory.mcore.util.datetime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocalDateRangeTest {

    @DisplayName("LocalDateRange 생성 한다")
    @Test
    void init() throws Exception {
        // given
        LocalDate start = LocalDate.of(2021, 1, 1);
        LocalDate end = LocalDate.of(2021, 12, 31);

        // when
        LocalDateRange range = LocalDateRange.of(start, end);

        // then
        assertThat(range.getStart()).isEqualTo(start);
        assertThat(range.getEnd()).isEqualTo(end);
    }

    @DisplayName("시작일이 종료일 보다 이후인 LocalDateRange 생성 할 수 없다")
    @Test
    void when_InitWithWrongDates_expect_IllegalArgumentException() throws Exception {
        // given
        LocalDate start = LocalDate.of(2021, 12, 31);
        LocalDate end = LocalDate.of(2020, 1, 1);

        // when
        assertThatThrownBy(() -> LocalDateRange.of(start, end))
                // then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start date must be before end date");
    }

    @DisplayName("LocalDateRange 에 포함된 날짜이면 true 를 반환 한다 - 시작일 과 종료일 포함")
    @Test
    void when_RangeContainsDate_expect_true() throws Exception {
        // given
        LocalDateRange range = LocalDateRange.of(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31));

        // when

        // then
        assertThat(range.contains(LocalDate.of(2021, 1, 1))).isTrue();
        assertThat(range.contains(LocalDate.of(2021, 12, 31))).isTrue();
    }

    @DisplayName("LocalDateRange 에 포함되지 않은 날짜이면 false 를 반환 한다")
    @Test
    void when_RangeDoesNotContainsDate_expect_false() throws Exception {
        // given
        LocalDateRange range = LocalDateRange.of(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31));

        // when

        // then
        assertThat(range.contains(LocalDate.of(2020, 12, 31))).isFalse();
        assertThat(range.contains(LocalDate.of(2022, 1, 1))).isFalse();
    }

    @DisplayName("LocalDateRange 의 기간을 구한다 - 시작일 과 종료일 포함")
    @Test
    void when_RangeInclusiveDifference_expect_Period() throws Exception {
        // given
        Period expectedPeriod = Period.of(1, 0, 0);

        // when
        LocalDateRange range = LocalDateRange.of(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31));

        // then
        assertThat(range.difference(true)).isEqualTo(expectedPeriod);
    }

    @DisplayName("LocalDateRange 의 기간을 구한다 - 종료일 제외")
    @Test
    void when_RangeExclusiveDifference_expect_Period() throws Exception {
        // given
        Period expectedPeriod = Period.of(0, 11, 30);

        // when
        LocalDateRange range = LocalDateRange.of(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31));

        // then
        assertThat(range.difference()).isEqualTo(expectedPeriod);
    }

    @DisplayName("유효기간 만료 경과 일수를 구한다")
    @Test
    void findExpiredDays() throws Exception {
        // given
        LocalDate expireDate = LocalDate.of(2023, 9, 10);
        LocalDate today = LocalDate.of(2023, 9, 12);

        LocalDateRange range = LocalDateRange.of(expireDate, today);

        // when
        Period difference = range.difference();

        // then
        assertThat(difference.getDays()).isEqualTo(2);
    }

}