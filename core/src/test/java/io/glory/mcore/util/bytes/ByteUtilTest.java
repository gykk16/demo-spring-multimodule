package io.glory.mcore.util.bytes;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ByteUtilTest {

    @DisplayName("byte array 를 합친다")
    @Test
    void concat() throws Exception {
        // given
        byte[] bytes1 = {1, 2, 3};
        byte[] bytes2 = {4, 5, 6};
        byte[] bytes3 = {7, 8, 9};

        byte[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        // when
        byte[] result = ByteUtil.concat(bytes1, bytes2, bytes3);

        // then
        assertThat(result).hasSize(9).isEqualTo(expected);
    }

    @DisplayName("좌측 패딩을 한 byte array 를 반환 한다")
    @Test
    void when_toBytesWithLeftPadding_expect_LeftPadding() throws Exception {
        // given
        String text = "123";
        int length = 10;
        char paddingChar = '0';

        // when`
        byte[] bytes = ByteUtil.toBytes(text, StandardCharsets.UTF_8.name(), length, paddingChar, true);
        String newText = new String(bytes, StandardCharsets.UTF_8);

        // then
        assertThat(newText).isEqualTo("0000000123");
    }

    @DisplayName("우측 패딩을 한 byte array 를 반환 한다")
    @Test
    void when_toBytesWithRightPadding_expect_RightPadding() throws Exception {
        // given
        String text = "123";
        int length = 10;
        char paddingChar = '0';

        // when`
        byte[] bytes = ByteUtil.toBytes(text, StandardCharsets.UTF_8.name(), length, paddingChar, false);
        String newText = new String(bytes, StandardCharsets.UTF_8);

        // then
        assertThat(newText).isEqualTo("1230000000");
    }

}