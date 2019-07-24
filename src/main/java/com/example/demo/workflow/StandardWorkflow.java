package com.example.demo.workflow;

import com.example.demo.activity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
public class StandardWorkflow extends AbstractWorkflow {

    @Autowired
    private ApplicationContext context;

    public StandardWorkflow(){

    }

    public StandardWorkflow(String workflowName, Map<String, Object> parameters) {
        super(workflowName, parameters);
    }

    public void startWorkflow() {

        List<Activity> activities = new ArrayList<>();

        HDFSActivity hdfsActivity = context.getBean(HDFSActivity.class);

        log.info("HDFS Activity=[{}]", hdfsActivity.hashCode());

        activities.add(new NothingActivity("file"));
        activities.add(new PollerActivity("poller"));
        activities.add(new ParserActivity("parser"));
        activities.add(new SaveActivity("save"));
        activities.add(hdfsActivity);


        // You have to define the lists of activities from some repositories( file or db ).
        super.setActivities(activities);

        super.doWorkflow();
    }
}
