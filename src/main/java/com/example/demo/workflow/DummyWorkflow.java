package com.example.demo.workflow;

import com.example.demo.activity.*;
import com.example.demo.job.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class DummyWorkflow extends AbstractWorkflow implements ApplicationContextInitializer {

    @Autowired
    private ApplicationContext context;
    private List<Activity> activities;

    public DummyWorkflow() {
    }

    @Override
    public ActivityResult start(Job job) {

        log.info("DummyWorkflow-{}", job.getId());

        return super.start();
    }


    @Override
    public void stop() {

    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("SS");
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
