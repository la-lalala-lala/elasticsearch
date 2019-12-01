package ac.cn.saya.elasticsearch.api.document;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * @Title: DocumentTest
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/23 0023 19:53
 * @Description:
 */

public class DocumentTest {

    private PreBuiltTransportClient client;

    @Before
    public void getClient() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        // 获取客户流对象
        client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.20.1.91"),9300));
    }

    // 创建文档
    @Test
    public void createIndexByObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","1");
        jsonObject.put("title","ElasticSearch教程");
        jsonObject.put("author","saya");
        jsonObject.put("content","ElasticSearch是基于Lucene的全文搜索服务器");
        // 创建
        IndexResponse response = client.prepareIndex("news", "article", "1").setSource(jsonObject).execute().actionGet();
        System.out.println("索引:"+response.getIndex());
        System.out.println("类型:"+response.getType());
        System.out.println("id:"+response.getId());
        System.out.println("版本:"+response.getVersion());
        System.out.println("结果:"+response.getResult());
        // 关闭资源
        client.close();
    }

    // 查询单个索引
    @Test
    public void queryIndexByOne(){
        // 查询
        GetResponse response = client.prepareGet("news", "article", "1").get();
        // 打印
        System.out.println(response.getSourceAsString());
        // 关闭资源
        client.close();
    }

    // 查询多个索引
    @Test
    public void queryMultiIndex(){
        // 查询（这里的id不是文章的id，而是索引的id）
        MultiGetResponse responses = client.prepareMultiGet()
                .add("news", "article", "3")
                .add("news", "article", "2", "3")
                .add("news", "article", "1").get();
        for (MultiGetItemResponse multiGetResponse:responses){
            GetResponse item = multiGetResponse.getResponse();
            // 判断是否存在
            if (item.isExists()){
                System.out.println(item.getSourceAsString());
            }
        }
        // 释放资源
        client.close();
    }

    // 更新文档
    @Test
    public void updateDocument() throws InterruptedException, ExecutionException {
        UpdateRequest updateRequest = new UpdateRequest("news", "article", "1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("author","Pandora");
        jsonObject.put("content","ElasticSearch是基于Lucene的全文检索服务器");
        updateRequest.doc(jsonObject);
        UpdateResponse response = client.update(updateRequest).get();
        System.out.println("索引:"+response.getIndex());
        System.out.println("类型:"+response.getType());
        System.out.println("id:"+response.getId());
        System.out.println("版本:"+response.getVersion());
        System.out.println("结果:"+response.getResult());
        // 关闭资源
        client.close();
    }

    // 更新文档(如果要更新的文档不存在，则插入一条)
    @Test
    public void upsertDocument() throws InterruptedException, ExecutionException {
        // 要修改后的文档
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","2");
        jsonObject.put("title","Redis教程");
        jsonObject.put("author","saya");
        jsonObject.put("content","Redis是一款内存数据库");
        // 如不存在，则添加
        IndexRequest indexRequest = new IndexRequest("news", "article", "2").source(jsonObject);
        // 修改哪个文档
        UpdateRequest updateRequest = new UpdateRequest("news", "article", "2");
        updateRequest.doc(jsonObject);
        updateRequest.upsert(indexRequest);
        UpdateResponse response = client.update(updateRequest).get();
        System.out.println("索引:"+response.getIndex());
        System.out.println("类型:"+response.getType());
        System.out.println("id:"+response.getId());
        System.out.println("版本:"+response.getVersion());
        System.out.println("结果:"+response.getResult());
        // 关闭资源
        client.close();
    }

    // 删除文档
    @Test
    public void deleteDocument(){
        // 删除文档
        DeleteResponse response = client.prepareDelete("news", "article", "3").get();
        System.out.println("索引:"+response.getIndex());
        System.out.println("类型:"+response.getType());
        System.out.println("id:"+response.getId());
        System.out.println("版本:"+response.getVersion());
        System.out.println("结果:"+response.getResult());
        // 关闭资源
        client.close();
    }

}
