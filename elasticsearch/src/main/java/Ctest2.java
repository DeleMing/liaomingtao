import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: LiaoMingtao
 * @date: 2022/11/22
 */
public class Ctest2 {

    public static void main(String[] args) throws IOException {
        RestClientBuilder http = RestClient.builder(new HttpHost("192.168.3.21", 9200, "http"));
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(http);

        SearchRequest request = new SearchRequest("swhy");
        request.types("performance");

        // 指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.boolQuery().mustNot(QueryBuilders.matchPhraseQuery("categorys.major_group", "操作系统")));
        // 准确计数
        builder.trackTotalHits(true);
        // 超时时间60s
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        request.source(builder);
        request.scroll("1m");
        System.out.println("查询语句:" + builder.toString());
        //3. 执行查询
        SearchResponse resp = restHighLevelClient.search(request);

        // 初始化查询结果List
        List<String> jsonStringList = new ArrayList<>();
        // 获取第一页的查询结果
        SearchHit[] searchHits = resp.getHits().getHits();
        for (SearchHit hit : searchHits) {
            jsonStringList.add(hit.getSourceAsString());
        }
        // 获取ScrollId
        String scrollId = resp.getScrollId();
        // 返回结果不为空则滚动查询
        while (searchHits != null && searchHits.length > 0) {
            // 初始化scroll查询
            SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
            searchScrollRequest.scroll("1m");
            // 发起请求并接收响应
            resp = restHighLevelClient.searchScroll(searchScrollRequest, RequestOptions.DEFAULT);
            // 更新ScrollId
            scrollId = resp.getScrollId();
            // 更新查询结果
            searchHits = resp.getHits().getHits();
            // 放入List
            for (SearchHit hit : searchHits) {
                jsonStringList.add(hit.getSourceAsString());
            }
        }
        //4. 输出结果
        // for (SearchHit hit : resp.getHits().getHits()) {
        //     System.out.println(hit.getSourceAsMap());
        // }

        System.out.println(resp.getHits().getHits().length);
        System.out.println(jsonStringList.size());
    }
}
