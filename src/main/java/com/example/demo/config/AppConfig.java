package com.example.demo.config;

import com.example.demo.activity.Test;
import com.example.demo.workflow.StandardWorkflow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public Test test(){
        return new Test();
    }

    @Bean
    @Scope("prototype")
    public StandardWorkflow standardWorkflow(){
        return new StandardWorkflow();
    }
}
