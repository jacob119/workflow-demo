package com.example.demo.activity;

import com.example.demo.context.Context;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class BaseActivity implements Activity {

    @NonNull
    private String name;


    public final void setName(String name) {
        this.name = name;
    }

    public BaseActivity(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isNil(){
        return false;
    }

    @Override
    public void doActivity(Context context) {
        log.warn("Nothing to do for action-name=[{}]", this.name);
    }
}
