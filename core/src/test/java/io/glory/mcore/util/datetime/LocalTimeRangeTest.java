package io.glory.mcore.util.datetime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocalTimeRangeTest {

    @DisplayName("LocalTimeRange 생성 한다")
    @Test
    void init() throws Exception {
        // given
        LocalTime start = LocalTime.of(0, 0, 0);
        LocalTime end = LocalTime.of(23, 59, 59);

        // when
        LocalTimeRange range = LocalTimeRange.of(start, end);

        // then
        assertThat(range.getStart()).isEqualTo(start);
        assertThat(range.getEnd()).isEqualTo(end);
    }

    @DisplayName("시작 시간이 종료 시간 보다 이후인 LocalTimeRange 생성 할 수 없다")
    @Test
    void when_InitWithWrongTime_expect_IllegalArgumentException() throws Exception {
        // given
        LocalTime start = LocalTime.of(12, 0, 1);
        LocalTime end = LocalTime.of(12, 0, 0);

        // when
        assertThatThrownBy(() -> LocalTimeRange.of(start, end))
                // then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start time must be before end time");
    }

    @DisplayName("LocalTimeRange 에 포함된 시간이면 true 를 반환 한다 - 시작 시간 와 종료 시간 포함")
    @Test
    void when_RangeContainsTime_expect_true() throws Exception {
        // given
        LocalTime start = LocalTime.of(12, 0, 0);
        LocalTime end = LocalTime.of(13, 0, 0);

        LocalTimeRange range = LocalTimeRange.of(start, end);

        // when

        // then
        assertThat(range.contains(LocalTime.of(12, 0, 0))).isTrue();
        assertThat(range.contains(LocalTime.of(13, 0, 0))).isTrue();
    }

    @DisplayName("LocalTimeRange 에 포함된 시간이 아니면 false 를 반환 한다")
    @Test
    void when_RangeDoesNotContainsTime_expect_false() throws Exception {
        // given
        LocalTime start = LocalTime.of(12, 0, 0);
        LocalTime end = LocalTime.of(13, 0, 0);

        LocalTimeRange range = LocalTimeRange.of(start, end);

        // when

        // then
        assertThat(range.contains(LocalTime.of(11, 59, 59))).isFalse();
        assertThat(range.contains(LocalTime.of(13, 0, 0, 1))).isFalse();
    }

    @DisplayName("LocalTimeRange 의 기간을 구한다")
    @Test
    void when_RangeDifference_expect_Duration() throws Exception {
        // given
        LocalTime start = LocalTime.of(12, 0, 0);
        LocalTime end = LocalTime.of(13, 0, 0);
        LocalTimeRange range = LocalTimeRange.of(start, end);

        // when
        Duration duration = range.difference();

        // then
        assertThat(duration).isEqualTo(Duration.ofHours(1));
    }

}