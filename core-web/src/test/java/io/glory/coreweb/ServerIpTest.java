package io.glory.coreweb;

import static io.glory.testsupport.TestTimeRunner.timeTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetAddress;

import io.glory.testsupport.integrated.IntegratedTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServerIpTest extends IntegratedTestSupport {

    @DisplayName("서버 ip 반환 성능 테스트")
    @Test
    void server_ip_time() throws Exception {
        // given
        final int LOOP_COUNT = 1_000_000;

        // when
        long staticCall = timeTest(LOOP_COUNT, "IpAddrUtil::getServerIp", IpAddrUtil::getServerIP);
        long repeatedCall = timeTest(LOOP_COUNT, "InetAddress.getLocalHost().getHostAddress()",
                () -> InetAddress.getLocalHost().getHostAddress());

        // then
        assertThat(staticCall).isLessThan(repeatedCall);
    }

}
