package com.example.demo;

import com.example.demo.util.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class FTPUtilTest {
    private String server = "192.168.35.126";
    private int port = 21;
    private String user = "foo";
    private String localStorage = "/Users/jacob/Downloads/cccc/";
    private String pass = "foo";
    private FTPClient client = null;

    @Before
    public void setup() throws IOException {
        client = FTPUtil.initializeFtpClient(server, port, user, pass);
    }

    @Test
    public void downloadAllFilesTest() throws IOException {
        FTPUtil.downloadAllFiles(client, "*", FTPUtil.FileType.BINARY, new File(localStorage));
    }

    @Test
    public void downloadMissingFilesTest() throws IOException {

        Collection<File> files = FTPUtil.downloadMissingFiles(client, "*", FTPUtil.FileType.BINARY, new File(localStorage));

        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}