package com.example.demo.config;

import com.example.demo.activity.*;
import com.example.demo.job.DefaultJobManager;
import com.example.demo.job.JobManager;
import com.example.demo.workflow.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public Test test(){
        return new Test();
    }

    @Bean
    @Qualifier("standardWorkflow")
    @Scope("prototype")
    public Workflow standardWorkflow() {
        return new StandardWorkflow();
    }


    @Bean
    @Qualifier("dummyWorkflow")
    @Scope("prototype")
    public Workflow DummyWorkflow() {
        return new DummyWorkflow();
    }

    @Bean
    public WorkflowManager DefaultWorkflowManager() {
        return new DefaultWorkflowManager();
    }

    @Bean
    public JobManager DefaultJobManager() {
        return new DefaultJobManager(DefaultWorkflowManager());
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(20);
        threadPoolTaskExecutor.setQueueCapacity(50);
        threadPoolTaskExecutor.setMaxPoolSize(30);
        return threadPoolTaskExecutor;
    }


}
