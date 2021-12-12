package tests;

import java.time.Duration;
import java.time.Instant;

public class TestTimer {

    private final Instant startTime;
    private long testingTimeMillis;

    public TestTimer() {
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
