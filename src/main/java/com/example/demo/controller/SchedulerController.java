package com.example.demo.controller;

import com.example.demo.schedule.DynamicScheduler;
import com.example.demo.schedule.SchedulerManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class SchedulerController {

    @Autowired
    private SchedulerManager schedulerManager;

    @RequestMapping("/scheduler/startAll")
    public String startAll() {
        schedulerManager.startAll();
        return "startAll";
    }

    @RequestMapping("/scheduler/stopAll")
    public String stopAll() {
        schedulerManager.stopAll();
        return "stopAll";
    }

    @RequestMapping("/scheduler/list")
    public List<String> getNameList() {
        return schedulerManager.getList();
    }

    @RequestMapping("/scheduler/start")
    public String start(@RequestParam(name = "name") String name) {
        DynamicScheduler dynamicScheduler = schedulerManager.getDynamicScheduler(name);
        if (dynamicScheduler != null) {
            log.info("Staring schedule.. {} ", name);
            dynamicScheduler.start();
        } else {
            log.error("Null point error {} ", name);
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
