package io.glory.coreweb;

import static io.glory.coreweb.IpAddrUtil.getClientIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IpAddrUtilTest {

    @DisplayName("서버 IP 를 반환 한다")
    @Test
    void get_server_ip() throws Exception {
        // given

        // when
        String serverIP = IpAddrUtil.getServerIP();

        // then
        System.out.println("==> serverIP = " + serverIP);
        assertThat(serverIP).isNotNull().isNotEmpty();
    }

    @DisplayName("서버 IP 의 마지막 octet 를 반환 한다")
    @Test
    void get_server_ip_last() throws Exception {
        // given

        // when
        String ipLastOctet = IpAddrUtil.getServerIpLastOctet();

        // then
        System.out.println("==> ipLastOctet = " + ipLastOctet);
        assertThat(ipLastOctet).isNotNull().isNotEmpty();
    }

    @DisplayName("동일한 서버 IP 를 반환 한다")
    @Test
    void get_server_ip_consistency() throws Exception {
        // given

        // when
        String serverIP1 = IpAddrUtil.getServerIP();
        String serverIP2 = IpAddrUtil.getServerIP();

        // then
        assertThat(serverIP1).isEqualTo(serverIP2);
    }

}