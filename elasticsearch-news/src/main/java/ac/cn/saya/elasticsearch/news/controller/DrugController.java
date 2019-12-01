package ac.cn.saya.elasticsearch.news.controller;

import ac.cn.saya.elasticsearch.news.entity.es.DrugESEntity;
import ac.cn.saya.elasticsearch.news.entity.mysql.DrugDBEntity;
import ac.cn.saya.elasticsearch.news.repository.es.DrugESRepository;
import ac.cn.saya.elasticsearch.news.repository.mysql.DrugDBRepository;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: DrugController
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/28 0028 14:46
 * @Description:
 */
@RestController
@RequestMapping(value = "/drug")
public class DrugController {


    private static final String DRUG_INDEX_NAME = "drug";

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private DrugESRepository drugESRepository;

    @Autowired
    private DrugDBRepository drugDBRepository;

    // http://127.0.0.1:8080/drug/es/batch
    @GetMapping(value = "/es/batch")
    public JSONObject batchToES(){
        JSONObject result = new JSONObject();
        List<DrugDBEntity> list = drugDBRepository.findAll();
        int size = list.size();
        if (size>0){
            // 防止抛出异常，用之前进行一次判断
            boolean indexExists = elasticsearchTemplate.indexExists(DRUG_INDEX_NAME);
            if (indexExists){
                // 索引存在则清除之前的数据
                drugESRepository.deleteAll();
            }
            for (DrugDBEntity item:list) {
                DrugESEntity es = new DrugESEntity(
                        item.getDrugId(),
                        item.getCnName(),
                        item.getPinyin(),
                        item.getCommonName(),
                        item.getApprNumber(),
                        item.getCompanyName(),
                        item.getComponent(),
                        item.getIndication(),
                        item.getDosage(),
                        item.getAdversereactions(),
                        item.getPrecautions()
                );
                drugESRepository.save(es);
            }
            result.put("code", "0");
            result.put("msg", "同步成功");
        }else {
            result.put("code", "-1");
            result.put("msg", "数据库中没有记录");
        }
        return result;
    }

    /**
     * 通过名称进行检索
     * @param type
     * @param name
     * @return
     */
    // http://127.0.0.1:8080/drug/search/name/es?name=
    @GetMapping(value = "/search/name/{type}")
    public JSONObject searchByName(@PathVariable("type") String type, @RequestParam("name") String name){
        JSONObject result = new JSONObject();
        StopWatch watch = new StopWatch();
        if ("es".equals(type)){
            // 防止抛出异常，用之前进行一次判断
            boolean indexExists = elasticsearchTemplate.indexExists(DRUG_INDEX_NAME);
            if (!indexExists){
                result.put("msg", "查询失败，索引不存在");
                result.put("code", "-1");
                return result;
            }
            watch.start();
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("cnName", name));
            builder.should(QueryBuilders.matchPhraseQuery("commonName", name));
            Page<DrugESEntity> search = (Page<DrugESEntity>) drugESRepository.search(builder);
            watch.stop();
            result.put("data", search);
            result.put("code", "0");
        }else {
            watch.start();
            result.put("data", drugDBRepository.queryByName(name));
            watch.stop();
            result.put("code", "0");
        }
        result.put("msg", "查询成功");
        result.put("search-time",watch.getTotalTimeMillis());
        return result;
    }

    @GetMapping(value = "/search/appr/{type}")
    public JSONObject searchByAppr(@PathVariable("type") String type, @RequestParam("name") String name){
        JSONObject result = new JSONObject();
        StopWatch watch = new StopWatch();
        if ("es".equals(type)) {
            // 防止抛出异常，用之前进行一次判断
            boolean indexExists = elasticsearchTemplate.indexExists(DRUG_INDEX_NAME);
            if (!indexExists) {
                result.put("msg", "查询失败，索引不存在");
                result.put("code", "-1");
                return result;
            }
            watch.start();
            Page<DrugESEntity> search = (Page<DrugESEntity>) drugESRepository.findByAppr(name,new PageRequest(0,1000));
            watch.stop();
            result.put("data", search);
            result.put("code", "0");
        }else {
            watch.start();
            result.put("data", drugDBRepository.queryByAppr(name));
            watch.stop();
            result.put("code", "0");
        }
        result.put("msg", "查询成功");
        result.put("search-time",watch.getTotalTimeMillis());
        return result;
    }

    // http://127.0.0.1:8080/drug/search/component/es0?name=盐酸
    @GetMapping(value = "/search/component/{type}")
    public JSONObject searchByComponent(@PathVariable("type") String type, @RequestParam("name") String name){
        JSONObject result = new JSONObject();
        StopWatch watch = new StopWatch();
        if ("es".equals(type)) {
            // 防止抛出异常，用之前进行一次判断
            boolean indexExists = elasticsearchTemplate.indexExists(DRUG_INDEX_NAME);
            if (!indexExists) {
                result.put("msg", "查询失败，索引不存在");
                result.put("code", "-1");
                return result;
            }
            watch.start();
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("component", name));
            Page<DrugESEntity> search = (Page<DrugESEntity>) drugESRepository.search(builder);
            watch.stop();
            result.put("data", search);
            result.put("code", "0");
        }else {
            watch.start();
            result.put("data", drugDBRepository.queryByComponent(name));
            watch.stop();
            result.put("code", "0");
        }
        result.put("msg", "查询成功");
        result.put("search-time",watch.getTotalTimeMillis());
        return result;
    }

    // http://127.0.0.1:8080/drug/search/indication/es?name=呼吸道感染
    @GetMapping(value = "/search/indication/{type}")
    public JSONObject searchByIndication(@PathVariable("type") String type, @RequestParam("name") String name){
        JSONObject result = new JSONObject();
        StopWatch watch = new StopWatch();
        if ("es".equals(type)) {
            // 防止抛出异常，用之前进行一次判断
            boolean indexExists = elasticsearchTemplate.indexExists(DRUG_INDEX_NAME);
            if (!indexExists) {
                result.put("msg", "查询失败，索引不存在");
                result.put("code", "-1");
                return result;
            }
            watch.start();
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("indication", name));
            Page<DrugESEntity> search = (Page<DrugESEntity>) drugESRepository.search(builder);
            watch.stop();
            result.put("data", search);
            result.put("code", "0");
        }else {
            watch.start();
            result.put("data", drugDBRepository.queryByIndication(name));
            watch.stop();
            result.put("code", "0");
        }
        result.put("msg", "查询成功");
        result.put("search-time",watch.getTotalTimeMillis());
        return result;
    }

}
