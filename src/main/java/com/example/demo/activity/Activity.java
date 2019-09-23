package com.example.demo.activity;

import com.example.demo.context.Context;

public interface Activity {
    String getName();

    void setName(String name);

    void doActivity(Context context);

    public boolean isNil();

    public static final Activity NULL = new Activity() {
        @Override
        public String getName() {
            return "Not defined";
        }

        @Override
        public void setName(String name) {
        }

        @Override
        public void doActivity(Context context) {
        }

        @Override
        public boolean isNil() {
            return true;
        }
    };
}
