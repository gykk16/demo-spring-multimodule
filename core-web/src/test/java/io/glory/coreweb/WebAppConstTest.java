package io.glory.coreweb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WebAppConstTest {

    @DisplayName("내부 IP 목록")
    @Test
    void internal_ips() throws Exception {
        assertThat(WebAppConst.INTERNAL_IPS)
                .containsExactlyInAnyOrder(
                        "127.0.0.1",
                        "0:0:0:0:0:0:0:1"
                );
    }

}