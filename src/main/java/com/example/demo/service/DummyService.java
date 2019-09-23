package com.example.demo.service;

import com.example.demo.Application;
import com.example.demo.activity.NothingActivity;
import com.example.demo.activity.ParserActivity;
import com.example.demo.activity.PollerActivity;
import com.example.demo.activity.SaveActivity;
import com.example.demo.job.DefaultJobManager;
import com.example.demo.job.JobManager;
import com.example.demo.schedule.DynamicScheduler;
import com.example.demo.schedule.SchedulerManager;
import com.example.demo.workflow.DummyWorkflow;
import com.example.demo.workflow.Workflow;
import com.example.demo.workflow.WorkflowManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DummyService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SchedulerManager schedulerManager;

    @Autowired
    private DummyWorkflow dummyWorkflow;

    public void add(String name){
       // Workflow newWorkflow = context.getBean(DummyWorkflow.class);
        dummyWorkflow.setName(name);

        dummyWorkflow.add(new NothingActivity("file"));


        // Step 2. make workflow Manager.
        WorkflowManager newWorkflowManager = context.getBean(WorkflowManager.class);

        // Step 3. assign to workflow for working.
        newWorkflowManager.add(dummyWorkflow);

        // Step 4. workflow Manager into JobManager.
        JobManager defaultJobManager = context.getBean(JobManager.class);

        // Step 5. Scheduling.
        DynamicScheduler aa = new DynamicScheduler(name, 2, new DefaultJobManager(newWorkflowManager));
        schedulerManager.add(aa);
    }
}
