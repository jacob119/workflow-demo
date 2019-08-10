package com.example.demo.job;

import com.example.demo.schedule.SchedulerActor;

public interface JobManager extends SchedulerActor {
    void add(Job job);
}
