package com.example.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

@Slf4j
public class FileListener extends FileAlterationListenerAdaptor {
    /**
     * File creation execution
     */
    public void onFileCreate(File file) {
        log.info("[new]:" + file.getAbsolutePath());
    }

    /**
     * File creation modification
     */
    public void onFileChange(File file) {
        log.info("[modification]:" + file.getAbsolutePath());
    }

    /**
     * File deletion
     */
    public void onFileDelete(File file) {
        log.info("[delete]:" + file.getAbsolutePath());
    }

    /**
     * Directory Creation
     */
    public void onDirectoryCreate(File directory) {
        log.info("[new]:" + directory.getAbsolutePath());
    }

    /**
     * Directory modification
     */
    public void onDirectoryChange(File directory) {
        log.info("[modification]:" + directory.getAbsolutePath());
    }

    /**
     * Directory deletion
     */
    public void onDirectoryDelete(File directory) {
        log.info("[delete]:" + directory.getAbsolutePath());
    }

    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
    }

    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }
}
