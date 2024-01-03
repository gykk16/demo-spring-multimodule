package io.glory.module.socket.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Fast Socket Client
 * <p>
 * Socket Server 에 메시지를 전송하고 응답 메시지를 받는다
 * </p>
 * <p>
 * {@link SocketClient} 를 사용하면서 {@link AutoCloseable} 을 구현하지 않아도 된다
 */
public class FastSocketClient {

    private static final int DEFAULT_CONNECT_TIMEOUT = 3_000;
    private static final int DEFAULT_READ_TIMEOUT    = 5_000;

    private FastSocketClient() {
    }

    /**
     * 소켓 통신 메시지 전송
     * <ul>
     * <li>기본 연결 타임 아웃 3초, 읽기 타임 아웃 5초</li>
     * <li>기본 인코딩 UTF-8</li>
     * </ul>
     *
     * @param ip      호스트 IP
     * @param port    포트
     * @param message 전송 메시지
     * @return 수신 메시지
     */
    public static String sendMessage(String ip, int port, String message) throws IOException {
        return sendMessage(ip, port, message, StandardCharsets.UTF_8.name());
    }

    /**
     * 소켓 통신 메시지 전송
     *
     * @param ip      호스트 IP
     * @param port    포트
     * @param message 전송 메시지
     * @param charset 인코딩 문자셋
     * @return 수신 메시지
     */
    public static String sendMessage(String ip, int port, String message, String charset)
            throws IOException {
        return sendMessage(ip, port, message, charset, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }


    /**
     * 소켓 통신 메시지 전송
     *
     * @param ip             호스트 IP
     * @param port           포트
     * @param message        전송 메시지
     * @param charset        인코딩 문자셋
     * @param connectTimeout 연결 타임 아웃
     * @param readTimeout    읽기 타임 아웃
     * @return 수신 메시지
     */
    public static String sendMessage(String ip, int port, String message, String charset,
            int connectTimeout, int readTimeout) throws IOException {

        try (SocketClient client = new SocketClient(connectTimeout, readTimeout)) {
            client.connect(ip, port);
            return client.sendMessage(message, charset);
        }
    }

}
