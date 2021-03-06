package org.lmt.avro;

/**
 * avro schema 定义
 *
 * @author Administrator
 */
public final class AvroSchemaDef {

    /**
     * 指标schema定义
     */
    public static final String ZORK_METRIC_SCHEMA = "{\n" +
            "    \"namespace\": \"com.zork.metrics\",\n" +
            "    \"type\": \"record\",\n" +
            "    \"name\": \"metrics\",\n" +
            "    \"fields\": [\n" +
            "        {\n" +
            "            \"name\": \"metricsetname\",\n" +
            "            \"type\": [\n" +
            "                \"string\",\n" +
            "                \"null\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"timestamp\",\n" +
            "            \"type\": [\n" +
            "                \"string\",\n" +
            "                \"null\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"dimensions\",\n" +
            "            \"type\": [\n" +
            "                \"null\",\n" +
            "                {\n" +
            "                    \"type\": \"map\",\n" +
            "                    \"values\": \"string\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"metrics\",\n" +
            "            \"type\": [\n" +
            "                \"null\",\n" +
            "                {\n" +
            "                    \"type\": \"map\",\n" +
            "                    \"values\": \"double\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    /**
     * 日志指标集定义
     */
    public static final String ZORK_LOG_SCHEMA = "{\n" +
            "    \"namespace\": \"com.zork.logs\",\n" +
            "    \"type\": \"record\",\n" +
            "    \"name\": \"logs\",\n" +
            "    \"fields\": [\n" +
            "        {\n" +
            "            \"name\": \"logTypeName\",\n" +
            "            \"type\": [\n" +
            "                \"string\",\n" +
            "                \"null\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"timestamp\",\n" +
            "            \"type\": [\n" +
            "                \"string\",\n" +
            "                \"null\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"source\",\n" +
            "            \"type\": [\n" +
            "                \"string\",\n" +
            "                \"null\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"offset\",\n" +
            "            \"type\": [\n" +
            "                \"string\",\n" +
            "                \"null\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"dimensions\",\n" +
            "            \"type\": [\n" +
            "                \"null\",\n" +
            "                {\n" +
            "                    \"type\": \"map\",\n" +
            "                    \"values\": \"string\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"measures\",\n" +
            "            \"type\": [\n" +
            "                \"null\",\n" +
            "                {\n" +
            "                    \"type\": \"map\",\n" +
            "                    \"values\": \"double\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"normalFields\",\n" +
            "            \"type\": [\n" +
            "                \"null\",\n" +
            "                {\n" +
            "                    \"type\": \"map\",\n" +
            "                    \"values\": \"string\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private AvroSchemaDef() {

    }
}
