package io.glory.module.socket.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Socket Client
 * <p>
 * Socket Server 에 메시지를 전송하고 응답 메시지를 받는다
 * </p>
 */
public class SocketClient implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(SocketClient.class);

    private static final int DEFAULT_CONNECT_TIMEOUT = 3_000;
    private static final int DEFAULT_READ_TIMEOUT    = 5_000;

    private final int    connectTimeout;
    private final int    readTimeout;
    private final Socket socket;

    private BufferedOutputStream out;
    private BufferedInputStream  in;

    /**
     * SocketClient 생성
     */
    public SocketClient() {
        this(DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * SocketClient 생성
     *
     * @param connectTimeout 연결 타임 아웃
     * @param readTimeout    읽기 타임 아웃
     */
    public SocketClient(int connectTimeout, int readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.socket = new Socket();
    }

    /**
     * 서버에 연결
     *
     * @param ip   서버 IP
     * @param port 서버 포트
     */
    public void connect(String ip, int port) throws IOException {
        try {
            socket.connect(new InetSocketAddress(ip, port), connectTimeout);
            socket.setSoTimeout(readTimeout);
            out = new BufferedOutputStream(socket.getOutputStream());
            in = new BufferedInputStream(socket.getInputStream());
        } catch (IOException e) {
            log.error("# Failed to connect to the server [{}:{}] - {}", ip, port, e.getMessage());
            throw e;
        }
        log.info("# Connected to the server [{}:{}]", ip, port);
    }

    /**
     * 서버 연결 해제
     */
    public void disconnect() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }
            log.info("# Disconnected from the server");
        } catch (IOException e) {
            log.error("# Failed to disconnect from the server", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 서버에 메시지 전송
     * <p>
     * 기본 문자셋은 UTF-8
     *
     * @param message 메시지
     * @return 서버로부터 받은 응답 메시지
     */
    public String sendMessage(String message) throws IOException {
        return sendMessage(message, StandardCharsets.UTF_8.name());
    }

    /**
     * 서버에 메시지 전송
     *
     * @param message 메시지
     * @param charset 문자셋
     * @return 서버로부터 받은 응답 메시지
     */
    public String sendMessage(String message, String charset) throws IOException {
        return sendMessage(message, charset, 1024);
    }

    public String sendMessage(String message, String charset, int resSize) throws IOException {
        byte[] sendBytes = message.getBytes(charset);
        log.info("# Send     >>> [{}] {}bytes", message, sendBytes.length);

        out.write(sendBytes);
        out.flush();

        byte[] buffer = new byte[resSize];
        int read = in.read(buffer);
        if (read == -1) {
            throw new RuntimeException("# Failed to read from the server");
        }

        String received = new String(buffer, 0, read, charset);
        log.info("# Received <<< [{}] {}bytes", received, read);

        return received;
    }

    @Override
    public void close() {
        try {
            disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
