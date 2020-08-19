package com.java.until.ftp;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


/**
 * @author nieyawei
 * @version 1.0
 * @className: FTPClientFactory
 * @description:
 * @date 2019-06-06 8:53
 */

public class FTPClientFactory extends BasePooledObjectFactory<FTPClient> {
    private static String host;
    private static int port;
    private static String username;
    private static String password;
    private static String encoding;
    private static boolean passiveMode;
    private static int connectTimeout;
    private static int bufferSize;
    static {
        ClassPathResource resource = new ClassPathResource("application.properties");
        Properties properties = null;
        try {
            properties = PropertiesLoaderUtils.loadProperties(resource);
            host = properties.getProperty("ftp.host");
            port = Integer.valueOf(properties.getProperty("ftp.port"));
            username = properties.getProperty("ftp.username");
            password = properties.getProperty("ftp.password");
            encoding = properties.getProperty("ftp.encoding");
            passiveMode = Boolean.parseBoolean(properties.getProperty("ftp.passiveMode"));
            connectTimeout = Integer.valueOf(properties.getProperty("ftp.connectTimeout"));
            bufferSize = Integer.valueOf(properties.getProperty("ftp.bufferSize"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FTPClientFactory() {
    }

    /**
     * 创建FtpClient对象
     */
    @Override
    public FTPClient create() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.setControlEncoding(encoding);
            ftpClient.setConnectTimeout(connectTimeout);
            //开启服务器对utf8的支持
            ftpClient.sendCommand("OPTS UTF8","ON");
            //判断连接远程服务器是否成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
                return null;
            }
            //登录，并判断是否登陆成功
            if (!ftpClient.login(username, password)) {
                System.out.println("ftp连接失败 username is {blt}; password: {123456}");
            }
            //缓存大小
            ftpClient.setBufferSize(bufferSize);
            //采用被动模式
            if (passiveMode) {
                ftpClient.enterLocalPassiveMode();
            }
            //设置文件传输类型为二进制方式
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        } catch (IOException e) {
            System.out.println(e);
        }
        return ftpClient;
    }

    /**
     * 用PooledObject封装对象放入池中
     */
    @Override
    public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
        return new DefaultPooledObject<>(ftpClient);
    }

    /**
     * 销毁FtpClient对象
     */
    @Override
    public void destroyObject(PooledObject<FTPClient> ftpPooled) {
        if (ftpPooled == null) {
            return;
        }

        FTPClient ftpClient = ftpPooled.getObject();

        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * 验证FtpClient对象
     */
    @Override
    public boolean validateObject(PooledObject<FTPClient> ftpPooled) {
        try {
            FTPClient ftpClient = ftpPooled.getObject();
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

}