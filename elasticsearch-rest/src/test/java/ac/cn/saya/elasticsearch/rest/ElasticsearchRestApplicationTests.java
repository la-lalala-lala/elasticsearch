package ac.cn.saya.elasticsearch.rest;

import ac.cn.saya.elasticsearch.rest.tools.BaseElasticDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticsearchRestApplicationTests {

    @Autowired
    private BaseElasticDao baseElasticDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void testIndexExist() throws Exception{
        System.out.println(baseElasticDao.indexExist("news"));
    }

}
