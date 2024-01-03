package io.glory.mcore.util.idgenerator.tsid;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TsidFastTest {

    private static final Logger LOGGER = Logger.getLogger("Tsid256GeneratorTest");

    @Disabled("heavy test")
    @DisplayName("Tsid Fast 테스트 - 동시 요청자 수: 256, 1024, 4096, 100000, 500000 일 때")
    @ParameterizedTest
    @ValueSource(ints = {256, 256, 1024, 4096, 100000, 500000})
    void tsid_fast(int numberOfUsers) throws Exception {
        // given
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch endLatch = new CountDownLatch(numberOfUsers);

        // when
        long[][] tsids = new long[numberOfUsers][1];

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfUsers; i++) {

            int finalI = i;
            executorService.execute(() -> {
                try {
                    Tsid tsid = Tsid.fast();
                    tsids[finalI][0] = tsid.toLong();

                } finally {
                    endLatch.countDown();
                }
            });
        }
        endLatch.await();

        long endTime = System.currentTimeMillis();

        Set<Long> set = new HashSet<>();
        for (long[] ids : tsids) {
            for (long id : ids) {
                set.add(id);
            }
        }

        // then
        LOGGER.info(() -> "[동시 요청자 수: " + numberOfUsers + "명] " + (endTime - startTime) + "ms");
        executorService.shutdownNow();

        LOGGER.info("==> set.size() = " + set.size());
        assertThat(set).hasSize(numberOfUsers);
    }

    @Disabled("heavy test")
    @DisplayName("Tsid Fast 테스트 - 동시 요청자 수: 256, 1024, 4096, 100000, 500000 일 때")
    @ParameterizedTest
    @ValueSource(ints = {256, 256, 1024, 4096, 100000, 500000})
    void tsid_fast_thread_pool(int numberOfUsers) throws Exception {
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
                        Tsid tsid = Tsid.fast();
                        tsids[threadI][j] = tsid.toLong();
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