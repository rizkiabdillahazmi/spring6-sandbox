package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by Rizki Abdillah Azmi on 23-Sep-23
 */
public class ThreadUtil {
    private ThreadUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger logger = LoggerFactory.getLogger(ThreadUtil.class);
    private static final int POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;

    public static Scheduler customElastic() {
        return Schedulers.newBoundedElastic(POOL_SIZE, MAX_POOL_SIZE, "customElastic");
    }

    public static void printThreads() {
        var header = String.format("|%-50s|%-30s|%-10s|%-15s|%-10s|",
                "Name", "Group", "Priority", "State", "Type").toUpperCase();
        var separator = "+".repeat(header.length());

        var threadsInfo = Thread.getAllStackTraces()
                .keySet()
                .stream()
                .sorted(Comparator.comparing(Thread::getName))
                .map(thread -> String.format("|%-50s|%-30s|%-10d|%-15s|%-10s|",
                        thread.getName(),
                        thread.getThreadGroup().getName(),
                        thread.getPriority(),
                        thread.getState(),
                        thread.isDaemon() ? "Daemon" : "User"))
                .collect(Collectors.joining("\n"));

        var table = "Thread Table :"
                + "\n"
                + separator
                + "\n"
                + header
                + "\n"
                + separator
                + "\n"
                + threadsInfo
                + "\n"
                + separator;

        logger.info(table);
    }
}
