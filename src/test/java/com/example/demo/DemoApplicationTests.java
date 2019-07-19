package com.example.demo;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.DefaultFileNameGenerator;
import org.springframework.integration.file.remote.ClientCallbackWithoutResult;
import org.springframework.integration.file.remote.SessionCallbackWithoutResult;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;

import java.io.*;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost("192.168.35.126");
        sf.setPort(21);
        sf.setUsername("foo");
        sf.setPassword("foo");

        FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(sf);
        DefaultFileNameGenerator fileNameGenerator = new DefaultFileNameGenerator();
        fileNameGenerator.setBeanFactory(mock(BeanFactory.class));
        fileNameGenerator.setExpression("'foobar.txt'");
        template.setFileNameGenerator(fileNameGenerator);
        template.setRemoteDirectoryExpression(new LiteralExpression("foo/"));
        template.setUseTemporaryFileName(false);
        template.setBeanFactory(mock(BeanFactory.class));
        template.afterPropertiesSet();
        template.execute(session -> {
            session.mkdir("foo/");
            return session.mkdir("foo/bar/");
        });
        template.append(new GenericMessage<>("foo"));
        template.append(new GenericMessage<>("bar"));

        assertThat(template.exists("foo/foobar.txt")).isTrue();
        template.executeWithClient((ClientCallbackWithoutResult<FTPClient>) client -> {
            try {
                FTPFile[] files = client.listFiles("foo/foobar.txt");
                assertThat(files[0].getSize()).isEqualTo(6);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        template.execute((SessionCallbackWithoutResult<FTPFile>) session -> {
            assertThat(session.remove("foo/foobar.txt")).isTrue();
            assertThat(session.rmdir("foo/bar/")).isTrue();
            FTPFile[] files = session.list("foo/");
            assertThat(files.length).isEqualTo(0);
            assertThat(session.rmdir("foo/")).isTrue();
        });
        assertThat(template.exists("foo")).isFalse();
    }

    @Test
    public void List() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost("192.168.35.126");
        sf.setPort(21);
        sf.setUsername("foo");
        sf.setPassword("foo");

        FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(sf);
        DefaultFileNameGenerator fileNameGenerator = new DefaultFileNameGenerator();
        //fileNameGenerator.setBeanFactory(mock(BeanFactory.class));
        fileNameGenerator.setExpression("'*.txt'");
        template.setFileNameGenerator(fileNameGenerator);

        template.setRemoteDirectoryExpression(new LiteralExpression("foo/"));

        template.execute((SessionCallbackWithoutResult<FTPFile>) session -> {
            FTPFile[] files = session.list("agentlog.txt");
            for (FTPFile ftpFile : files) {
                if (ftpFile != null) {
                    if (ftpFile.isDirectory() || ftpFile.getSize() == 0)
                        continue;
                    String name = ftpFile.getName();
                    long size = ftpFile.getSize();
                    String timestamp = ftpFile.getTimestamp().getTime().toString();
                    String type = ftpFile.isDirectory() ? "Directory" : "File";

                    System.out.println("Name: " + name);
                    System.out.println("Size: " + size);
                    System.out.println("Type: " + type);
                    System.out.println("Timestamp: " + timestamp);

                    File file = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write("foo".getBytes());
                    fileOutputStream.close();
                    try {
                        template.send(new GenericMessage<>(file));
                        fail("exception expected");
                    } catch (MessagingException e) {
                        assertThat(e.getCause().getMessage()).isEqualTo("bar");
                    }
                    File newFile = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
                } else {
                    System.out.println("The specified file/directory may not exist!");
                }
            }
        });
    }

    @Test
    public void DownloadTestUsingCompletePending() {
        String server = "192.168.35.126";
        int port = 21;
        String user = "foo";
        String pass = "foo";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            // use local passive mode to pass firewall
            // ftpClient.enterLocalPassiveMode();


            System.out.println("ReplyString : " + ftpClient.getReplyString());
            //System.out.println("ReplyCode : " + ftpClient.getReply());
            // get details of a file or directory
            //String remoteFilePath = "Java/CodeLib/FTP.rar";
            String remoteFilePath = " ";

            FTPFile[] ftpFiles = ftpClient.listFiles(remoteFilePath, FTPFile::isFile); //(remoteFilePath, FTPFile::isFile);
            {
                for (FTPFile ftpFile : ftpFiles) {

                    File downloadFile = new File("/Users/jacob/Downloads/cccc/" + ftpFile.getName());
                    File parentDir = downloadFile.getParentFile();
                    if (!parentDir.exists()) {
                        parentDir.mkdir();
                    }

                    if (ftpFile != null) {
                        String name = ftpFile.getName();
                        long size = ftpFile.getSize();
                        String timestamp = ftpFile.getTimestamp().getTime().toString();
                        String type = ftpFile.isDirectory() ? "Directory" : "File";

                        System.out.println("Name: " + name);
                        System.out.println("Size: " + size);
                        System.out.println("Type: " + type);
                        System.out.println("Timestamp: " + timestamp);

                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                        InputStream inputStream = ftpClient.retrieveFileStream(ftpFile.getName());
                        byte[] bytesArray = new byte[4096];
                        int bytesRead = -1;
                        int i = 0;
                        while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                            outputStream.write(bytesArray, 0, bytesRead);
                            System.out.println("*********-->" + i++);
                        }
                        boolean success = ftpClient.completePendingCommand();
                        if (success) {
                            System.out.println(name + " has been downloaded successfully.");
                        }
                    } else {
                        System.out.println("The specified file/directory may not exist!");
                    }
                }
            }

            ftpClient.logout();
            ftpClient.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    @Test
    public void DownloadTest() {
        String server = "192.168.35.126";
        int port = 21;
        String user = "foo";
        String pass = "foo";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.setBufferSize(1024);

            ftpClient.doCommand("pwd", "");
            // use local passive mode to pass firewall
            // ftpClient.enterLocalPassiveMode();


            System.out.println("ReplyString : " + ftpClient.getReplyString());
            //System.out.println("ReplyCode : " + ftpClient.getReply());
            // get details of a file or directory
            //String remoteFilePath = "Java/CodeLib/FTP.rar";
            String remoteFilePath = "/";

            FTPFile[] ftpFiles = ftpClient.listFiles(remoteFilePath, FTPFile::isFile); //(remoteFilePath, FTPFile::isFile);
            {
                for (FTPFile ftpFile : ftpFiles) {

                    File downloadFile = new File("/Users/jacob/Downloads/cccc/" + ftpFile.getName());
                    File parentDir = downloadFile.getParentFile();
                    if (!parentDir.exists()) {
                        parentDir.mkdir();
                    }

                    if (ftpFile != null) {
                        String name = ftpFile.getName();
                        long size = ftpFile.getSize();
                        String timestamp = ftpFile.getTimestamp().getTime().toString();
                        String type = ftpFile.isDirectory() ? "Directory" : "File";

                        System.out.println("Name: " + name);
                        System.out.println("Size: " + size);
                        System.out.println("Type: " + type);
                        System.out.println("Timestamp: " + timestamp);

                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        boolean success = ftpClient.retrieveFile(remoteFilePath + "/" + name, outputStream);

                        if (success) {
                            System.out.println(name + " has been downloaded successfully.");
                        }
                    } else {
                        System.out.println("The specified file/directory may not exist!");
                    }
                }
            }

            ftpClient.logout();
            ftpClient.disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
