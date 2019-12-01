package ac.cn.saya.elasticsearch.news.repository.mysql;

import ac.cn.saya.elasticsearch.news.entity.mysql.NewsDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: NewsDBRepository
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/24 0024 14:03
 * @Description:
 * news持久层
 */

@Repository
public interface NewsDBRepository extends JpaRepository<NewsDBEntity,Integer> {

    @Query("select e from NewsDBEntity e where e.title like concat('%',:keyword,'%') or e.content like concat('%',:keyword,'%')")
    public List<NewsDBEntity> search(@Param("keyword") String keyword);

}
