package com.example.demo.workflow;

import com.example.demo.activity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StandardWorkflow extends AbstractWorkflow {

    public StandardWorkflow(String workflowName, Map<String, Object> parameters) {
        super(workflowName, parameters);
        generatedActivity();
    }

    public void generatedActivity() {
        List<Activity> activities = new ArrayList<>();

        activities.add(new NothingActivity("file"));
        activities.add(new PollerActivity("poller"));
        activities.add(new ParserActivity("paser"));
        activities.add(new SaveActivity("save"));

        // You have to define the lists of activities from some repositories( file or db ).
        this.setActivities(activities);
    }


}
