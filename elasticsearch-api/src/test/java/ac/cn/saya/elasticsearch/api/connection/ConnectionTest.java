package ac.cn.saya.elasticsearch.api.connection;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Title: ConnectionTest
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/23 0023 18:15
 * @Description:
 */

public class ConnectionTest {

    @Test
    public void getClient() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        // 获取客户流对象
        //从创建一个 客户端
        TransportClient client = new PreBuiltTransportClient(settings);
        //设置操作es服务主机及端口号
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("103.46.128.20"),36921));
        System.out.println(client.toString());
    }



}
