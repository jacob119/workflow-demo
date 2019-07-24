package com.example.demo;

import com.example.demo.activity.Test;
import com.example.demo.context.Context;
import com.example.demo.context.StandardContext;
import com.example.demo.listener.FileListener;
import com.example.demo.service.AsyncService;
import com.example.demo.service.FTPService;
import com.example.demo.service.FileListenerService;
import com.example.demo.workflow.StandardWorkflow;
import com.example.demo.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@SpringBootApplication
@EnableAsync
public class Application implements ApplicationRunner {

    @Value("${ftp.server.fixedDelay}")
    private int fixedDelay = 1000;

    @Autowired
    private FTPService ftpService;

    @Autowired
    private AsyncService asyncService;


    @Autowired
    private FileListenerService fileListenerService;

    @Autowired
    private ApplicationContext context;


    private int jobCount = 2;

    public static void main(String[] args) {

        log.info("Staring...");
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Assert.isNull(ftpService, "fptService is null ??");

//        Test test = context.getBean(Test.class);
//        System.out.println(test.hashCode());
//
//        Test test1 = context.getBean(Test.class);
//        System.out.println(test1.hashCode());

        // Step 1. File Monitoring on the specific directory.
        //fileListenerService.start();

        // Step 2. Pulling some to do.
        startWorkflow();

//
//        while (true) {
//            Thread.sleep(fixedDelay);
//
//
//        }
    }

    public void startWorkflow() {
        try {

            for (int i = 0; i < jobCount; i++) {
                Map<String, Object> parameters1 = new HashMap<>();
                parameters1.put("id", 10);
                parameters1.put("test", 40);

                Context ct = new StandardContext(parameters1);
                Workflow workflow = context.getBean(StandardWorkflow.class);
                workflow.setName("workflow:" + i);
                workflow.setContext(ct);
                asyncService.doAsync(workflow);

            }


        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
    }
}
