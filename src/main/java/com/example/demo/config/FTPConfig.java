package com.example.demo.config;

import com.example.demo.service.FTPService;
import com.example.demo.util.FTPUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "env")
public class FTPConfig {

    @Value("${ftp.server.ip}")
    private String ftpServer;

    @Value("${ftp.server.port}")
    private int ftpPort;

    @Value("${ftp.server.user}")
    private String ftpUser;

    @Value("${ftp.server.pass}")
    private String ftpPass;

    @Value("${ftp.server.localPath}")
    private String ftpLocalPath;

    @Bean
    public FTPClient createFtpClient() {
        return new FTPClient();
    }


    @Service
    public @Data
    class FTPServiceImpl implements FTPService {

        private long tryCount = 0;

        @Autowired
        private FTPClient client;

        @Override
        public void open() throws IOException {
//            System.out.println("FTP Open..." + tryCount++);
            if (client.isConnected() || client != null)
                client.disconnect();
            client = FTPUtil.initializeFtpClient(ftpServer, ftpPort, ftpUser, ftpPass);
            Collection<File> files = FTPUtil.downloadMissingFiles(client, "*", FTPUtil.FileType.BINARY, new File(ftpLocalPath));

            for (File file : files) {
                log.info("List of downloadMissingFiles {}", file.getName());
            }
        }
    }
}
