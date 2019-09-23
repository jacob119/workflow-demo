package com.example.demo.activity;

import com.example.demo.context.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParserActivity extends BaseActivity {
    public ParserActivity(String name) {
        super(name);
    }


    @Override
    public void doActivity(Context context) {
        log.info("+++ doParserActivity");
    }

}
