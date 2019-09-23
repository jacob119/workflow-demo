package com.example.demo.controller;

import com.example.demo.activity.NothingActivity;
import com.example.demo.activity.ParserActivity;
import com.example.demo.activity.PollerActivity;
import com.example.demo.activity.SaveActivity;
import com.example.demo.config.TCPInfo;
import com.example.demo.job.JobManager;
import com.example.demo.schedule.DynamicScheduler;
import com.example.demo.schedule.SchedulerManager;
import com.example.demo.service.DummyService;
import com.example.demo.workflow.DummyWorkflow;
import com.example.demo.workflow.Workflow;
import com.example.demo.workflow.WorkflowManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class SchedulerController {


    @Autowired
    private SchedulerManager schedulerManager;


    @Autowired
    private DummyService dummyService;

    @RequestMapping(value = "/scheduler/startAll", method = RequestMethod.GET)
    public String startAll() {
        schedulerManager.startAll();
        return "startAll";
    }

    @RequestMapping(value = "/scheduler/stopAll", method = RequestMethod.GET)
    public @ResponseBody  String stopAll() {
        schedulerManager.stopAll();
        return "stopAll";
    }

    @RequestMapping(value = "/scheduler/list", method = RequestMethod.GET)
    public @ResponseBody  List<String> getNameList() {
        return schedulerManager.getList();
    }

    @RequestMapping(value = "/scheduler/list", method = RequestMethod.POST)
    public @ResponseBody String postSchedule(@RequestBody final TCPInfo tcpInfo) {
        dummyService.add(tcpInfo.getName());
        System.out.println(tcpInfo);
        return "OK";
    }

    @RequestMapping("/scheduler/start")
    public @ResponseBody  String start(@RequestParam(name = "name") String name) {
        DynamicScheduler dynamicScheduler = schedulerManager.getDynamicScheduler(name);
        if (dynamicScheduler != null) {
            log.info("Staring schedule.. [{}] ", name);
            dynamicScheduler.start();
        } else {
            log.error("Null point error.. [{}] ", name);
        }
        return name;
    }

    @RequestMapping("/scheduler/stop")
    public String stop(@RequestParam(name = "name") String name) {
        DynamicScheduler dynamicScheduler = schedulerManager.getDynamicScheduler(name);
        if (dynamicScheduler != null) {
            log.info("Stopping schedule.. {} ", name);
            dynamicScheduler.stop();
        } else {
            log.error("Null point error {} ", name);
        }
        return name;
    }
}
