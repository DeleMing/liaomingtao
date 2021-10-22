package org.lmt;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: LiaoMingtao
 * @date: 2021/6/25
 */
@Slf4j
public class Ctest8 {

    private static String slash = "/";

    /**
     * 创建目录
     *
     * @param dir 目录
     */
    public static void mkdirDir(String dir) {
        try {
            String dirTemp = dir;
            File dirPath = new File(dirTemp);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * 复制文件夹
     *
     * @param oldPath 源文件夹路径 如：E:/phsftp/src
     * @param newPath 目标文件夹路径 如：E:/phsftp/dest
     */
    public static void copyFolder(String oldPath, String newPath) {
        mkdirDir(newPath);
        File file = new File(oldPath);
        String[] files = file.list();
        File temp = null;
        for (int i = 0; i < files.length; i++) {
            if (oldPath.endsWith(slash)) {
                temp = new File(oldPath + files[i]);
            } else {
                temp = new File(oldPath + slash + files[i]);
            }

            if (temp.isFile()) {
                FileInputStream input = null;
                FileOutputStream output = null;
                try {
                    input = new FileInputStream(temp);
                    output = new FileOutputStream(newPath + "/" + (temp.getName()));
                    byte[] buffer = new byte[1024 * 2];
                    int len;
                    while ((len = input.read(buffer)) != -1) {
                        output.write(buffer, 0, len);
                    }
                    output.flush();
                } catch (IOException e) {
                    log.error("errMsg:", e);
                    log.error("errMsg:{}", "错误");
                    log.error("errMsg:", "错误");
                    log.error("errMsg:{}{}", "错误", String.valueOf(e));
                } finally {
                    CloseableUtils.close(output);
                    CloseableUtils.close(input);
                }
            }
            if (temp.isDirectory()) {
                copyFolder(oldPath + "/" + files[i], newPath + "/" + files[i]);
            }
        }
    }

    public static void main(String[] args) {
        copyFolder("F:\\ceshi\\", "G:\\ceshi2");
    }
}
