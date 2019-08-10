package com.example.demo.schedule;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

@Component
public class SchedulerManager {

    //   private BlockingQueue<DynamicScheduler> dynamicSchedulers;
//    dynamicSchedulers = new LinkedBlockingDeque<>();

    private List<DynamicScheduler> dynamicSchedulers;

    public SchedulerManager() {
        dynamicSchedulers = new CopyOnWriteArrayList<>();
    }

    public void add(DynamicScheduler dynamicScheduler) {
        this.dynamicSchedulers.add(dynamicScheduler);
    }

    public List<String> getList() {
        return this.dynamicSchedulers.stream().map(s -> s.getScheduler().getThreadNamePrefix()).collect(Collectors.toList());
    }

    public DynamicScheduler getDynamicScheduler(String name) {
        return this.dynamicSchedulers.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
    }

    public void startAll() {
        for (DynamicScheduler dynamicScheduler : dynamicSchedulers) {
            dynamicScheduler.start();
        }
    }

    public void stopAll() {
        for (DynamicScheduler dynamicScheduler : dynamicSchedulers) {
            dynamicScheduler.stop();
        }
    }
}
