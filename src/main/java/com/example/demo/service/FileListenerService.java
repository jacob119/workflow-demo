package com.example.demo.service;

import com.example.demo.listener.FileListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class FileListenerService {

    @Value("${file.monitor.directory}")
    private String baseDir;

    @Value("${file.monitor.filter}")
    private String baseFilter;

    @Value("${file.monitor.interval}")
    private long pollingInt;

    private FileAlterationMonitor monitor = null;

    public void start() {
        // Polling interval 5 seconds
        long interval = TimeUnit.SECONDS.toMillis(pollingInt);
        // Create filters
        IOFileFilter directories = FileFilterUtils.and(
                FileFilterUtils.directoryFileFilter(),
                HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(baseFilter));

        IOFileFilter filter = FileFilterUtils.or(directories, files);
        // Use filters
        FileAlterationObserver observer = new FileAlterationObserver(new File(baseDir), filter);
        // Do not use filters
        //FileAlterationObserver observer = new FileAlterationObserver(new File(rootDir));
        observer.addListener(new FileListener());
        // Create a File Change Listener
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        // Start monitoring
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        if (monitor != null) {
            monitor.stop();
        }
    }

}
