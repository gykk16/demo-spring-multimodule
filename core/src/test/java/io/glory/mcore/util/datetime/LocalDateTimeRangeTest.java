package io.glory.mcore.util.datetime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocalDateTimeRangeTest {

    @DisplayName("LocalDateTimeRange 생성 한다")
    @Test
    void init() throws Exception {
        // given
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59, 59);

        // when
        LocalDateTimeRange range = LocalDateTimeRange.of(start, end);

        // then
        assertThat(range.getStart()).isEqualTo(start);
        assertThat(range.getEnd()).isEqualTo(end);
    }

    @DisplayName("시작일시가 종료일시 보다 이후인 LocalDateTimeRange 생성 할 수 없다")
    @Test
    void when_InitWithWrongDates_expect_IllegalArgumentException() throws Exception {
        // given
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59, 59);

        // when
        assertThatThrownBy(() -> LocalDateTimeRange.of(start, end))
                // then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start date must be before end date");
    }

    @DisplayName("LocalDateTimeRange 에 포함된 날짜이면 true 를 반환 한다 - 시작일시 와 종료일시 포함")
    @Test
    void when_RangeContainsDate_expect_true() throws Exception {
        // given
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59, 59);

        LocalDateTimeRange range = LocalDateTimeRange.of(start, end);

        // when

        // then
        assertThat(range.contains(LocalDateTime.of(2023, 1, 1, 0, 0, 0))).isTrue();
        assertThat(range.contains(LocalDateTime.of(2023, 12, 31, 23, 59, 59))).isTrue();
    }

    @DisplayName("LocalDateTimeRange 에 포함되지 않은 날짜이면 false 를 반환 한다")
    @Test
    void when_RangeDoesNotContainsDate_expect_false() throws Exception {
        // given
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59, 59);

        LocalDateTimeRange range = LocalDateTimeRange.of(start, end);

        // when

        // then
        assertThat(range.contains(LocalDateTime.of(2022, 12, 31, 23, 59, 59, 999999))).isFalse();
        assertThat(range.contains(LocalDateTime.of(2024, 1, 1, 0, 0, 0))).isFalse();
    }

    @DisplayName("LocalDateTimeRange 의 기간을 구한다")
    @Test
    void when_RangeInclusiveDifference_expect_Duration() throws Exception {
        // given
        LocalDateTime start = LocalDate.of(2023, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(2024, 1, 1).atStartOfDay();
        LocalDateTimeRange range = LocalDateTimeRange.of(start, end);

        // when
        Duration duration = range.difference();

        // then
        assertThat(duration).isEqualTo(Duration.ofDays(365));
    }

    @DisplayName("LocalDateTimeRange 의 기간을 구한다 (시간 단위)")
    @Test
    void when_RangeHoursDifference_expect_Duration() throws Exception {
        // given
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 12, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 1, 14, 0, 0);
        LocalDateTimeRange range = LocalDateTimeRange.of(start, end);

        // when
        Duration duration = range.difference();

        // then
        assertThat(duration).isEqualTo(Duration.ofHours(2));
    }

    @DisplayName("LocalDateTimeRange 의 기간을 구한다 (초 단위)")
    @Test
    void when_RangeSecondsDifference_expect_Duration() throws Exception {
        // given
        LocalDateTime start = LocalDateTime.of(2023, 8, 31, 12, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 8, 31, 12, 1, 0);
        LocalDateTimeRange range = LocalDateTimeRange.of(start, end);

        // when
        Duration duration = range.difference();

        // then
        assertThat(duration).isEqualTo(Duration.ofSeconds(60));
    }

}