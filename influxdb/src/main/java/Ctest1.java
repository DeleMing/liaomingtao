import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.List;

/**
 * @author: LiaoMingtao
 * @date: 2022/9/5
 */
@Slf4j
public class Ctest1 {

    public static void main(String[] args) {
        InfluxDB connect = InfluxUtils.createConnect("http://192.168.70.92:8086", "admin", "admin");
        String sql = "select * from asset_controllerBattery limit 1";
        // String sql = "select * from cpu_system_mb limit 1";
        QueryResult query = connect.query(new Query(sql, "dwd_all_metric"));
        List<QueryResult.Result> results = query.getResults();
        if (results.size() == 0) {
            log.error("运行sql:{},查询结果为空:{}", sql, JSON.toJSONString(results));
        }
        List<QueryResult.Series> series = results.get(0).getSeries();
        if (StrUtil.isNotBlank(results.get(0).getError()) || null == series || series.isEmpty()) {
            log.error("运行sql:{},查询结果为空串:{}", sql, JSON.toJSONString(results));
        }
    }
}
