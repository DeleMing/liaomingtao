package org.lmt;

import com.alibaba.fastjson.JSON;
import com.sun.rowset.internal.Row;
import kafka.producer.ProducerPool;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.lmt.DateUtil;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/15
 */
public class Ctest4 {

    public static void main(String[] args) {
        String a = "2021-09-02 09:14:59.921173 msg : [szse opu:10][msg_no:200102: ver:0]<PktCashAuctionConfirm:<PktConfirm:<PktReportIndex:<partition_no=2|report_index=1|>appl_id=\"010\"|reporting_pbu_id=\"395696\"|submitting_pbu_id=\"395696\"|security_id=\"000762  \"|security_id_source=\"102 \"|owner_type=1|clearing_firm=\"01\"|transact_time=20210902091500000|user_info=\"        \"|order_id=\"00N0QYS3180000HX\"|cl_ord_id=\"110000004H\"|orig_cl_ord_id=\"          \"|exec_id=\"0102000000000276\"|exec_type=0|ord_status=0|ord_rej_reason=0|leaves_qty=100000|cum_qty=0|side=1|ord_type=2|order_qty=100000|price=711100|account_id=\"0600520692  \"|branch_id=\"NH  \"|order_restrict=\"    \"|>stop_px=0|min_qty=0|max_price_levels=0|time_in_force=0|cash_margin=1|>\n";
        test(a);
    }

    private static final long serialVersionUID = -1L;

