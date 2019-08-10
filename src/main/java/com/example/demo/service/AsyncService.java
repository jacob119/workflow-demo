package com.example.demo.service;

import com.example.demo.job.Job;
import com.example.demo.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncService {


    //  @Async
    public void doAsync(Workflow workflow) {
        try {
            Thread.sleep(500);
            workflow.start(new Job() {

            });
            // log.info("doAsync...........{}", workflow.doWorkflow());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
