package tests;

import java.time.Duration;
import java.time.Instant;

public class AbstractTest {

    TestTimer tt;
    boolean result;

    protected void startTest() {
        result = true;
        tt = new TestTimer();
    }

    protected void completeTest() {
        tt.stop();
        String testName = Thread.currentThread().getStackTrace()[2].getMethodName();
        if (result) {
            System.out.printf("- TEST '%s' - PASSED (%s)\n", testName, tt);
        } else {
            System.err.printf("- TEST '%s' - FAILED (%s)\n", testName, tt);
        }
    }
}

class TestTimer {

    private final Instant startTime;
    private long testingTimeMillis;

    protected TestTimer() {
        this.startTime = Instant.now();
    }

    protected void stop() {
        Instant finish = Instant.now();
        testingTimeMillis = Duration.between(startTime, finish).toMillis();
    }

    @Override
    public String toString() {
        return testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";
    }
}