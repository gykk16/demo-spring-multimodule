package io.glory.mcore.util.idgenerator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TraceIdGeneratorTest {

    private static final Logger LOGGER = Logger.getLogger("TraceIdGeneratorTest");

    private final TraceIdGenerator traceIdGenerator = new TraceIdGenerator(1, 1);

    @DisplayName("Smilecon Tsid 파싱")
    @Test
    void parse() throws Exception {
        // given
        String[] ids = {
                "1173413936850677760",
                "1171993924705325056"
        };

        // when
        for (String id_ : ids) {
            long id = Long.parseLong(id_);
            long[] parsed = TraceIdGenerator.parse(id);
            LocalDateTime generatedAt = TraceIdGenerator.generatedAt(id);

            System.out.println("==> id = " + id + " , " + Arrays.toString(parsed) + " , generatedAt = " + generatedAt);
        }
    }

    @DisplayName("Smilecon Tsid 를 파싱하면 시간, 워커, 프로세스, 시퀀스가 나온다")
    @Test
    void when_parse_expect_TimestampWorkerProcessSequence() throws Exception {
        // given
        long id = 1170953214355443712L;
        long generatedId = traceIdGenerator.generate();

        // when
        long[] parsed1 = TraceIdGenerator.parse(id);
        long[] parsed2 = TraceIdGenerator.parse(generatedId);

        // then
        System.out.println("==>          id = " + id + ", parsed = " + Arrays.toString(parsed1));
        System.out.println("==> generatedId = " + generatedId + ", parsed = " + Arrays.toString(parsed2));

        assertThat(parsed1).hasSize(4);
        assertThat(parsed2).hasSize(4);
    }

    @Disabled("heavy test")
    @DisplayName("Smilecon Tsid Generator 32 thread 테스트 - 동시 요청자 수: 256, 1024, 4096, 100000, 500000 일 때")
    @ParameterizedTest
    @ValueSource(ints = {256, 256, 1024, 4096, 100000, 500000})
    void generate(int numberOfUsers) throws Exception {
        // given
        int threadPoolSize = 32;
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        CountDownLatch endLatch = new CountDownLatch(threadPoolSize);

        // when
        long[][] tsids = new long[threadPoolSize][numberOfUsers];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadPoolSize; i++) {

            int threadI = i;
            executorService.execute(() -> {
                try {
                    for (int j = 0; j < numberOfUsers; j++) {
                        tsids[threadI][j] = traceIdGenerator.generate();
                    }

                } finally {
                    endLatch.countDown();
                }
            });
        }
        endLatch.await();

        long endTime = System.currentTimeMillis();
        LOGGER.info(() -> "[동시 요청자 수: " + numberOfUsers + "명] " + (endTime - startTime) + "ms");

        Set<Long> set = new HashSet<>();
        for (long[] ids : tsids) {
            Arrays.stream(ids).forEach(set::add);
        }

        // then
        executorService.shutdownNow();

        LOGGER.info("==> set.size() = " + set.size());
        assertThat(set).hasSize(numberOfUsers * threadPoolSize);
    }

}