package com.example.demo.workflow;

import com.example.demo.activity.Activity;
import com.example.demo.activity.ActivityResult;
import com.example.demo.context.Context;
import com.example.demo.context.StandardContext;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public abstract class AbstractWorkflow implements Workflow {

    private Context context;

    @NonNull
    private String name;
    private List<Activity> activities;

    public AbstractWorkflow(String workflowName, Map<String, Object> parameters) {
        this.name = workflowName;
        this.context = new StandardContext(parameters);
    }

    public List<Activity> getActivities() {

        if (this.activities == null || this.activities.isEmpty()) {
            log.error("There is no defined action for {}", this.name);
            throw new IllegalArgumentException(
                    "There is no defined action for {}" + this.name
            );
        }
        return this.activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public ActivityResult doWorkflow() {
        UUID uuid = UUID.randomUUID();
        String workflowId = "Id-" + uuid;
        log.info(">>{}==[{}]", "Started Workflow", workflowId);

        // Stop Watch Start..
        StopWatch stopWatch = new StopWatch(workflowId);
        // Step 1. Job id 를 만들어서 부여 한다.
        boolean isSuccess = false;
        // stopWatch.start();
        for (int i = 0; i < this.activities.size(); i++) {

            try {
                // Step 2. Start Time Check.
                stopWatch.start("taskId=" + i);
                log.info("ㅁㅁㅁ {} is working.", this.activities.get(i).getName());
                this.activities.get(i).doActivity(this.context);
                Thread.sleep(100);

                stopWatch.stop();
                // Step 3. End Time Check.
                isSuccess = true;
            } catch (IllegalAccessError ex) {
                isSuccess = false;
                log.error(ex.getLocalizedMessage());
            } catch (InterruptedException e) {
                log.error(e.getLocalizedMessage());
            }
        }

        log.info(">>{}==[{}]", "Ended Workflow", workflowId);
        log.info("{}", stopWatch.shortSummary());
        return new ActivityResult(isSuccess, "SUCCESS");
    }
}
