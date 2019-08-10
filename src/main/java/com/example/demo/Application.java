package com.example.demo;

import com.example.demo.activity.*;
import com.example.demo.context.Context;
import com.example.demo.context.StandardContext;
import com.example.demo.job.JobManager;
import com.example.demo.schedule.DynamicScheduler;
import com.example.demo.schedule.SchedulerManager;
import com.example.demo.service.AsyncService;
import com.example.demo.service.FTPService;
import com.example.demo.service.FileListenerService;
import com.example.demo.workflow.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Application implements CommandLineRunner {

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

    @Autowired
    private SchedulerManager schedulerManager;



    private int jobCount = 1;

    public static void main(String[] args) {
        log.info("Starting...");
        SpringApplication.run(Application.class, args);
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


    // @Scheduled(fixedRateString = "5000", initialDelay = 3000)
    private void scheduleTest() {
        log.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Starting Workflow...");
//        startWorkflow();
    }

    @Override
    public void run(String... args) throws Exception {

        // Step 1. make workflow.
        Workflow dummyWorkflow = context.getBean("dummyWorkflow", DummyWorkflow.class);

        dummyWorkflow.add(new NothingActivity("file"));
        dummyWorkflow.add(new PollerActivity("poller"));
        dummyWorkflow.add(new ParserActivity("parser"));
        dummyWorkflow.add(new SaveActivity("save"));

        // Step 2. make workflow Manager.
        WorkflowManager defaultWorkflowManager = context.getBean(WorkflowManager.class);

        // Step 3. assign to workflow for working.
        defaultWorkflowManager.add(dummyWorkflow);

        // Step 4. workflow Manager into JobManager.
        JobManager defaultJobManager = context.getBean(JobManager.class);

        // Step 5. Scheduling.
        DynamicScheduler aa = new DynamicScheduler("etl", 2, defaultJobManager);

        DynamicScheduler bb = new DynamicScheduler("etl888", 10, defaultJobManager);

        schedulerManager.add(aa);
        schedulerManager.add(bb);

        schedulerManager.startAll();

//        DynamicScheduler aa = new DynamicScheduler("etl",2, );
//        schedulerManager.add();
//        DummyWorkflow dummyWorkflow = new DummyWorkflow();
//        StandardWorkflow standardWorkflow = new StandardWorkflow();
        //    DynamicScheduler aa = new DynamicScheduler("etl",2, dummyWorkflow);
        //    DynamicScheduler bb = new DynamicScheduler("etl2",10, standardWorkflow);

//        schedulerManager.add(aa);
//        schedulerManager.add(bb);
//
//        schedulerManager.startAll();
    }
}
