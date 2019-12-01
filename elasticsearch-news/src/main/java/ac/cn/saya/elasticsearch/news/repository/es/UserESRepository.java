package ac.cn.saya.elasticsearch.news.repository.es;

import ac.cn.saya.elasticsearch.news.entity.es.UserESEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Title: UserESRepository
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/27 0027 14:16
 * @Description:
 * 用户索引持久层
 */
@Repository
public interface UserESRepository extends ElasticsearchRepository<UserESEntity,Integer> {

}
