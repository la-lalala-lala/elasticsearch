package ac.cn.saya.elasticsearch.rest;

import ac.cn.saya.elasticsearch.rest.entity.ElasticSearchBaseEntity;
import ac.cn.saya.elasticsearch.rest.entity.UserEntityElasticSearch;
import ac.cn.saya.elasticsearch.rest.mapping.UserMapping;
import ac.cn.saya.elasticsearch.rest.tools.BaseElasticDao;
import ac.cn.saya.elasticsearch.rest.tools.JackJsonUtil;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ElasticsearchRestApplicationTests {

    @Autowired
    private BaseElasticDao baseElasticDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void testIndexExist() throws Exception{
        System.out.println(baseElasticDao.indexExist("user"));
    }

    @Test
    public void testDeleteIndex() throws Exception{
        baseElasticDao.deleteIndex("user");
    }

    @Test
    public void testCreateMapping() throws Exception{
        baseElasticDao.createIndexMapping("user",new UserMapping());
    }

    @Test
    public void testGetMapping() throws Exception{
        baseElasticDao.getIndexMapping("user");
    }

    @Test
    public void testInsertDocument() throws Exception{
        UserEntityElasticSearch user = new UserEntityElasticSearch();
        user.setId(1);
        user.setName("saya");
        user.setAccount("saya");
        user.setPasswprd("123456");
        user.setSex(true);
        user.setAge(18);
        user.setEmail("saya@saya.ac.cn");
        user.setPhone("18064902295");
        ElasticSearchBaseEntity<UserEntityElasticSearch> baseEntity = new ElasticSearchBaseEntity<>("user",String.valueOf(user.getId()),user);
        baseElasticDao.insertDocument(baseEntity);
    }

    @Test
    public void testUpdateDocument() throws Exception{
        UserEntityElasticSearch user = new UserEntityElasticSearch();
        user.setId(1);
        user.setName("Pandora");
        user.setAccount("Pandora");
        user.setEmail("saya@saya.ac.cn");
        user.setPhone("17345225420");
        ElasticSearchBaseEntity<UserEntityElasticSearch> baseEntity = new ElasticSearchBaseEntity<>("user",String.valueOf(user.getId()),user);
        baseElasticDao.updateDocument(false,baseEntity);
    }

    @Test
    public void testGetDocumentById() throws Exception{
        String result = baseElasticDao.getDocumentById(new ElasticSearchBaseEntity("user", "1"));
        System.out.println("result:"+result);
    }

    @Test
    public void testDeleteDocument() throws Exception{
        DocWriteResponse.Result result = baseElasticDao.deleteDocument(new ElasticSearchBaseEntity("user", "1"));
        System.out.println("result:"+result);
    }

    @Test
    public void testBulkDocument() throws Exception{
        UserEntityElasticSearch pandora = new UserEntityElasticSearch();
        pandora.setId(1);
        pandora.setName("Pandora");
        pandora.setAccount("Pandora");
        pandora.setEmail("saya@saya.ac.cn");
        pandora.setPhone("18064902295");
        UserEntityElasticSearch shmily = new UserEntityElasticSearch();
        shmily.setId(2);
        shmily.setName("Shmily");
        shmily.setAccount("Shmily");
        shmily.setEmail("saya@saya.ac.cn");
        shmily.setPhone("17345225420");
        IndexRequest request1 = new IndexRequest("user");
        request1.source(JackJsonUtil.toJson(pandora,false), XContentType.JSON);
        request1.id("1");
        IndexRequest request2 = new IndexRequest("user");
        request2.source(JackJsonUtil.toJson(shmily,false), XContentType.JSON);
        request2.id("2");
        List<DocWriteRequest> datas = new ArrayList<>();
        datas.add(request1);
        datas.add(request2);
        baseElasticDao.bulkDocument(datas);
    }

    @Test
    public void testSearchDocument() throws Exception{
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("address","Place"));
        searchSourceBuilder.from(1);
        searchSourceBuilder.size(20);
        SearchHits hits = baseElasticDao.searchDocument("bank", searchSourceBuilder);
        for (SearchHit item:hits) {
            System.out.println("item:"+item.getSourceAsString());
        }
    }

}
