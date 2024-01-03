package io.glory.mcore.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomUtilTest {

    @DisplayName("0 또는 1 을 랜덤하게 생성 한다")
    @Test
    void when_getZeroOrOne_expect_ZeroOrOne() throws Exception {
        // given
        for (int i = 0; i < 5; i++) {

            // when
            int result = RandomUtil.getZeroOrOne();

            // then
            assertThat(result).isIn(0, 1);
        }
    }

    @DisplayName("0, 1 또는 2 를 랜덤하게 생성 한다")
    @Test
    void when_getZeroOrOneOrTwo_expect_ZeroOrOneOrTwo() throws Exception {
        // given
        for (int i = 0; i < 5; i++) {

            // when
            int result = RandomUtil.getZeroOrOneOrTwo();

            // then
            assertThat(result).isIn(0, 1, 2);
        }
    }

    @DisplayName("min 과 max 사이의 랜덤 숫자를 생성 한다")
    @Test
    void when_getRandomIntInRange_expect_RandomNumberBetweenMinAndMax() throws Exception {
        // given
        int min = 10;
        int max = 20;

        // when
        int result = RandomUtil.getRandomIntInRange(min, max);

        // then
        assertThat(result).isBetween(min, max);
    }

    @DisplayName("길이 n 의 랜덤 숫자를 생성 한다")
    @Test
    void when_getRandomIntOfLength_expect_RandomNumberOfLength() throws Exception {
        // given
        int length = 7;

        // when
        int randomIntOfLength = RandomUtil.getRandomIntOfLength(length);

        // then
        assertThat(String.valueOf(randomIntOfLength)).hasSize(length);
    }

}