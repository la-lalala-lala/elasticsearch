package ac.cn.saya.elasticsearch.rest.tools;

import ac.cn.saya.elasticsearch.rest.config.ESRestClientConfig;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Title: ElasticSearchDao
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author liunengkai
 * @Date: 6/23/21 20:56
 * @Description: ElasticSearch基础操作类
 */
@Repository
public class ElasticSearchDao {

    @Resource
    RestHighLevelClient client;

    /**
     * 查询前n条条
     * @param query 查询条件
     * @param size 返回的数据量
     * @param index 索引
     * @return
     */
    public List<Map<String,Object>> getListByQuery(QueryBuilder query,int size, List<SortBuilder<?>> sorts,String... index) {
        List<Map<String,Object>> rs = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(size>1?size:100);
        for (SortBuilder<?> sort : sorts) {
            sourceBuilder.sort(sort);
        }
        try {
            SearchResponse response = client.search(searchRequest, ESRestClientConfig.COMMON_OPTIONS);
            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {
                String recordStr = hit.getSourceAsString();
                Map<String,Object> map = JackJsonUtil.readValue(recordStr, Map.class);
                rs.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 分页获取对象
     * @param pageNum  页码
     * @param pageSize 页大小
     * @param query    查询条件
     * @return
     */
    public Paging<Object> getListByQuery(int pageNum, int pageSize, QueryBuilder query, List<SortBuilder<?>> sorts,String... index) {
        Paging<Object> page = new Paging<>();
        List<Map<String,Object>> rs = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        int from = Math.max((pageNum - 1) * pageSize, 0);
        sourceBuilder.from(from);
        sourceBuilder.size(pageSize);
        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);
        for (SortBuilder<?> sort : sorts) {
            sourceBuilder.sort(sort);
        }
        long totalCount = 0L;
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            totalCount = response.getHits().getTotalHits().value;
            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {
                String recordStr = hit.getSourceAsString();
                Map<String,Object> map = JackJsonUtil.readValue(recordStr, Map.class);
                rs.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        page.setPageSize(pageSize);
        page.setDateSum(totalCount);
        page.setTotalPage();
        page.setPageNow(pageNum);
        page.setGrid(rs);
        return page;
    }


}
