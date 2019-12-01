package ac.cn.saya.elasticsearch.news.repository.mysql;

import ac.cn.saya.elasticsearch.news.entity.mysql.DrugDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: DrugDBRepository
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/28 0028 14:28
 * @Description:
 */
@Repository
public interface DrugDBRepository extends JpaRepository<DrugDBEntity, Integer> {

    /**
     * 按通用名和中文名
     *
     * @param name
     * @return
     */
    @Query("select e from DrugDBEntity e where e.cnName like concat('%',:name,'%') or e.commonName like concat('%',:name,'%')")
    public List<DrugDBEntity> queryByName(@Param("name") String name);

    /**
     * 按批准文号
     *
     * @param name
     * @return
     */
    @Query("select e from DrugDBEntity e where e.apprNumber like concat(:name,'%')")
    public List<DrugDBEntity> queryByAppr(@Param("name") String name);

    /**
     * 按成分
     *
     * @param name
     * @return
     */
    @Query("select e from DrugDBEntity e where e.component like concat('%',:name,'%')")
    public List<DrugDBEntity> queryByComponent(@Param("name") String name);

    /**
     * 按适用症
     *
     * @param name
     * @return
     */
    @Query("select e from DrugDBEntity e where e.indication like concat('%',:name,'%')")
    public List<DrugDBEntity> queryByIndication(@Param("name") String name);


}
