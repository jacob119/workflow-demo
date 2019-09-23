package com.example.demo.context;

import java.util.HashMap;
import java.util.Map;

public class StandardContext implements Context {
    private Map<String, Object> contexts;

    public StandardContext(Map<String, Object> parameters) {
        this.contexts = (parameters == null) ? new HashMap<>() : parameters;
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.contexts.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return this.contexts.get(name);
    }
}
