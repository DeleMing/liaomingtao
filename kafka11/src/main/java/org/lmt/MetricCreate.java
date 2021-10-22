package org.lmt;

import org.lmt.avro.AvroSchemaDef;
import org.lmt.avro.AvroSerializer;
import avro.shaded.com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author shaojiao
 * @version 1.0
 * @date 2020/12/7 16:00
 */
public class MetricCreate {

    public static void main(String[] args) throws ParseException {
        Properties props = new Properties();
        // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"kafka-1:19092,kafka-2:19092,kafka-3:19092");
        // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"zork90-13:9092,zork90-14:9092,zork90-16:9092");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"zork-rd-dev-70-57:9092,zork-rd-dev-70-185:9092");
        // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"yf170:9092,yf171:9092,yf172:9092");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,1000);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        KafkaProducer kafkaProducer = new KafkaProducer(props);
        String metricData = "{\"dimensions\":{\"core_module\":\"system\",\"appsystem\":\"dev_test\",\"hostname\":\"yf170\",\"workernodeid\":\"999999\",\"ip\":\"192.168.70.170\"},\"measures\":{\"core_id\":0,\"core_irq_pct\":0,\"core_user_pct\":0.0775,\"core_system_pct\":0.0285,\"core_softirq_pct\":0.0112,\"core_nice_pct\":0,\"core_idle_pct\":0.7176,\"core_steal_pct\":0,\"core_iowait_pct\":0.1651},\"metricsetname\":\"core\",\"timestamp\":\"2020-10-23T07:04:09.539Z\"}";

        /// createDataOne(kafkaProducer);

        Map<String, String> hostMap = getHostMap(50);
        long startTime = changeToTimestamp("2021-10-22 14:49:00.000");
        long endTime = changeToTimestamp("2021-10-22 15:09:59.999");
        long timeSpan = 30000L;
        List<Double> valueList = new ArrayList();
        valueList.add(0.01);
        valueList.add(0.6);
        valueList.add(0.12);
        int valueIndex = 0;
        while (startTime <= endTime) {
            Double value = valueList.get(valueIndex);
            if(valueIndex == 2){
                valueIndex = 0;
            }else{
                valueIndex++;
            }
            for (String ip : hostMap.keySet()) {
                Map<String, Object> map = createMetric(startTime, "QT2", hostMap.get(ip), ip, "集群1", "集群2", value);
                /// Map<String, Object> map = createMetric(startTime, "OTC", hostMap.get(ip), ip, "OTC数据库", "证通灾备", 1.0);
                Map<String, Object> mapOne = createMetricOne(startTime, "QT2", hostMap.get(ip), ip, "集群1", "集群2", value);
                Map<String, Object> mapTwo = createMetricTwo(startTime, "QT2", hostMap.get(ip), ip, "集群1", "集群2", value);
                sendMetricOne(kafkaProducer, map);
                sendMetricOne(kafkaProducer, mapOne);
                sendMetricOne(kafkaProducer, mapTwo);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startTime += timeSpan;
        }


    }


    public static long changeToTimestamp(String dateStr) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = df.parse(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long timestamp = cal.getTimeInMillis();
        System.out.println(timestamp);
        return timestamp;
    }

    public static Map<String, String> getHostMap(int size){
        String result = HttpClientUtil.get("http://192.168.70.57:9040/v1/comm/batchBizTopo?bizId=7",new HashMap<>(0));
        System.out.println(result);
        // String result = HttpClientUtil.get("http://192.168.70.57:9040/v1/comm/batchBizTopo?bizId=7",new HashMap<>(0));
        JSONObject resultJson = JSONObject.parseObject(result);
        if(resultJson.getInteger("code") != 0){
            System.out.println(result);
            return new HashMap<>(0);
        }
        Map<String, String> hostMap = new HashMap<>(10);
        handleJson(resultJson.getJSONArray("data"),hostMap, size);
        System.out.println(hostMap.size());
        return hostMap;
    }

    public static Map<String, String> handleJson(JSONArray jsonArray, Map<String, String> hostMap, int size){
        for(int i=0; i<jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String objId = jsonObject.getString("objId");
            if(!"host".equals(objId)){
                JSONArray jsonArray1 = jsonObject.getJSONArray("children");
                handleJson(jsonArray1, hostMap, size);
                if(hostMap.size() == size){
                    return hostMap;
                }
            }else{
                JSONObject jsonObject1 = jsonObject.getJSONObject("tag");
                String ip = jsonObject1.getString("bk_host_innerip");
                String hostname = jsonObject1.getString("bk_host_name");
                if(!hostMap.containsKey(ip)){
                    hostMap.put(ip,hostname);
                    if(hostMap.size() == size){
                        return hostMap;
                    }
                }
            }
        }
        return  hostMap;
    }

    public static void createDataTwo(KafkaProducer kafkaProducer){
        /// endMetricAvro(kafkaProducer, metricData);
        long startTime = 1623823500000L;
        long endTime = 1623825000000L;
        long timeSpan = 10000L;
        int sleep = 1;
        while (startTime <= endTime) {
            // Map<String,Object> map = createMetric(startTime,"dev_test", "noahtest-216", "192.168.70.216", "备用","备用", 1.0);
            // Map<String,Object> map = createMetric(startTime,"dev_test", "yf171", "192.168.70.171", "基础监控","linux模块", 1.0);
            Map<String,Object> map = createMetric(startTime,"dev_test", "node120", "192.168.70.120", "基础监控","linux模块", 1.0);
            sendMetricOne(kafkaProducer, map);

            // Map<String,Object> map1 = createMetric(startTime,"dev_test", "yf172", "192.168.70.172", "基础监控","", 1.0);
            Map<String,Object> map1 = createMetric(startTime,"dev_test", "noahtest-215", "192.168.70.215", "备用","备用", 1.0);
            sendMetricOne(kafkaProducer, map1);
            Map<String,Object> map2 = createMetric(startTime,"dev_test", "noahtest-215", "192.168.70.215", "基础监控","linux模块", 1.0);
            sendMetricOne(kafkaProducer, map2);
            if(sleep == 30){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sleep = 0;
            }else{
                sleep++;
            }
            startTime += timeSpan;
        }
    }

    public static void createDataOne(KafkaProducer kafkaProducer) throws ParseException {
        List<Double> valueList = new ArrayList();
        valueList.add(0.01);
        valueList.add(0.6);
        valueList.add(0.12);
        long startTime = changeToTimestamp("2021-09-06 15:24:00.000");;
        long endTime = changeToTimestamp("2021-09-06 15:28:59.999");;
        int valueIndex = 0;
        long timeSpan = 30000L;
        int sleep = 1;
        while (startTime <= endTime){
            Double value = valueList.get(valueIndex);
            if(valueIndex == 2){
                valueIndex = 0;
            }else{
                valueIndex++;
            }
            Map<String,Object> map = createMetric(startTime,"alarm", "zork90-13", "192.168.90.13", "告警集群","告警模块1", value);
            sendMetricOne(kafkaProducer, map);
            Map<String,Object> mapTwo = createMetric(startTime,"alarm", "zork90-10", "192.168.90.10", "告警集群","告警模块1", value);
            sendMetricOne(kafkaProducer, mapTwo);
            Map<String,Object> mapThree = createMetric(startTime,"alarm", "zork90-14", "192.168.90.14", "告警集群","告警模块2", value);
            sendMetricOne(kafkaProducer, mapThree);
            Map<String,Object> mapFour = createMetric(startTime,"alarm", "zork90-16", "192.168.90.16", "告警集群","告警模块2", value);
            sendMetricOne(kafkaProducer, mapFour);
            Map<String,Object> mapFive = createMetric(startTime,"alarm", "zork90-10", "192.168.90.10", "告警集群2","告警模块1", value);
            sendMetricOne(kafkaProducer, mapFive);
            Map<String,Object> mapSix = createMetric(startTime,"alarm", "zork90-14", "192.168.90.14", "告警集群2","告警模块1", value);
            sendMetricOne(kafkaProducer, mapSix);
            startTime += timeSpan;
            if(sleep == 100){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sleep = 0;
            }else{
                sleep++;
            }
        }
    }

    public static Map<String,Object> createMetric(long timestamp, String systemCode, String hostnema, String ip, String clusterName, String serviceName, Double value){
        Map<String, Object> dataMap = new HashMap<>(4);

        // 维度
        Map<String,String> dimensions = new HashMap<>(4);
        dimensions.put("hostname",hostnema);
        dimensions.put("ip",ip);
        dimensions.put("appsystem",systemCode);
        dimensions.put("clustername",clusterName);
        dimensions.put("servicename",serviceName);
        dimensions.put("appprogramname",serviceName);

        // 度量值
        Map<String,Double> measures = new HashMap<>();
        measures.put("user_pct", value);
        measures.put("cores", value);
        measures.put("system_pct", value);
        measures.put("nice_pct", value);
        measures.put("idle_pct", value);
        measures.put("iowait_pct", value);
        measures.put("irq_pct", value);
        measures.put("softirq_pct", value);
        measures.put("steal_pct", value);
        measures.put("total_pct", value);
        measures.put("used_pct", value);

        dataMap.put("dimensions", dimensions);
        dataMap.put("measures", measures);
        dataMap.put("metricsetname","cpu_system_mb");
        dataMap.put("timestamp", timestamp);
        return dataMap;
    }

    public static Map<String,Object> createMetricOne(long timestamp, String systemCode, String hostnema, String ip, String clusterName, String serviceName, Double value){
        Map<String, Object> dataMap = new HashMap<>(4);

        // 维度
        Map<String,String> dimensions = new HashMap<>(4);
        dimensions.put("hostname",hostnema);
        dimensions.put("ip",ip);
        dimensions.put("appsystem",systemCode);
        dimensions.put("clustername",clusterName);
        dimensions.put("servicename",serviceName);
        dimensions.put("appprogramname",serviceName);

        // 度量值
        Map<String,Double> measures = new HashMap<>();
        measures.put("total", value);
        measures.put("used_bytes", value);
        measures.put("free", value);
        measures.put("used_pct", value);
        measures.put("actual_used_bytes", value);
        measures.put("actual_free", value);
        measures.put("actual_used_pct", value);
        measures.put("swap_total", value);
        measures.put("swap_used_bytes", value);
        measures.put("swap_free", value);
        measures.put("swap_used_pct", value);

        dataMap.put("dimensions", dimensions);
        dataMap.put("measures", measures);
        dataMap.put("metricsetname","memory_system_mb");
        dataMap.put("timestamp", timestamp);
        return dataMap;
    }

    public static Map<String,Object> createMetricTwo(long timestamp, String systemCode, String hostnema, String ip, String clusterName, String serviceName, Double value){
        Map<String, Object> dataMap = new HashMap<>(4);

        // 维度
        Map<String,String> dimensions = new HashMap<>(4);
        dimensions.put("hostname",hostnema);
        dimensions.put("ip",ip);
        dimensions.put("appsystem",systemCode);
        dimensions.put("clustername",clusterName);
        dimensions.put("servicename",serviceName);
        dimensions.put("appprogramname",serviceName);

        // 度量值
        Map<String,Double> measures = new HashMap<>();
        measures.put("read_count", value);
        measures.put("write_count", value);
        measures.put("read_bytes", value);
        measures.put("write_bytes", value);
        measures.put("read_time", value);
        measures.put("write_time", value);
        measures.put("io_time", value);
        measures.put("iostat_read_request_merges_per_sec", value);
        measures.put("iostat_write_request_merges_per_sec", value);
        measures.put("iostat_read_request_per_sec", value);
        measures.put("iostat_write_request_per_sec", value);
        measures.put("iostat_read_per_sec_bytes", value);
        measures.put("iostat_read_await", value);
        measures.put("iostat_write_per_sec_bytes", value);
        measures.put("iostat_write_await", value);
        measures.put("iostat_await", value);
        measures.put("iostat_service_time", value);
        measures.put("iostat_busy", value);

        dataMap.put("dimensions", dimensions);
        dataMap.put("measures", measures);
        dataMap.put("metricsetname","diskio_system_mb");
        dataMap.put("timestamp", timestamp);
        return dataMap;
    }

    public static void sendMetricAvro(KafkaProducer kafkaProducer, String tempStr){
        AvroSerializer avroSerializer = new AvroSerializer(AvroSchemaDef.ZORK_METRIC_SCHEMA);
        JSONObject data = JSONObject.parseObject(tempStr);
        // 序列化
        String metricsetname = data.getString("metricsetname");
        String timestamp = data.getString("timestamp");
        Map<String, String> dimensions = changeToMap(data.getJSONObject("dimensions"), 1);
        Map<String, Double> metrics = changeToMap(data.getJSONObject("measures"), 2);
        byte[] bytes = avroSerializer.serializingMetric(metricsetname, timestamp, dimensions, metrics);
        ProducerRecord<String, byte[]> msgtar = new ProducerRecord<String, byte[]>("metricdata", null, bytes);
        kafkaProducer.send(msgtar);
    }

    public static void sendMetricOne(KafkaProducer kafkaProducer, Map<String,Object> map){
        AvroSerializer avroSerializer = new AvroSerializer(AvroSchemaDef.ZORK_METRIC_SCHEMA);
        // 序列化
        String metricsetname = String.valueOf(map.get("metricsetname"));
        String timestamp = String.valueOf(map.get("timestamp"));
        Map<String, String> dimensions = (Map<String, String>)map.get("dimensions");
        Map<String, Double> metrics = (Map<String, Double>)map.get("measures");
        byte[] bytes = avroSerializer.serializingMetric(metricsetname, timestamp, dimensions, metrics);
        ProducerRecord<String, byte[]> msgtar = new ProducerRecord<String, byte[]>("dwd_all_metric", null, bytes);
        kafkaProducer.send(msgtar);
    }

    public static Map changeToMap(JSONObject jsonObject,Integer type){
        Map map = new HashMap();
        for(String key : jsonObject.keySet()){
            String value = String.valueOf(jsonObject.get(key));
            if (type == 1 ) {
                map.put(key, value);
            }else{
                map.put(key, Double.parseDouble(value));
            }
        }
        return map;
    }

}
