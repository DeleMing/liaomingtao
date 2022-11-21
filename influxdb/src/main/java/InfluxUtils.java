import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liaomingtao
 */
@Slf4j
public class InfluxUtils {
    static OkHttpClient.Builder client = new OkHttpClient.Builder().readTimeout(100000, TimeUnit.SECONDS);

    /**
     * 连接时序数据库 ，若不存在则创建
     *
     * @param openurl  协议+ip
     * @param username user
     * @param password pwd
     * @return connection
     */
    public static InfluxDB createConnect(String openurl, String username, String password) {
        return InfluxDBFactory.connect(openurl, username, password, client);
    }

    /**
     * 测试连接是否正常
     *
     * @return true 正常
     */
    public static boolean ping(InfluxDB influxDb) throws Exception {
        boolean isConnected = false;
        Pong pong = influxDb.ping();
        if (pong != null) {
            isConnected = true;
        }
        return isConnected;
    }

    /**
     * 获取Influx中的所有数据源
     *
     * @param influxDb 连接信息
     * @param database 库名
     * @return 表集合
     */
    public List<String> getDataBases(InfluxDB influxDb, String database) throws Exception {
        QueryResult query = influxDb.query(new Query("show databases", database));
        List<QueryResult.Result> results = query.getResults();
        if (null != results.get(0).getError() && !"".equals(results.get(0).getError())) {
            log.error(query.toString());
            throw new Exception("异常");
        }
        List<String> tableList = new ArrayList<>();
        List<List<Object>> values = results.get(0).getSeries().get(0).getValues();
        for (List<Object> value : values) {
            tableList.add(String.valueOf(value.get(0)));
        }
        return tableList;
    }

    /**
     * 获取表信息
     *
     * @param influxDb 连接信息
     * @param database 库名
     * @return 表集合
     */
    public void getTable(InfluxDB influxDb, String database) throws Exception {
        QueryResult query = influxDb.query(new Query("show measurements", database));
        List<QueryResult.Result> results = query.getResults();
        if (null != results.get(0).getError() && !"".equals(results.get(0).getError())) {
            log.error(query.toString());
            throw new Exception("异常");
        }
    }

    /**
     * 获取表中的字段信息
     *
     * @param influxDb  连接
     * @param database  数据库
     * @param tableName 表名
     * @return r
     * @throws Exception e
     */
    public void getField(InfluxDB influxDb, String database, String tableName) throws Exception {
        QueryResult query = influxDb.query(new Query("select * from " + tableName + " limit 1", database));
        List<QueryResult.Result> results = query.getResults();
        if (null != results.get(0).getError() && !"".equals(results.get(0).getError())) {
            log.error(query.toString());
            throw new Exception("异常");
        }
        List<String> columns = results.get(0).getSeries().get(0).getColumns();
    }

    /**
     * 批量写入数据
     *
     * @param database        数据库
     * @param retentionPolicy 保存策略
     * @param consistency     一致性
     * @param records         要保存的数据（调用BatchPoints.lineProtocol()可得到一条record）
     */
    public void batchInsert(InfluxDB influxDb, final String database, final String retentionPolicy, final InfluxDB.ConsistencyLevel consistency,
                            final List<String> records) {
        influxDb.write(database, retentionPolicy, consistency, records);
    }

    /**
     * 查询数据
     */
    public List<QueryResult.Series> query(InfluxDB influxDb, String database, String command) {
        QueryResult query = influxDb.query(new Query(command, database));
        log.info("InfluxDB查询结果：" + query.toString());
        return query.getResults().get(0).getSeries();
    }

    /**
     * 关闭数据库
     */
    public void close(InfluxDB influxDb) {
        influxDb.close();
    }

}
