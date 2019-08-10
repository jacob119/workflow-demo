package com.example.demo.job;

import com.example.demo.context.Context;
import com.example.demo.context.StandardContext;

import java.util.HashMap;
import java.util.Map;

public class JobConf {
    private Map<String, Object> contexts;

    public JobConf() {
        this.contexts = new HashMap<>();
    }
}
