package com.example.demo.activity;

import com.example.demo.context.Context;
import com.example.demo.service.ShellCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@Scope(value = "prototype")
public class HDFSActivity extends BaseActivity {

    @Autowired
    private ShellCommandService shellCommandService;

    public HDFSActivity() {
        super("");
    }

    @Override
    public void doActivity(Context context) {

        log.info("+++ +++ do HDFSActivity");

        try {
            shellCommandService.executeCommand();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
