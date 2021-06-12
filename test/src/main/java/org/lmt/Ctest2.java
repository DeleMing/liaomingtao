package org.lmt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author: LiaoMingtao
 * @date: 2021/3/31
 */
public class Ctest2 {
    static String a = "[\n" +
            "            {\n" +
            "                \"bk_biz_id\": 2,\n" +
            "                \"bk_biz_code\": \"lanjing\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"\",\n" +
            "                \"bk_biz_maintainer\": \"admin,binbin,jinyajie,zhongwenjun\",\n" +
            "                \"bk_biz_tester\": \"\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-03-03T10:59:09.094+08:00\",\n" +
            "                \"bk_biz_productor\": \"\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"\",\n" +
            "                \"original_id\": \"\",\n" +
            "                \"bk_biz_name\": \"蓝鲸\",\n" +
            "                \"last_time\": \"2021-05-26T15:33:12.306+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 3,\n" +
            "                \"bk_biz_code\": \"dev_test\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"\",\n" +
            "                \"bk_biz_maintainer\": \"admin,binbin,zhongwenjun,haobenkun,shaojiao,liuwang,cuiwu,wangke,jinyajie,liaomingtao,wangmin,nihongxin,lisi\",\n" +
            "                \"bk_biz_tester\": \"\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-03-03T11:25:24.695+08:00\",\n" +
            "                \"bk_biz_productor\": \"\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"\",\n" +
            "                \"original_id\": \"\",\n" +
            "                \"bk_biz_name\": \"研发部测试\",\n" +
            "                \"last_time\": \"2021-05-26T15:40:00.982+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 4,\n" +
            "                \"bk_biz_code\": \"network\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"\",\n" +
            "                \"bk_biz_maintainer\": \"admin,binbin,wangmin,lisi\",\n" +
            "                \"bk_biz_tester\": \"\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-03-10T11:12:14.016+08:00\",\n" +
            "                \"bk_biz_productor\": \"\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"\",\n" +
            "                \"original_id\": \"\",\n" +
            "                \"bk_biz_name\": \"网络日志\",\n" +
            "                \"last_time\": \"2021-05-26T15:39:46.642+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 5,\n" +
            "                \"bk_biz_code\": \"\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"\",\n" +
            "                \"bk_biz_maintainer\": \"binbin,nihongxin,admin\",\n" +
            "                \"bk_biz_tester\": \"\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-03-19T17:27:04.731+08:00\",\n" +
            "                \"bk_biz_productor\": \"\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"\",\n" +
            "                \"bk_biz_name\": \"业务测试\",\n" +
            "                \"last_time\": \"2021-04-14T10:15:30.464+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 6,\n" +
            "                \"bk_biz_code\": \"qt1\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"\",\n" +
            "                \"bk_biz_maintainer\": \"liuwang,binbin,nihongxin,shaojiao,wangmin,admin,zhongwenjun\",\n" +
            "                \"bk_biz_tester\": \"\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-03-24T13:31:18.349+08:00\",\n" +
            "                \"bk_biz_productor\": \"\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"\",\n" +
            "                \"original_id\": \"\",\n" +
            "                \"bk_biz_name\": \"千台机器系统1\",\n" +
            "                \"last_time\": \"2021-05-08T15:07:15.429+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 7,\n" +
            "                \"bk_biz_code\": \"QT2\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"\",\n" +
            "                \"bk_biz_maintainer\": \"liuwang,binbin,wangmin\",\n" +
            "                \"bk_biz_tester\": \"\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-03-24T13:53:49.451+08:00\",\n" +
            "                \"bk_biz_productor\": \"\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"\",\n" +
            "                \"bk_biz_name\": \"千台机器系统2\",\n" +
            "                \"last_time\": \"2021-04-09T10:18:12.003+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 8,\n" +
            "                \"bk_biz_code\": \"sync_test_1\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"synctest\",\n" +
            "                \"bk_biz_maintainer\": \"synctest,wangmin,binbin,admin,shaojiao\",\n" +
            "                \"bk_biz_tester\": \"synctest\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-03-30T10:54:50.166+08:00\",\n" +
            "                \"bk_biz_productor\": \"synctest\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"synctest\",\n" +
            "                \"bk_biz_name\": \"同步测试业务1\",\n" +
            "                \"last_time\": \"2021-04-07T16:00:38.449+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 9,\n" +
            "                \"bk_biz_code\": \"sync_test_2\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"synctest\",\n" +
            "                \"bk_biz_maintainer\": \"synctest,wangmin,binbin,admin,shaojiao\",\n" +
            "                \"bk_biz_tester\": \"synctest\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-03-30T15:04:51.133+08:00\",\n" +
            "                \"bk_biz_productor\": \"synctest\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"synctest\",\n" +
            "                \"bk_biz_name\": \"同步测试业务2\",\n" +
            "                \"last_time\": \"2021-04-07T16:00:51.324+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 10,\n" +
            "                \"bk_biz_code\": \"sync_test_3\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"synctest\",\n" +
            "                \"bk_biz_maintainer\": \"synctest,wangmin,binbin,admin,shaojiao\",\n" +
            "                \"bk_biz_tester\": \"synctest\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-03-30T15:48:21.236+08:00\",\n" +
            "                \"bk_biz_productor\": \"synctest\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"synctest\",\n" +
            "                \"bk_biz_name\": \"同步测试业务3\",\n" +
            "                \"last_time\": \"2021-04-07T16:00:58.003+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 11,\n" +
            "                \"bk_biz_code\": \"alarm\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"\",\n" +
            "                \"bk_biz_maintainer\": \"admin,wangmin\",\n" +
            "                \"bk_biz_tester\": \"\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-03-31T15:35:54.425+08:00\",\n" +
            "                \"bk_biz_productor\": \"\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"\",\n" +
            "                \"bk_biz_name\": \"告警业务测试\",\n" +
            "                \"last_time\": \"2021-03-31T17:34:36.264+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 12,\n" +
            "                \"bk_biz_code\": \"icubeserror\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"\",\n" +
            "                \"bk_biz_maintainer\": \"admin,binbin\",\n" +
            "                \"bk_biz_tester\": \"\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-04-08T14:59:46.953+08:00\",\n" +
            "                \"bk_biz_productor\": \"\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"\",\n" +
            "                \"bk_biz_name\": \"错误日志系统\",\n" +
            "                \"last_time\": \"2021-04-08T14:59:46.953+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 13,\n" +
            "                \"bk_biz_code\": \"kongxitong\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": \"\",\n" +
            "                \"bk_biz_maintainer\": \"binbin,admin,nihongxin,wangke\",\n" +
            "                \"bk_biz_tester\": \"\",\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-04-08T16:18:59.418+08:00\",\n" +
            "                \"bk_biz_productor\": \"\",\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": \"\",\n" +
            "                \"bk_biz_name\": \"空系统\",\n" +
            "                \"last_time\": \"2021-04-13T15:20:17.22+08:00\",\n" +
            "                \"bk_supplier_id\": 0\n" +
            "            },\n" +
            "            {\n" +
            "                \"bk_biz_id\": 15,\n" +
            "                \"bk_biz_code\": \"uyun\",\n" +
            "                \"language\": \"1\",\n" +
            "                \"life_cycle\": \"2\",\n" +
            "                \"bk_biz_developer\": null,\n" +
            "                \"bk_biz_maintainer\": \"admin\",\n" +
            "                \"bk_biz_tester\": null,\n" +
            "                \"time_zone\": \"Asia/Shanghai\",\n" +
            "                \"default\": 0,\n" +
            "                \"create_time\": \"2021-04-15T11:13:19.604+08:00\",\n" +
            "                \"bk_biz_productor\": null,\n" +
            "                \"bk_supplier_account\": \"0\",\n" +
            "                \"operator\": null,\n" +
            "                \"original_id\": \"5f683effd179d730754290b3\",\n" +
            "                \"bk_biz_name\": \"IT运维管理系统2.0(UYUN)\",\n" +
            "                \"last_time\": \"2021-04-15T11:13:19.604+08:00\",\n" +
            "                \"bk_supplier_id\": \"0\"\n" +
            "            }\n" +
            "        ]";

    public static void main(String[] args) {
        List<Map<String, Object>> maps = JSON.parseObject(a, new TypeReference<List<Map<String, Object>>>() {
        });
        StringBuilder b = new StringBuilder();
        for (Map<String, Object> map : maps) {
            Object bkBizId = map.get("bk_biz_id");
            String s = bkBizId.toString();
            b.append(s).append(",");
        }
        System.out.println(b.toString());
    }
}
