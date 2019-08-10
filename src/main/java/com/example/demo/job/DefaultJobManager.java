package com.example.demo.job;

import com.example.demo.schedule.SchedulerActor;
import com.example.demo.workflow.DefaultWorkflowManager;
import com.example.demo.workflow.WorkflowManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class DefaultJobManager implements JobManager {
    private BlockingQueue<Job> jobList;
    private WorkflowManager workflowManager;

    public DefaultJobManager(WorkflowManager workflowManager) {
        this.workflowManager = workflowManager;
        this.jobList = new LinkedBlockingDeque<>();
    }

    @Override
    public void add(Job job) {
        if (!isExist(job.getId())) {
            this.jobList.add(job);
        }
    }

    public boolean isExist(String id) {
        return this.jobList.stream().anyMatch(s -> s.getId().equals(id));
    }

    public Job getJob(String id) {
        return this.jobList.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void doTask() {

        // Step 1. polling adaptive.
        //  Step 1-1. Job Creation.
        //  Step 1-2. Job Queueing.
        // jobPicker.Pick();

        // Step 2. starting workflow.
        workflowManager.startAll(jobList.peek());
    }
}
