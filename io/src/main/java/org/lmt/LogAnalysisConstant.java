package org.lmt;

/**
 * @author: LiaoMingtao
 * @date: 2020/9/24
 */
public class LogAnalysisConstant {

    private LogAnalysisConstant() {
    }

    /**
     * 合并相关字段
     */
    public static final String MERGE = "merge";
    public static final String ERROR = "error";
    public static final int MERGE_COUNT = 2;
    public static final String REQ_ZH = "请求内容：";
    public static final String RESP_ZH = "响应内容：";

    /**
     * 符号常量
     */
    public static final String EQUAL_SIGN = "=";
    public static final String SPACE = " ";
    public static final String VERTICAL_LINE = "|";
    public static final String DOUBLE_QUOTATION_MARKS = "\"";
    public static final String COLON = ":";
    public static final String COMMA = ",";

    /**
     * 解析字段
     */
    public static final String TIMESTAMP = "@timestamp";
    public static final String SOURCE = "source";
    public static final String OFFSET = "offset";
    public static final String BEAT = "beat";

    /**
     * 维度常量
     */
    public static final String APPSYSTEM = "appsystem";
    public static final String CLUSTERNAME = "clustername";
    public static final String SERVICENAME = "servicename";
    public static final String SERVICECODE = "servicecode";
    public static final String IP = "ip";
    public static final String HOSTNAME = "hostname";
    public static final String MESSAGE_1 = "message";
    public static final String FUNCTION_ID = "funcid";
    public static final String FUNCTION_NAME = "funcname";



    /**
     * xml 相关字符串
     */
    public static final String MESSAGES = "Messages";
    public static final String MESSAGE = "Message";
    public static final String NAME = "name";
    public static final String PKTNO = "pktno";
    public static final String FIELD = "Field";
    public static final String SEQUENCE = "Sequence";
    public static final String INHERIT = "inherit";
    public static final String PRIMITIVE_TYPE = "primitiveType";

    /**
     * 普通字段常量
     */
    public static final String LOG_DATETIME = "log_datetime";
    public static final String LOG_TIMESTAMP = "log_timestamp";
}
