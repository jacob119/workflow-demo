package com.example.demo.workflow;

import com.example.demo.job.Job;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class DefaultWorkflowManager implements WorkflowManager {
    private List<Workflow> workflowList;

    public DefaultWorkflowManager() {
        this.workflowList = new ArrayList<>();
    }

    public void add(Workflow workflow) {
        this.workflowList.add(workflow);
    }

    public void startAll(Job job) {
        for (Workflow workflow : this.workflowList) {
            workflow.start(job);
        }
    }

    public void stopAll() {
        for (Workflow workflow : this.workflowList) {
            workflow.stop();
        }
    }
}
