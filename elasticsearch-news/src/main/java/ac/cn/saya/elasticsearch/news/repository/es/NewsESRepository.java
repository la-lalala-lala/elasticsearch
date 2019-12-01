package ac.cn.saya.elasticsearch.news.repository.es;

import ac.cn.saya.elasticsearch.news.entity.es.NewsESEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Title: NewsESRepository
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/24 0024 14:48
 * @Description:
 * 动态表索引持久层
 */
@Repository
public interface NewsESRepository extends ElasticsearchRepository<NewsESEntity,Integer> {
}
