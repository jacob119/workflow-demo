package com.example.demo;

import com.example.demo.activity.Test;
import com.example.demo.context.Context;
import com.example.demo.context.StandardContext;
import com.example.demo.service.AsyncService;
import com.example.demo.service.FTPService;
import com.example.demo.workflow.StandardWorkflow;
import com.example.demo.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
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

import java.util.HashMap;
import java.util.Map;


@Slf4j
@ComponentScan("com.example.demo.*")
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
    private ApplicationContext context;

    public static void main(String[] args) {

        log.info("Staring...");
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        Test test = context.getBean(Test.class);
        System.out.println(test.hashCode());

        Test test1 = context.getBean(Test.class);
        System.out.println(test1.hashCode());
        while (true) {
            try {
                Thread.sleep(fixedDelay);
                //ftpService.open();


                for (int i = 0; i < 10; i++) {
                    Map<String, Object> parameters1 = new HashMap<>();
                    parameters1.put("id", 10);
                    parameters1.put("test", 40);

                    Context ct  = new StandardContext(parameters1);
                    Workflow workflow = context.getBean(StandardWorkflow.class);
                    workflow.setName("workflow:" + i );
                    workflow.setContext(ct);
                    asyncService.doAsync(workflow);

                }
            } catch (Exception ex) {
                log.error(ex.getLocalizedMessage());
            }

        }
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
