package com.example.demo.workflow;

import com.example.demo.job.Job;

public interface WorkflowManager {
    void startAll(Job job);

    void stopAll();

    void add(Workflow workflow);
}
