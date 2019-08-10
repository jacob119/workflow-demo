package com.example.demo.schedule;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.concurrent.TimeUnit;

public class DynamicScheduler {
    private String name;
    private ThreadPoolTaskScheduler scheduler;
    private SchedulerActor schedulerActor;
    private long interval = 1;

    public DynamicScheduler(String name, long interval, SchedulerActor schedulerActor) {
        this.name = name;
        this.interval = interval;
        this.schedulerActor = schedulerActor;
    }

    public void stop() {
        scheduler.shutdown();
    }

    public void start() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        // 스케쥴러가 시작되는 부분
        scheduler.setThreadNamePrefix(this.name);
        scheduler.schedule(getRunnable(), getTrigger());
    }

    private Runnable getRunnable() {
        return () -> {
            // do something
            this.schedulerActor.doTask();
        };
    }

    private Trigger getTrigger() {
        // 작업 주기 설정
        return new PeriodicTrigger(this.interval, TimeUnit.SECONDS);
    }

    public ThreadPoolTaskScheduler getScheduler() {
        return scheduler;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public String getName() {
        return name;
    }
}
