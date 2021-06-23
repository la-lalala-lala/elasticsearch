package ac.cn.saya.elasticsearch.rest;

import ac.cn.saya.elasticsearch.rest.tools.ElasticSearchDao;
import ac.cn.saya.elasticsearch.rest.tools.JackJsonUtil;
import ac.cn.saya.elasticsearch.rest.tools.Paging;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Title: ElasticSearchDaoTest
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author liunengkai
 * @Date: 6/23/21 21:14
 * @Description:
 */
@SpringBootTest
public class ElasticSearchDaoTest {

    @Autowired
    private ElasticSearchDao elasticSearchDao;

    @Test
    public void testGetListByQuery1(){
        MatchQueryBuilder where = QueryBuilders.matchQuery("address", "Place");
        List<Map<String, Object>> list = elasticSearchDao.getListByQuery(where, 10, Collections.singletonList(SortBuilders.fieldSort("address").order(SortOrder.ASC)), "bank");
        for (Map<String, Object> item:list) {
            System.out.println(JackJsonUtil.toJson(item,false));
        }
    }

    @Test
    public void testGetListByQuery2(){
        MatchQueryBuilder where = QueryBuilders.matchQuery("address", "Place");
        Paging<Object> result = elasticSearchDao.getListByQuery(1, 10, where, Collections.singletonList(SortBuilders.fieldSort("address").order(SortOrder.ASC).unmappedType("long")), "bank");
        return;
    }

}
