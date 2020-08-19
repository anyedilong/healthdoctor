package com.java.until.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * @author nieyawei
 * @version 1.0
 * @className: FTPClientFactory
 * @description:
 * @date 2019-06-06 9:33
 */

public class UpUtils {

    private static FTPClientPool pool;

    static {
        try {
            pool = FTPClientPool.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param in 上传文件的输入流
     * @param in 上传文件的路径
     * @param in 生成的文件名
     * @return : boolean 是否上传成功
     * @Description : 上传文件至FTP服务器
     * @author: 聂亚威
     * @date : 2019年6月7日 上午11:43
     */
    public static boolean upload(InputStream in, String outPath, String outFileName) {
        OutputStream out = null;
        try {
            FTPClient ftp = pool.borrowObject();
            /*FTPFile[] files1 = ftp.listFiles();
            for(FTPFile file : files1){
                System.out.println(file.getName());
            }*/
            boolean isTrue = makeDirectory(ftp, outPath);
            if (!isTrue) {
                return isTrue;
            }

            String fileName = outPath + "/" + outFileName;
            //检查服务器是否存在该文件
            FTPFile[] files = ftp.listFiles(new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            int remoteSize = 0;
            int inFileSize = in.available();
            if (files.length >= 1) {
                remoteSize = (int) files[0].getSize();
                if (remoteSize >= inFileSize) {
                    System.out.println(outFileName + "已存在！");
                    return true;
                }
            }

            //显示进度的上传
            int step = inFileSize / 100;
            int progress = 0;
            int uploadedSize = 0;
            out = ftp.appendFileStream(new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));

            //断点续传
            if (remoteSize > 0) {
                ftp.setRestartOffset(remoteSize);
                if (step != 0)
                    progress = remoteSize / step;
                in.skip(remoteSize);
                uploadedSize = remoteSize;
            }
            byte[] bytes = new byte[102400];
            int c;
            int count = 0;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                uploadedSize += c;
                if (step != 0 && count > 100 && progress < uploadedSize / step) {
                    count = 0;
                    progress = uploadedSize / step;
                    if (progress >= 100) {
                        progress = 99;
                    }
                    System.out.println("上传进度:" + progress + "%");
                }
                count++;
            }
            System.out.println("上传完成:100%");
            out.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //创建目录
    private static boolean makeDirectory(FTPClient ftp, String path) {
        if (!checkDirectory(ftp, path)) {
            String[] pathArray = path.split("/");
            for (int i = 0; i < pathArray.length; i++) {
                try {
                    if (StringUtils.isNotBlank(pathArray[i])) {
                        boolean isTrue = ftp.makeDirectory(new String(pathArray[i].getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                        if (!isTrue) {
                            return false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    //校验目录或文件是否存在
    private static boolean checkDirectory(FTPClient ftp, String path) {
        try {
            return ftp.changeWorkingDirectory(new String(path.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** * 下载文件 * 
     * @param pathname FTP服务器文件目录 * 
     * @param filename 文件名称 * 
     * @param localpath 下载后的文件路径 * 
     * @return */
     public  static InputStream getFTPFile(String pathname, String filename){ 
         InputStream is = null;
         FTPClient ftpClient = null;
         try { 
        	 ftpClient = pool.borrowObject();
             //切换FTP目录 
        	 ftpClient.changeWorkingDirectory(new String(pathname.getBytes(), FTP.DEFAULT_CONTROL_ENCODING)); 
             FTPFile[] ftpFiles = ftpClient.listFiles(); 
             if(ftpFiles.length > 0){
                 is  = ftpClient.retrieveFileStream(filename);
             }
             //ftpClient.logout(); 
         } catch (Exception e) { 
             e.printStackTrace(); 
         } finally{ 
             if(ftpClient.isConnected()){ 
                 try{
                	 ftpClient.disconnect();
                 }catch(IOException e){
                     e.printStackTrace();
                 }
             } 
         } 
         return is; 
     }
    
    
    /*public static void main(String[] args) throws Exception {
        //File file = new File("D:/Download/readme.txt");
        //System.out.println(file.length());
        //boolean is = upload(new FileInputStream(file), "/abc",  "readme.txt");
        //boolean is = downLoad("plsqldeveloperxx.zip", "D:\\Download\\plsqldeveloperxx.zip");
        //System.out.println(is);
        FTPClient ftpClient = pool.borrowObject();
        if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8","ON"))){
            System.out.println(ftpClient.sendCommand("OPTS UTF8","ON"));
        }
        System.out.println(ftpClient.sendCommand("OPTS utf8","ON"));
    }*/
}


