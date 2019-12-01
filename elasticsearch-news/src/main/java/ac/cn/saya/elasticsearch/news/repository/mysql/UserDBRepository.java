package ac.cn.saya.elasticsearch.news.repository.mysql;

import ac.cn.saya.elasticsearch.news.entity.mysql.UserDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: UserDBRepository
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/27 0027 18:01
 * @Description:
 */
@Repository
public interface UserDBRepository extends JpaRepository<UserDBEntity,Integer> {

    @Query("select e from UserDBEntity e where e.userName <> '' and e.nickName <> '' and e.regionName IS not NULL")
    public List<UserDBEntity> getNotNullUser();

    @Query("select e from UserDBEntity e where e.userName like concat('%',:keyword,'%') or e.nickName like concat('%',:keyword,'%')")
    public List<UserDBEntity> getUserList(@Param("keyword") String keyword);

}
