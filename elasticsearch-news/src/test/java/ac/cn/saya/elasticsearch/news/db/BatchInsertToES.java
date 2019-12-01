package ac.cn.saya.elasticsearch.news.db;

import ac.cn.saya.elasticsearch.news.entity.mysql.UserDBEntity;
import ac.cn.saya.elasticsearch.news.repository.mysql.UserDBRepository;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Title: BatchInsertToES
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/27 0027 18:12
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class BatchInsertToES {

    @Autowired
    private UserDBRepository userDBRepository;

    @Test
    public void batch() {
        List<UserDBEntity> userList = userDBRepository.getNotNullUser();
        System.out.println(JSON.toJSONString(userList));
    }
}
