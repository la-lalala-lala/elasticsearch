package ac.cn.saya.elasticsearch.rest;

import ac.cn.saya.elasticsearch.rest.config.ESRestClientConfig;
import ac.cn.saya.elasticsearch.rest.entity.UserEntity;
import ac.cn.saya.elasticsearch.rest.tools.BaseElasticDao;
import ac.cn.saya.elasticsearch.rest.tools.JackJsonUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Title: CreateIndexTest
 * @ProjectName elasticsearch
 * @Author saya
 * @Date: 2021/3/28 17:59
 * @Description: TODO
 */
@SpringBootTest
public class CreateIndexTest {

    @Resource
    RestHighLevelClient client;

    @Test
    public void addIndex() throws Exception{
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setName("saya");
        user.setAccount("Pandora");
        user.setAge(18);
        user.setSex(true);
        user.setPasswprd("666666");
        user.setEmail("saya@saya.ac.cn");
        user.setPhone("11111111111");

        IndexRequest userRequest = new IndexRequest("user");
        userRequest.id((user.getId()).toString());
        String json = JackJsonUtil.toJson(user);
        userRequest.source(json, XContentType.JSON);
        IndexResponse index = client.index(userRequest, ESRestClientConfig.COMMON_OPTIONS);
        System.out.println(index);
    }

}
