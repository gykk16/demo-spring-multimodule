package io.glory.testsupport.integrated;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 통합 테스트 지원 클래스
 * <p>
 * `local` 프로파일을 사용 한다.
 */
@SpringBootTest
@ActiveProfiles({"local"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class IntegratedLocalTestSupport {

    protected ObjectMapper objectMapper = new ObjectMapper();

}
