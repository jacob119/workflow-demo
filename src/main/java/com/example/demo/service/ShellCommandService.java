package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ShellCommandService {

    private boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    // https://www.baeldung.com/run-shell-command-in-java
    public void executeCommand() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.redirectErrorStream(true);
        if (isWindows) {
            builder.command("cmd.exe", "/c", "dir");
        } else {
            builder.command("sh", "-c", "ls");
        }
        Process process = builder.start();
        int errorCode = process.waitFor();
        String result = convertStreamToStr(process.getInputStream());
        log.info("errorCode={},result={}", errorCode, result);
    }

    private String convertStreamToStr(InputStream is) throws IOException {

        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}
