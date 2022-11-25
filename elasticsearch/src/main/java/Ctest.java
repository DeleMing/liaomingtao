// import org.apache.http.HttpHost;
// import org.elasticsearch.action.search.SearchRequest;
// import org.elasticsearch.action.search.SearchResponse;
// import org.elasticsearch.client.RestClient;
//
// import org.elasticsearch.client.RestHighLevelClient;
// import org.elasticsearch.index.query.QueryBuilders;
//
// import org.elasticsearch.search.builder.SearchSourceBuilder;
//
// import java.io.IOException;
//
// /**
//  * @author: LiaoMingtao
//  * @date: 2022/11/22
//  */
// public class Ctest {
//
//     public static void main(String[] args) throws IOException {
//         RestClient http = RestClient.builder(new HttpHost("192.168.3.21", 9200, "http")).build();
//         RestHighLevelClient restHighLevelClient = new RestHighLevelClient(http);
//         SearchRequest request = new SearchRequest("swhy");
//         request.types("performance");
//
//         // 指定查询条件
//         SearchSourceBuilder builder = new SearchSourceBuilder();
//         builder.query(QueryBuilders.boolQuery().mustNot(QueryBuilders.matchPhraseQuery("categorys.major_group", "操作系统")));
//         // ES默认只查询10条数据，如果想查询更多，添加size
//         builder.size(10000);
//
//
//         request.source(builder);
//         System.out.println("查询语句:" + builder.toString());
//         //3. 执行查询
//         SearchResponse resp = restHighLevelClient.search(request);
//
//         //4. 输出结果
//         // for (SearchHit hit : resp.getHits().getHits()) {
//         //     System.out.println(hit.getSourceAsMap());
//         // }
//
//         System.out.println(resp.getHits().getHits().length);
//         http.close();
//     }
// }
