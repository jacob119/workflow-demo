package com.example.demo.workflow;

import com.example.demo.activity.Activity;
import com.example.demo.activity.ActivityResult;
import com.example.demo.context.Context;
import com.example.demo.job.Job;

import java.util.List;

public interface Workflow {

    void setName(String name);

    String getName();

    void add(Activity activities);

    List<Activity> getActivities();

    void setContext(Context context);

    Context getContext();

    ActivityResult start(Job job);

    void stop();
}
