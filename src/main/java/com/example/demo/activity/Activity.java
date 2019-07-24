package com.example.demo.activity;

import com.example.demo.context.Context;

public interface Activity {
    String getName();

    void setName(String name);

    void doActivity(Context context);
}
