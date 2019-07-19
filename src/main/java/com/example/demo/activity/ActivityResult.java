package com.example.demo.activity;

import lombok.Data;

public @Data
class ActivityResult {
    private boolean isSuccess;
    private String errorText;

    public ActivityResult(boolean isSuccess, String errorText) {
        this.isSuccess = isSuccess;
        this.errorText = errorText;
    }

}
