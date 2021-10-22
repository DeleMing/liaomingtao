package org.lmt;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author wangm
 * @create 2021/1/13 13:17
 */
public class CloseableUtils {
    /**
     * 关闭流文件
     * @param closeables
     */
    public static void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException ioException) {
                    }
                }
            }
        }
    }
}
