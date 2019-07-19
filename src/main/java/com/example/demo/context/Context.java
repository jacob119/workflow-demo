package com.example.demo.context;

public interface Context {
    void setAttribute(String name, Object value);

    Object getAttribute(String name);
}
