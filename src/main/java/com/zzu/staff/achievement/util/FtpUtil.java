package com.zzu.staff.achievement.util;

import com.zzu.staff.achievement.config.FtpConfig;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

public class FtpUtil {

    public static String pictureUploadByConfig(FtpConfig ftpConfig, String picNewName, String dirName,
                                               InputStream inputStream) throws IOException {
        String picHttpPath;
        boolean flag = uploadFile(ftpConfig.getFTP_IP(), ftpConfig.getFTP_PORT(), ftpConfig.getFTP_USER_NAME(),
                ftpConfig.getFTP_PASSWD(), ftpConfig.getFTP_BASE_PATH(), picNewName, dirName,inputStream);
        if (!flag) {
            return null;
        }
        picHttpPath = ftpConfig.getFTP_BASE_URL() + picNewName;
        return picHttpPath;
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host
     *            FTP服务器hostname
     * @param ftpPort
     *            FTP服务器端口
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param basePath
     *            FTP服务器基础目录
     * @param filename
     *            上传到FTP服务器上的文件名
     * @param input
     *            输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, String ftpPort, String username, String password, String basePath,
                                     String filename, String dirName,InputStream input) {
        int port = Integer.parseInt(ftpPort);
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            System.out.println(ftp.login(username, password));
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            // 切换到上传目录
            ftp.changeWorkingDirectory(basePath);
            // 设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //ftp.enterLocalPassiveMode();// 这个设置允许被动连接--访问远程ftp时需要
            // 上传文件
            filename=new String(filename.getBytes("GBK"),"iso-8859-1");
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
}
