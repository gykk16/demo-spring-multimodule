package io.glory.mcore.util.bytes;

import java.nio.ByteBuffer;

public class ByteUtilEUCKR extends ByteUtil {

    private static final String EUC_KR = "EUC-KR";

    private ByteUtilEUCKR() {
        super();
    }

    /**
     * ByteBuffer 에서 EUC-KR 문자열을 읽어 온다
     *
     * @param buffer ByteBuffer
     * @param length 읽어 올 바이트 길이
     * @return 읽어 온 문자열
     */
    public static String readBytesEUCKR(ByteBuffer buffer, int length) {
        return readBytesEUCKR(buffer, length, true);
    }

    /**
     * ByteBuffer 에서 EUC-KR 문자열을 읽어 온다
     *
     * @param buffer ByteBuffer
     * @param length 읽어 올 바이트 길이
     * @param strip  문자열 앞뒤 공백 제거 여부
     * @return 읽어 온 문자열
     */
    public static String readBytesEUCKR(ByteBuffer buffer, int length, boolean strip) {
        return readBytes(buffer, length, EUC_KR, strip);
    }

    /**
     * byte array 를 EUC-KR 문자열로 변환 한다
     *
     * @param bytes byte array
     * @return 변환된 문자열
     */
    public static String toNewStringEUCKR(byte[] bytes) {
        return toNewString(bytes, EUC_KR);
    }

    /**
     * 문자열을 EUC-KR ByteBuffer 로 변환 한다
     *
     * @param input 문자열
     * @return 변환된 ByteBuffer
     */
    public static ByteBuffer toByteBufferEUCKR(String input) {
        return toByteBuffer(input, EUC_KR);
    }

    /**
     * int 를 EUC-KR byte array 로 변환 한다
     *
     * @param input  int
     * @param length byte array 의 길이
     * @return 변환된 byte array
     */
    public static byte[] toBytesEUCKR(int input, int length) {
        return toBytesEUCKR(String.valueOf(input), length);
    }

    /**
     * 문자열을 EUC-KR byte array 로 변환 한다
     *
     * @param input  문자열
     * @param length byte array 의 길이
     * @return 변환된 byte array
     */
    public static byte[] toBytesEUCKR(String input, int length) {
        return toBytes(input, EUC_KR, length);
    }

}
