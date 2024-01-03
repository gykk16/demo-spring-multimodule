package io.glory.mcore.util.bytes;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteUtil {

    private static final String DEFAULT_CHARSET = StandardCharsets.UTF_8.name();

    protected ByteUtil() {
    }

    /**
     * ByteBuffer 에서 문자열을 읽어 온다
     *
     * @param buffer ByteBuffer
     * @param length 읽어 올 바이트 길이
     * @return 읽어 온 문자열
     */
    public static String readBytes(ByteBuffer buffer, int length) {
        return readBytes(buffer, length, DEFAULT_CHARSET);
    }

    /**
     * ByteBuffer 에서 문자열을 읽어 온다
     *
     * @param buffer  ByteBuffer
     * @param length  읽어 올 바이트 길이
     * @param charset charset
     * @return 읽어 온 문자열
     */
    public static String readBytes(ByteBuffer buffer, int length, String charset) {
        return readBytes(buffer, length, charset, true);
    }

    /**
     * ByteBuffer 에서 문자열을 읽어 온다
     *
     * @param buffer  ByteBuffer
     * @param length  읽어 올 바이트 길이
     * @param charset charset
     * @param strip   문자열 앞뒤 공백 제거 여부
     * @return 읽어 온 문자열
     */
    public static String readBytes(ByteBuffer buffer, int length, String charset, boolean strip) {
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        if (strip) {
            return toNewString(bytes, charset).strip();
        }
        return toNewString(bytes, charset);
    }

    /**
     * byte array 를 charset 에 맞게 문자열로 변환 한다
     *
     * @param bytes   byte array
     * @param charset charset
     * @return 변환된 문자열
     */
    public static String toNewString(byte[] bytes, String charset) {
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 문자열을 charset 에 맞게 ByteBuffer 로 변환 한다
     *
     * @param input   문자열
     * @param charset charset
     * @return 변환된 ByteBuffer
     */
    public static ByteBuffer toByteBuffer(String input, String charset) {
        byte[] bytes = toBytes(input, charset);
        return ByteBuffer.wrap(bytes);
    }

    /**
     * 문자열을 charset 에 맞게 byte array 로 변환 한다
     * <ul>
     *     <li>byte array 가 length 보다 긴 경우, length 만큼만 잘라서 반환 한다</li>
     *     <li>byte array 가 length 보다 짧은 경우, length 만큼 우측 공백 패딩 후 byte array 로 변환 한다</li>
     *     <li>byte array 가 length 와 같은 경우, 그대로 반환 한다</li>
     * </ul>
     *
     * @param input   문자열
     * @param charset charset
     * @param length  byte array 의 길이
     * @return 변환된 byte array
     */
    public static byte[] toBytes(String input, int length) {
        return toBytes(input, DEFAULT_CHARSET, length, ' ');
    }

    /**
     * 문자열을 charset 에 맞게 byte array 로 변환 한다
     * <ul>
     *     <li>byte array 가 length 보다 긴 경우, length 만큼만 잘라서 반환 한다</li>
     *     <li>byte array 가 length 보다 짧은 경우, length 만큼 우측 공백 패딩 후 byte array 로 변환 한다</li>
     *     <li>byte array 가 length 와 같은 경우, 그대로 반환 한다</li>
     * </ul>
     *
     * @param input   문자열
     * @param charset charset
     * @param length  byte array 의 길이
     * @return 변환된 byte array
     */
    public static byte[] toBytes(String input, String charset, int length) {
        return toBytes(input, charset, length, ' ');
    }

    /**
     * 문자열을 charset 에 맞게 byte array 로 변환 한다
     * <ul>
     *     <li>byte array 가 length 보다 긴 경우, length 만큼만 잘라서 반환 한다</li>
     *     <li>byte array 가 length 보다 짧은 경우, length 만큼 우측 공백 패딩 후 byte array 로 변환 한다</li>
     *     <li>byte array 가 length 와 같은 경우, 그대로 반환 한다</li>
     * </ul>
     *
     * @param input   문자열
     * @param charset charset
     * @param length  byte array 의 길이
     * @param padChar 패딩할 문자
     * @return 변환된 byte array
     */
    public static byte[] toBytes(String input, String charset, int length, char padChar) {
        return toBytes(input, charset, length, padChar, false);
    }

    /**
     * 문자열을 charset 에 맞게 byte array 로 변환 한다
     * <ul>
     *     <li>byte array 가 length 보다 긴 경우, length 만큼만 잘라서 반환 한다</li>
     *     <li>byte array 가 length 보다 짧은 경우, length 만큼 우측 공백 패딩 후 byte array 로 변환 한다</li>
     *     <li>byte array 가 length 와 같은 경우, 그대로 반환 한다</li>
     * </ul>
     *
     * @param input       문자열
     * @param charset     charset
     * @param length      byte array 의 길이
     * @param padChar     패딩할 문자
     * @param leftPadding 왼쪽 패딩 여부
     * @return 변환된 byte array
     */
    public static byte[] toBytes(String input, String charset, int length, char padChar, boolean leftPadding) {
        byte[] bytes = toBytes(input, charset);

        // Trim the byte array if it's longer than the specified length
        if (bytes.length > length) {
            return Arrays.copyOf(bytes, length);
        }

        // Create a buffer of the specified length
        ByteBuffer buffer = ByteBuffer.allocate(length);

        // Calculate the number of padding characters needed
        int paddingLength = length - bytes.length;

        // If left padding is required, add padding characters before the string
        if (leftPadding) {
            for (int i = 0; i < paddingLength; i++) {
                buffer.put((byte)padChar);
            }
            buffer.put(bytes);
        }
        // If right padding is required, add the string first then padding characters
        else {
            buffer.put(bytes);
            for (int i = 0; i < paddingLength; i++) {
                buffer.put((byte)padChar);
            }
        }

        return buffer.array();
    }

    /**
     * 문자열을 charset 에 맞게 byte array 로 변환 한다
     *
     * @param input   변환할 문자열
     * @param charset 변환할 charset
     * @return 변환된 byte array
     */
    public static byte[] toBytes(String input, String charset) {
        try {
            return input.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * byte array 를 합친다
     *
     * @param arrays byte array ...
     * @return 합쳐진 byte array
     */
    public static byte[] concat(byte[]... arrays) {
        int length = 0;
        for (byte[] array : arrays) {
            length += array.length;
        }

        byte[] result = new byte[length];
        int offset = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }

}
