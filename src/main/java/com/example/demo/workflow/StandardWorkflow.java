package com.example.demo.workflow;

import com.example.demo.activity.*;
import com.example.demo.job.Job;
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

    @Override
    public ActivityResult start() {


        HDFSActivity hdfsActivity = context.getBean(HDFSActivity.class);

        log.info("HDFS Activity=[{}]", hdfsActivity.hashCode());

        super.add(new NothingActivity("file"));
        super.add(new PollerActivity("poller"));
        super.add(new ParserActivity("parser"));
        super.add(new SaveActivity("save"));
        super.add(hdfsActivity);


        // You have to define the lists of activities from some repositories( file or db ).

        return super.start();
    }

    @Override
    public ActivityResult start(Job job) {
        return null;
    }

    @Override
    public void stop() {

    }


}
