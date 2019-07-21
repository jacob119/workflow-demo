package com.example.demo.workflow;

import com.example.demo.activity.Activity;
import com.example.demo.activity.ActivityResult;
import com.example.demo.context.Context;
import java.util.List;

public interface Workflow {
    ActivityResult doWorkflow();

    void setName(String name);

    String getName();

    void setActivities(List<Activity> activities);

    List<Activity> getActivities();


    void setContext(Context context);

    Context getContext();
}
