package com.example.demo.job;

import com.example.demo.context.Context;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Data
public class Job {
    private String id;
    private String description;
    private JobConf jobConf;

    public Job() {
        this.id = UUID.randomUUID().toString();
    }


}
