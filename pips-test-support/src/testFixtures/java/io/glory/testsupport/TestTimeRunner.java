package io.glory.testsupport;

import java.util.concurrent.Callable;

public class TestTimeRunner {

    private TestTimeRunner() {
    }

    /**
     * 테스트 시간 측정
     *
     * @param loopCount 반복 횟수
     * @param testName  테스트 이름
     * @param callable  테스트 코드
     * @return 테스트 시간
     */
    public static long timeTest(int loopCount, String testName, Callable<String> callable) throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < loopCount; i++) {
            callable.call();
        }
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        System.out.println("==> " + testName + " , elapsed = " + elapsedTime + " ms");
        return elapsedTime;
    }

}
