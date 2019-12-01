package ac.cn.saya.elasticsearch.news.repository.es;

import ac.cn.saya.elasticsearch.news.entity.es.DrugESEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Title: DrugESRepository
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/28 0028 14:40
 * @Description:
 */
@Repository
public interface DrugESRepository extends ElasticsearchRepository<DrugESEntity,Integer> {

    //@Query("{ \"match\": { \"apprNumber\": ?0 }}")
    // wildcard不识别大写，需要将查询的词转为小写
    @Query("{ \"wildcard\": {\n" +
            "      \"apprNumber\": {\n" +
            "        \"value\": \"*?0*\"\n" +
            "      }\n" +
            "    }}")
    public Page<DrugESEntity> findByAppr(String name, Pageable pageable);

}