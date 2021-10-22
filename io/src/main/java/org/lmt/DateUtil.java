package org.lmt;

import org.joda.time.DateTime;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/15
 */
public class DateUtil {
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
    private static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy.MM.dd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> sdf1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> sdf2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> utcSdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }
    };
    private static ThreadLocal<SimpleDateFormat> utcSdf2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        }
    };
    private static ThreadLocal<SimpleDateFormat> utcSdf1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
        }
    };

    public DateUtil() {
    }

    public static Long timestamp(String timestamp) {
        return (new DateTime(timestamp)).toDate().getTime();
    }

    public static String format(String timestamp) {
        return ((SimpleDateFormat) sdf.get()).format((new DateTime(timestamp)).toDate());
    }

    public static Long utcDate2Timestamp2(String utcDateStr) throws ParseException {
        return ((SimpleDateFormat) utcSdf1.get()).parse(utcDateStr).getTime();
    }

    public static Long utcDate2Timestamp(String utcDateStr) throws ParseException {
        return ((SimpleDateFormat) utcSdf.get()).parse(utcDateStr).getTime();
    }

    public static Long utcDate2Timestamp3(String utcDateStr) throws ParseException {
        return ((SimpleDateFormat) utcSdf2.get()).parse(utcDateStr).getTime();
    }

    public static Long utcDate2Timestamp1(String utcDateStr) {
        try {
            return ((SimpleDateFormat) utcSdf.get()).parse(utcDateStr).getTime();
        } catch (ParseException var2) {
            var2.printStackTrace();
            return 0L;
        }
    }

    public static String format1(long timestamp) {
        return ((SimpleDateFormat) sdf2.get()).format((new DateTime(timestamp)).toDate());
    }

    public static String getUtcTimeStr() {
        return format.format(new Date()).toString();
    }

    public static String getUtcTimeStr(long interval) {
        long currentTimeMillis = System.currentTimeMillis();
        return format.format(new Date(currentTimeMillis + interval)).toString();
    }

    public static String yyyymmddFormat(String utcDateStr) {
        Date date = null;

        try {
            date = ((SimpleDateFormat) utcSdf1.get()).parse(utcDateStr);
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

        return ((SimpleDateFormat) sdf1.get()).format(date);
    }
}
