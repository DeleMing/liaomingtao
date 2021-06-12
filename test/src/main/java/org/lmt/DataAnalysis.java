package org.lmt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: LiaoMingtao
 * @date: 2021/5/28
 */
@Slf4j
public class DataAnalysis {

    private static final String EMPTY_STR = "";

    private static String getSpace(int spaceSize) {
        final String spaceStr = " ";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < spaceSize; i++) {
            result.append(spaceStr);
        }
        return result.toString();
    }

    private static String expressionToYml(String expression) {
        return String.format("- %s:", expression);
    }

    private static String generateYml(ProcessConfig processConfig) {
        StringBuilder result = new StringBuilder();
        List<ProcessConfig.ProcessParamConfig> processParamList = processConfig.getProcessParam();
        if (null == processParamList || processParamList.isEmpty()) {
            return EMPTY_STR;
        }
        List<String> conditionList = processParamList.stream().map(ProcessConfig.ProcessParamConfig::getCondition).collect(Collectors.toList());
        String conditionStr = conditionList.get(0);
        final String processors = "processors:";
        final String dropEvent = "- drop_event";
        final String when = "when:";
        String condition = "and:";
        final String andStr = "and";
        if (!andStr.equals(conditionStr)) {
            condition = "or:";
        }
        result.append(processors).append("\n")
                .append(getSpace(2)).append(dropEvent).append("\n")
                .append(getSpace(6)).append(when).append("\n")
                .append(getSpace(8)).append(condition).append("\n");
        for (ProcessConfig.ProcessParamConfig processParamConfig : processParamList) {
            String expression = processParamConfig.getExpression();
            result.append(getSpace(10)).append(expressionToYml(expression)).append("\n");
            String key = processParamConfig.getKey();
            String value = processParamConfig.getValue();
            result.append(getSpace(14)).append(key).append(getSpace(1)).append("\"").append(value).append("\"").append("\n");
        }
        return result.toString();
    }

    @SuppressWarnings("all")
    private static Map<String, Object> analysisJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return new HashMap<>(1);
        }
        String processName = "";
        String processCmdLine = "";
        String processStatusStr = "";
        String processStatus = "";
        double cpuUsedPct = 0.0;
        double memoryUsedPct = 0.0;
        long openFileSize = 0;
        long pid = 0l;
        Map<String, Object> sourceMap = JSONObject.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
        if (sourceMap.containsKey("process")) {
            Map<String, Object> processMap = (Map<String, Object>) MapUtils.getMap(sourceMap, "process");
            processName = MapUtils.getString(processMap, "name");
            pid = MapUtils.getLong(processMap, "pid");
        }
        if (sourceMap.containsKey("system")) {
            Map<String, Object> systemMap = (Map<String, Object>) MapUtils.getMap(sourceMap, "system");
            if (systemMap.containsKey("process")) {
                Map<String, Object> systemProcessMap = (Map<String, Object>) MapUtils.getMap(systemMap, "process");
                processStatusStr = MapUtils.getString(systemProcessMap, "state");
                processCmdLine = MapUtils.getString(systemProcessMap, "cmdline");
                try {
                    Map<String, Object> cpuMap = (Map<String, Object>) MapUtils.getMap(systemProcessMap, "cpu");
                    Map<String, Object> cpuTotalMap = MapUtils.getMap(cpuMap, "total");
                    Map<String, Object> cpuTotalNormMap = MapUtils.getMap(cpuTotalMap, "norm");
                    cpuUsedPct = MapUtils.getDoubleValue(cpuTotalNormMap, "pct");
                } catch (NullPointerException e) {
                    log.error("获取cpu使用率时出现空指针,数据不规范,使用率默认设置为0", e);
                }

                try {
                    Map<String, Object> memoryMap = (Map<String, Object>) MapUtils.getMap(systemProcessMap, "memory");
                    Map<String, Object> memoryRssMap = MapUtils.getMap(memoryMap, "rss");
                    memoryUsedPct = MapUtils.getDouble(memoryRssMap, "pct");
                } catch (NullPointerException e) {
                    log.error("获取内存使用率时出现空指针,数据不规范,使用率默认设置为0", e);
                }

                try {
                    Map<String, Object> fdMap = (Map<String, Object>) MapUtils.getMap(systemProcessMap, "fd");
                    openFileSize = MapUtils.getLong(fdMap, "open");
                } catch (NullPointerException e) {
                    log.error("获取文件打开数时出现空指针,数据不规范,使用率默认设置为0", e);
                }
            }
        }
        Map<String, Object> analysisMap = new HashMap<>(8);
        analysisMap.put("processName", processName);
        analysisMap.put("processCmdLine", processCmdLine);
        analysisMap.put("processStatusStr", processName);
        analysisMap.put("cpuUsedPct", cpuUsedPct);
        analysisMap.put("memoryUsedPct", memoryUsedPct);
        analysisMap.put("openFileSize", openFileSize);
        analysisMap.put("pid", pid);
        return analysisMap;
    }

    public static void main(String[] args) {
        // 方法一
        String temp = "{\n" +
                "    \"appprogramname\":\"linux模块\",\n" +
                "    \"user\":{\n" +
                "        \"name\":\"elk\"\n" +
                "    },\n" +
                "    \"metricset\":{\n" +
                "        \"name\":\"process\",\n" +
                "        \"period\":15000\n" +
                "    },\n" +
                "    \"servicename\":\"linux模块\",\n" +
                "    \"event\":{\n" +
                "        \"dataset\":\"system.process\",\n" +
                "        \"module\":\"system\",\n" +
                "        \"duration\":98824653\n" +
                "    },\n" +
                "    \"ip\":\"192.168.70.172\",\n" +
                "    \"transtime\":\"2021-05-21T02:57:36.805Z\",\n" +
                "    \"transip\":\"192.168.70.172\",\n" +
                "    \"service\":{\n" +
                "        \"type\":\"system\"\n" +
                "    },\n" +
                "    \"topicname\":\"ods_all_metric\",\n" +
                "    \"system\":{\n" +
                "        \"process\":{\n" +
                "            \"cmdline\":\"/zork/zkce/service/java/bin/java -Xms1g -Xmx1g -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -Des.networkaddress.cache.ttl=60 -Des.networkaddress.cache.negative.ttl=10 -XX:+AlwaysPreTouch -Xss1m -Djava.awt.headless=true -Dfile.encoding=UTF-8 -Djna.nosys=true -XX:-OmitStackTraceInFastThrow -Dio.netty.noUnsafe=true -Dio.netty.noKeySetOptimization=true -Dio.netty.recycler.maxCapacityPerThread=0 -Dlog4j.shutdownHookEnabled=false -Dlog4j2.disable.jmx=true -Djava.io.tmpdir=/tmp/elasticsearch-1355332158094167106 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=data -XX:ErrorFile=logs/hs_err_pid%p.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationStoppedTime -Xloggc:logs/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=32 -XX:GCLogFileSize=64m -Des.path.home=/zork/zkce/service/elasticsearch -Des.path.conf=/zork/zkce/service/elasticsearch/config -Des.distribution.flavor=default -Des.distribution.type=tar -cp /zork/zkce/service/elasticsearch/lib/* org.elasticsearch.bootstrap.Elasticsearch\",\n" +
                "            \"state\":\"sleeping\",\n" +
                "            \"fd\":{\n" +
                "                \"open\":1461,\n" +
                "                \"limit\":{\n" +
                "                    \"hard\":65536,\n" +
                "                    \"soft\":65536\n" +
                "                }\n" +
                "            },\n" +
                "            \"cpu\":{\n" +
                "                \"start_time\":\"2021-04-06T01:51:54.000Z\",\n" +
                "                \"total\":{\n" +
                "                    \"value\":\"1.8604008E9\",\n" +
                "                    \"norm\":{\n" +
                "                        \"pct\":0.0769\n" +
                "                    },\n" +
                "                    \"pct\":0.6154\n" +
                "                }\n" +
                "            },\n" +
                "            \"memory\":{\n" +
                "                \"size\":26293985280,\n" +
                "                \"rss\":{\n" +
                "                    \"pct\":0.0827,\n" +
                "                    \"bytes\":2074738688\n" +
                "                },\n" +
                "                \"share\":272699392\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"servicecode\":\"linux模块\",\n" +
                "    \"collectruleid\":9,\n" +
                "    \"appsystem\":\"dev_test\",\n" +
                "    \"ecs\":{\n" +
                "        \"version\":\"1.1.0\"\n" +
                "    },\n" +
                "    \"@version\":\"1\",\n" +
                "    \"@timestamp\":\"2021-05-21T02:57:35.599Z\",\n" +
                "    \"clustername\":\"基础监控\",\n" +
                "    \"process\":{\n" +
                "        \"name\":\"java\",\n" +
                "        \"args\":[\n" +
                "            \"/zork/zkce/service/java/bin/java\",\n" +
                "            \"-Xms1g\",\n" +
                "            \"-Xmx1g\",\n" +
                "            \"-XX:+UseConcMarkSweepGC\",\n" +
                "            \"-XX:CMSInitiatingOccupancyFraction=75\",\n" +
                "            \"-XX:+UseCMSInitiatingOccupancyOnly\",\n" +
                "            \"-Des.networkaddress.cache.ttl=60\",\n" +
                "            \"-Des.networkaddress.cache.negative.ttl=10\",\n" +
                "            \"-XX:+AlwaysPreTouch\",\n" +
                "            \"-Xss1m\",\n" +
                "            \"-Djava.awt.headless=true\",\n" +
                "            \"-Dfile.encoding=UTF-8\",\n" +
                "            \"-Djna.nosys=true\",\n" +
                "            \"-XX:-OmitStackTraceInFastThrow\",\n" +
                "            \"-Dio.netty.noUnsafe=true\",\n" +
                "            \"-Dio.netty.noKeySetOptimization=true\",\n" +
                "            \"-Dio.netty.recycler.maxCapacityPerThread=0\",\n" +
                "            \"-Dlog4j.shutdownHookEnabled=false\",\n" +
                "            \"-Dlog4j2.disable.jmx=true\",\n" +
                "            \"-Djava.io.tmpdir=/tmp/elasticsearch-1355332158094167106\",\n" +
                "            \"-XX:+HeapDumpOnOutOfMemoryError\",\n" +
                "            \"-XX:HeapDumpPath=data\",\n" +
                "            \"-XX:ErrorFile=logs/hs_err_pid%p.log\",\n" +
                "            \"-XX:+PrintGCDetails\",\n" +
                "            \"-XX:+PrintGCDateStamps\",\n" +
                "            \"-XX:+PrintTenuringDistribution\",\n" +
                "            \"-XX:+PrintGCApplicationStoppedTime\",\n" +
                "            \"-Xloggc:logs/gc.log\",\n" +
                "            \"-XX:+UseGCLogFileRotation\",\n" +
                "            \"-XX:NumberOfGCLogFiles=32\",\n" +
                "            \"-XX:GCLogFileSize=64m\",\n" +
                "            \"-Des.path.home=/zork/zkce/service/elasticsearch\",\n" +
                "            \"-Des.path.conf=/zork/zkce/service/elasticsearch/config\",\n" +
                "            \"-Des.distribution.flavor=default\",\n" +
                "            \"-Des.distribution.type=tar\",\n" +
                "            \"-cp\",\n" +
                "            \"/zork/zkce/service/elasticsearch/lib/*\",\n" +
                "            \"org.elasticsearch.bootstrap.Elasticsearch\"\n" +
                "        ],\n" +
                "        \"ppid\":1,\n" +
                "        \"pid\":1320,\n" +
                "        \"pgid\":1320,\n" +
                "        \"executable\":\"/zork/zkce/service/java/bin/java\",\n" +
                "        \"working_directory\":\"/zork/zkce/service/elasticsearch\"\n" +
                "    },\n" +
                "    \"tags\":[\n" +
                "        \"beats_input_raw_event\"\n" +
                "    ],\n" +
                "    \"collecttime\":\"2021-05-21T02:57:35.599Z\",\n" +
                "    \"host\":{\n" +
                "        \"name\":\"yf172\",\n" +
                "        \"hostname\":\"yf172\",\n" +
                "        \"architecture\":\"x86_64\",\n" +
                "        \"containerized\":false,\n" +
                "        \"id\":\"560a9e4ea53645668420a2c269bf8fe4\",\n" +
                "        \"os\":{\n" +
                "            \"name\":\"CentOS Linux\",\n" +
                "            \"kernel\":\"3.10.0-1062.el7.x86_64\",\n" +
                "            \"platform\":\"centos\",\n" +
                "            \"version\":\"7 (Core)\",\n" +
                "            \"family\":\"redhat\",\n" +
                "            \"codename\":\"Core\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"agent\":{\n" +
                "        \"id\":\"779557a4-0b5b-4ecc-a969-e1c8429d0221\",\n" +
                "        \"ephemeral_id\":\"9fe1a628-1bfe-4b87-9624-0e9e51f3b9ec\",\n" +
                "        \"hostname\":\"yf172\",\n" +
                "        \"version\":\"7.4.0\",\n" +
                "        \"type\":\"metricbeat\"\n" +
                "    }\n" +
                "}";
        Map<String, Object> stringObjectMap = analysisJson(temp);
        log.info(JSON.toJSONString(stringObjectMap));


        // 方法二
        ProcessConfig processConfig = new ProcessConfig();
        processConfig.setProcessName("java");
        List<ProcessConfig.ProcessParamConfig> processParamConfigList = new ArrayList<>();

        ProcessConfig.ProcessParamConfig processParamConfig = new ProcessConfig.ProcessParamConfig();
        processParamConfig.setKey("process.args");
        processParamConfig.setValue("aaa");
        processParamConfig.setExpression("equals");
        processParamConfig.setCondition("and");

        ProcessConfig.ProcessParamConfig processParamConfig2 = new ProcessConfig.ProcessParamConfig();
        processParamConfig2.setKey("system.process.cmdline");
        processParamConfig2.setValue("bbb");
        processParamConfig2.setExpression("not.equals");
        processParamConfig2.setCondition("and");

        processParamConfigList.add(processParamConfig);
        processParamConfigList.add(processParamConfig2);
        processConfig.setProcessParam(processParamConfigList);
        String s = generateYml(processConfig);
        log.info("\n" + s);

        // 传入值处理
        List<ProcessConfigDto> tempList = new ArrayList<>();
        ProcessConfigDto processConfigDto = new ProcessConfigDto();
        processConfigDto.setProcessName("java");
        processConfigDto.setProcessParam(s);
        tempList.add(processConfigDto);
        log.info(JSON.toJSONString(tempList));
    }
}
