package io.glory.mcore.util.bytes;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ByteUtilEUCKRTest {

    private static final String EUC_KR = "EUC-KR";

    @DisplayName("ByteBuffer 에서 EUC-KR 문자열을 읽어 온다")
    @Test
    void readBytesEUCKR() throws Exception {
        // given
        String testString = "테스트";
        byte[] eucKrBytes = testString.getBytes(EUC_KR);
        ByteBuffer buffer = ByteBuffer.wrap(eucKrBytes);

        // when
        String result = ByteUtilEUCKR.readBytesEUCKR(buffer, eucKrBytes.length);

        // then
        assertThat(result).isEqualTo(testString);
    }

    @DisplayName("byte 를 신규 문자열로 변환 한다")
    @Test
    void toNewStringEUCKR() throws Exception {
        // given
        String testString = "테스트";
        byte[] eucKrBytes = testString.getBytes(EUC_KR);

        // when
        String result = ByteUtilEUCKR.toNewStringEUCKR(eucKrBytes);

        // then
        assertThat(result).isEqualTo(testString).isNotSameAs(testString);
    }

    @DisplayName("문자열을 ByteBuffer 로 변환 한다")
    @Test
    void toByteBufferEUCKR() throws Exception {
        // given
        String testString = "테스트";
        byte[] bytes = testString.getBytes(EUC_KR);

        // when
        ByteBuffer buffer = ByteUtilEUCKR.toByteBufferEUCKR(testString);

        // then
        assertThat(buffer.array()).isEqualTo(bytes);
    }

    @DisplayName("문자열을 byte array 로 변환 한다")
    @Test
    void toBytesEUCKR() throws Exception {
        // given
        String testString = "테스트";
        int length = 10;
        byte[] expected = {-59, -41, -67, -70, -58, -82, 32, 32, 32, 32};

        // when
        byte[] result = ByteUtilEUCKR.toBytesEUCKR(testString, length);

        // then
        assertThat(result).hasSize(length).isEqualTo(expected);
    }

}