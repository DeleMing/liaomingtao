package org.lmt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

/**
 * @author: LiaoMingtao
 * @date: 2021/8/30
 */
public class MyCallable implements Callable<Object> {

    private FileChannel fc;
    private FileLock fl;
    private MappedByteBuffer mbBuf;
    private HashMap hm;

    public MyCallable(File src, long start, long end) {
        try {
            //得到当前文件的通道
            fc = new RandomAccessFile(src, "rw").getChannel();
            //锁定当前文件的部分
            fl = fc.lock(start, end, false);
            //对当前文件片段建立内存映射，如果文件过大需要切割成多个片段
            mbBuf = fc.map(FileChannel.MapMode.READ_ONLY, start, end);
            //创建HashMap实例存放处理结果
            hm = new HashMap<String, Integer>();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object call() throws Exception {
        String str = Charset.forName("UTF-8").decode(mbBuf).toString();
        //使用StringTokenizer分析单词
        StringTokenizer token = new StringTokenizer(str);
        String word;
        while(token.hasMoreTokens()) {
            //将处理结果放到一个HashMap中
            word = token.nextToken();
            if(null != hm.get(word)) {
                hm.put(word, (Integer)hm.get(word)+1);
            } else {
                hm.put(word, 1);
            }
        }
        try {
            //释放文件锁
            fl.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hm;
    }
}
