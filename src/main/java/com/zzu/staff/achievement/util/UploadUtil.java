package com.zzu.staff.achievement.util;

import java.io.File;
import java.util.UUID;

public class UploadUtil {

    /**
     * 得到真实文件名
     * @param fileName
     * @return
     */
    public static String subFileName(String fileName) {
        // 查找最后一个 \ (文件分隔符)位置
        int index = fileName.lastIndexOf(File.separator);
        if (index == -1) {
            // 没有分隔符，说明是真实名称
            return fileName;
        } else {
            return fileName.substring(index + 1);
        }
    }

    /**
     * 获得随机UUID文件名
     *
     * @param oldName
     * @param type
     * @param user
     * @param name
     * @param YEAR
     * @return
     */
    public static String generateRandonFileName(String oldName, int type, String user, String name, int YEAR) {
        // 首相获得扩展名，然后生成一个UUID码作为名称，然后加上扩展名
        String ext = oldName.substring(oldName.lastIndexOf("."));
        String newName = user+"_"+name+"_"+type+"_"+YEAR;
        return newName + ext;
    }
}
