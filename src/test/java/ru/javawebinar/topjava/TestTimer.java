package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class TestTimer extends Stopwatch {
    private static final Logger logger = getLogger(TestTimer.class);

    private Map<String,Long> timeRecords = new HashMap<>();

    private long totalTime;


    public String getTimeRecords(){
        return timeRecords.entrySet().stream()
                .map(entry->String.format("Test %s: %d ms",entry.getKey(),entry.getValue()))
                .collect(Collectors.joining("\n")).concat("\nTotal time for all test: "+totalTime+" ms");
    }

    @Override
    protected void finished(long nanos, Description description) {
        String methodName = description.getMethodName();
        String className = description.getTestClass().getSimpleName();
        long time = TimeUnit.NANOSECONDS.toMicros(nanos);
        logger.info(String.format("Test %s %s, spent %d ms", methodName, "finished", time));
        timeRecords.put(className +"."+ methodName,time);
        totalTime+=time;
    }
}
