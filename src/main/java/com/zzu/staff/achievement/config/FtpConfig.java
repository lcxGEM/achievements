package com.zzu.staff.achievement.config;

import lombok.Data;

@Data
public class FtpConfig {

    private String FTP_IP = "47.96.2.117";

    private String FTP_PORT = "21";

    private String FTP_USER_NAME = "ftptest";

    private String FTP_PASSWD = "1234";

    private String FTP_BASE_PATH = "/usr/local/nginx/html/img_server/imgserver/";

    private String FTP_BASE_URL = "http://47.96.2.117:8449/imgserver/";
}
