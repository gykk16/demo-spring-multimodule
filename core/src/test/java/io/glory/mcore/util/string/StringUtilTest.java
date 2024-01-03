package io.glory.mcore.util.string;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringUtilTest {

    @DisplayName("문자열 길이를 초과할 경우 지정된 길이만큼 잘라서 반환 한다")
    @Test
    void when_substringN_expect_DesignatedLength() throws Exception {
        // given
        String str = "1234567890";
        int expectedLength = 5;

        // when
        String result = StringUtil.substringN(str, expectedLength);

        // then
        assertThat(result)
                .isEqualTo("12345")
                .hasSize(expectedLength);
    }

    @DisplayName("null 을 전달할 경우 null 반환 한다")
    @Test
    void when_substringNWithNull_expect_Null() throws Exception {
        // given
        String str = null;
        int expectedLength = 5;

        // when
        String result = StringUtil.substringN(str, expectedLength);

        // then
        assertThat(result).isNull();
    }

    @DisplayName("문자열이 null 또는 공백이 아닐 경우 기존 문자열을 반환 한다")
    @Test
    void when_defaultIfBlank_expect_String() throws Exception {
        // given
        String expected = "string";

        // when
        String result1 = StringUtil.defaultIfBlank(expected);
        String result2 = StringUtil.defaultIfBlank(expected, "default");

        // then
        assertThat(result1).isEqualTo(expected);
        assertThat(result2).isEqualTo(expected);
    }

    @DisplayName("문자열이 null 또는 공백일 경우 지정된 문자열을 반환 한다")
    @Test
    void when_defaultIfBlank_expect_DefaultString() throws Exception {
        // given
        String defaultString = "default";

        // when
        String nullResult = StringUtil.defaultIfBlank(null, defaultString);
        String blankResult = StringUtil.defaultIfBlank(" ", defaultString);

        // then
        assertThat(nullResult).isEqualTo(defaultString);
        assertThat(blankResult).isEqualTo(defaultString);
    }

    @DisplayName("문자열이 null 또는 공백일 경우 '' 문자열을 반환 한다")
    @Test
    void when_defaultIfBlank_expect_Blank() throws Exception {
        // given
        String expected = "";

        // when
        String nullResult = StringUtil.defaultIfBlank(null);
        String blankResult = StringUtil.defaultIfBlank(" ");

        // then
        assertThat(nullResult).isEqualTo(expected);
        assertThat(blankResult).isEqualTo(expected);
    }

    @DisplayName("문자열이 공백(space) 있으면 true 를 반환 한다")
    @Test
    void when_isBlankHasWhitespace_expect_True() throws Exception {
        // given
        String str = " ";

        // when
        boolean result = StringUtil.isBlank(str);

        // then
        assertThat(result).isTrue();
    }

}