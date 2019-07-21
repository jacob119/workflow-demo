package com.example.demo.service;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ShellCommandServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void executeCommand() throws IOException, InterruptedException {
        ShellCommandService shellCommandService = new ShellCommandService();
        shellCommandService.executeCommand();
    }
}