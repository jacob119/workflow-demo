package com.example.demo.workflow;

import com.example.demo.activity.*;
import com.example.demo.job.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Component
@Scope(scopeName = "prototype")
public class DummyWorkflow extends AbstractWorkflow implements ApplicationContextInitializer {
    public DummyWorkflow() {
    }

    @Override
    public ActivityResult start(Job job) {

        //  log.info("DummyWorkflow-{}", job.getId());
        log.info("DummyWorkflow-{}", this.getName());

        return super.start();
    }


    @Override
    public void stop() {

    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("SS");
    }
}
