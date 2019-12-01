package ac.cn.saya.elasticsearch.news.controller;

import ac.cn.saya.elasticsearch.news.entity.es.NewsESEntity;
import ac.cn.saya.elasticsearch.news.entity.mysql.NewsDBEntity;
import ac.cn.saya.elasticsearch.news.repository.es.NewsESRepository;
import ac.cn.saya.elasticsearch.news.repository.mysql.NewsDBRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Title: NewsController
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/24 0024 14:07
 * @Description:
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsDBRepository newsDBRepository;

    @Autowired
    private NewsESRepository newsESRepository;

    @GetMapping("/count")
    public String getNewsSize(){
        return "news-count："+newsDBRepository.count();
    }

    @GetMapping(value = "/search/{type}")
    public JSONObject search(@PathVariable("type") String type, @RequestParam("value") String value){
        JSONObject jsonObject = new JSONObject();
        StopWatch watch = new StopWatch();
        watch.start();
        if ("es".equals(type)){
            // es
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title",value));
            builder.should(QueryBuilders.matchPhraseQuery("content",value));
            Page<NewsESEntity> search = (Page<NewsESEntity>) newsESRepository.search(builder);
            jsonObject.put("result",search.getContent());
            jsonObject.put("tyepe","ElasticSearch");
        }else {
            // mysql
            jsonObject.put("result",newsDBRepository.search(value));
            jsonObject.put("tyepe","MySQL");
        }
        watch.stop();
        jsonObject.put("search-time",watch.getTotalTimeMillis());
        return jsonObject;
    }

}
