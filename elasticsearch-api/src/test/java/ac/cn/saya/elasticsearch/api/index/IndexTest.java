package ac.cn.saya.elasticsearch.api.index;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Title: IndexTest
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/23 0023 18:34
 * @Description:
 */

public class IndexTest {

    private PreBuiltTransportClient client;

    @Before
    public void getClient() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        // 获取客户流对象
        client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.20.1.91"),9300));
    }

    @Test
    public void createIndex(){
        // 创建索引
        client.admin().indices().prepareCreate("news").get();
        // 关闭资源
        client.close();
    }

    @Test
    public void deleteIndex(){
        // 删除索引
        client.admin().indices().prepareDelete("news").get();
        // 释放资源
        client.close();
    }

}
