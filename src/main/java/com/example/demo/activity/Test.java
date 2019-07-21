package com.example.demo.activity;

import org.springframework.beans.factory.InitializingBean;



public class Test implements  InitializingBean {
    public Test() {
        System.out.println("Create");
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean");
    }
}