    private static ThreadLocal<SimpleDateFormat> YYYY_MM_DD_HH_MM_SS_SSSSSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private static ThreadLocal<SimpleDateFormat> YYYY_MM_DD_T_HH_MM_SS_Z = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00");
            df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            return df;
        }
    };

    private Map<String, String> nameAndIdMap;
    // private Map<String, LogParams> nameAndLogParamsMap;
    private Map<String, String> nameAndChildNameMap;
    private Map<String, List<String>> nameAndParamMap;
    private String logTypeName;
    private String errorTopicName;
    private Map kafkaConfigMap;
    private ProducerPool producerPool;

    public static final int NUM2 = 2;

    public String[] keyByFields;
    public int keyLength;

    public static void test(String message) {
        Map<String, String> normalFields = new HashMap<>(8, 1);
        Map<String, Double> measures = new HashMap<>(1, 1);
        Map<String, String> dimensions = new HashMap<>(10, 1);
        String functionId = null;
        String functionName = null;
        String logDateTime = null;
        String utcLogDateTime = null;
        Map<String, String> nameAndIdMap = new HashMap<>(1);
        try { //2021-09-02 09:14:56.913406 msg : [szse opu:12][msg_no:101301: ver:0]<PktIssueOrder:
            int dataIndex = message.indexOf(" ");
            String dateString = message.substring(0, dataIndex);
            message = message.substring(dataIndex + 1);
            int timeIndex = message.indexOf(" ");
            String timeString = message.substring(0, timeIndex);
            logDateTime = dateString + " " + timeString;
            utcLogDateTime = dealDateFormat(logDateTime);
            Long logTimestamp = DateUtil.utcDate2Timestamp3(logDateTime);

            int leftBracketIndex = message.indexOf("[");
            int leftAngleIndex = message.indexOf("<");
            String msgVerStr = message.substring(leftBracketIndex, leftAngleIndex);

            parseMsgVerStr(normalFields, msgVerStr);

            message = message.substring(leftAngleIndex);
            int functionNameIndex = message.indexOf(LogAnalysisConstant.COLON);
            functionName = message.substring(1, functionNameIndex);

            functionId = nameAndIdMap.get(functionName);
            setNormalFields(normalFields, message);

            normalFields.put(LogAnalysisConstant.LOG_DATETIME, logDateTime);
            normalFields.put(LogAnalysisConstant.LOG_TIMESTAMP, String.valueOf(logTimestamp));
            normalFields.put("解析时间", YYYY_MM_DD_T_HH_MM_SS_Z.get().format(System.currentTimeMillis()));
        } catch (Exception ex) {
            //log.info("[GuotaiLowDelayAnalysisFunction] 数据解析失败,报错信息: {}, 消息内容: {}", ex.getMessage(), message);
            // producerPool.getProducer().sendMessage(errorTopicName, ex.getMessage() + "\t" + message);
            return;
        }

        // 维度

        dimensions.put(LogAnalysisConstant.IP, "1111");

        dimensions.put(LogAnalysisConstant.FUNCTION_ID, functionId);
        dimensions.put(LogAnalysisConstant.FUNCTION_NAME, functionName);
        System.out.println(JSON.toJSONString(normalFields));

    }

    public static String dealDateFormat(String oldDateStr) throws ParseException {
        Date date = YYYY_MM_DD_HH_MM_SS_SSSSSS.get().parse(oldDateStr);
        return YYYY_MM_DD_T_HH_MM_SS_Z.get().format(date);
    }

    private static void setNormalFields(Map<String, String> normalFields, String s) {
        if (null == normalFields || null == s) {
            return;
        }
        // 存储功能号
        Queue<String> queue = new LinkedList<String>();
        // 存储属性值
        Stack<String> stack = new Stack<>();

        int left = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '<') {
                queue.add(s.substring(left + 1, i));
                left = i;
            } else if (s.charAt(i) == '>') {
                stack.add(s.substring(left + 1, i));
                left = i;
            } else {
                continue;
            }
        }

        while (!queue.isEmpty() && !stack.isEmpty()) {
            String func = queue.poll().replaceAll(":", "");
            String attr = stack.pop();
            // 功能号存在无属性问题
            if (StringUtils.isNotBlank(attr)) {
                String[] splited = attr.split("\\|");
                for (String kv : splited) {
                    String[] innerSplit = kv.split("=");
                    normalFields.put(innerSplit[0], innerSplit[1].replaceAll("\"", "").trim());
                }
            }
        }
        queue = null;
        stack = null;
    }

    public static void parseMsgVerStr(Map<String, String> normalFields, String str) {
        if (str.trim().length() <= 1) {
            return;
        }
        Stack<String> stack = new Stack<>();
        int left = 0;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == ']') {
                stack.add(str.substring(left + 1, i));
                left = i;
            } else if (str.charAt(i) == '[') {
                left = i;
            } else {
                continue;
            }
        }
        while (!stack.isEmpty()) {
            String msg = stack.pop().trim();
            String[] splited = msg.split(":");
            int len = splited.length;
            if (len % NUM2 != 0) {
                break;
            } else {
                for (int i = 1; i < len; i += NUM2) {
                    normalFields.put(splited[i - 1].trim(), splited[i].trim());
                }
            }
        }
    }

    /**
     * 获取后面合并时keyBy字段值,如果该字段就取值,没有就用UUID填充。目前只支持2层, xxxx.yyyy 或 xxx 例如: normalFields.client_seq_id , keyWord
     *
     * @param element
     * @return
     */
    // public String getKeyGroup(LogRecord element) {
    //     StringBuffer buff = new StringBuffer("");
    //     String value = null;
    //
    //     for (int i = 0; i < keyLength; i++) {
    //         String[] splited = keyByFields[i].split("\\.");
    //         try {
    //             Method method = LogRecord.class.getDeclaredMethod("get" + splited[0].substring(0, 1).toUpperCase() + splited[0].substring(1));
    //
    //             if (splited.length == NUM2) {
    //                 value = (String) ((Map<String, Object>) method.invoke(element)).get(splited[1]);
    //             } else {
    //                 value = (String) method.invoke(element);
    //             }
    //
    //             if (value != null) {
    //                 buff.append(value + "_");
    //             } else {
    //                 buff.append(UUID.randomUUID().toString());
    //             }
    //         } catch (Exception e) {
    //             log.error("[GuotaiLowDelayAnalysisFunction] keyBy字段解析失败,无法找到该方法: {}", e.getMessage());
    //             return null;
    //         }
    //     }
    //     return buff.toString();
    // }
}
