package ac.cn.saya.elasticsearch.rest.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: ESRestClientConfig
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author liunengkai
 * @Date: 3/22/21 23:02
 * @Description:
 */
@Configuration
public class ESRestClientConfig {

    @Value("${es.host}")
    private String host;

    @Value("${es.port}")
    private int port;

    @Value("${es.scheme}")
    private String scheme;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client=new RestHighLevelClient(
                RestClient.builder(new HttpHost(host,port,scheme)));
        //如果是集群
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")
//                        , new HttpHost("127.0.0.1", 9201, "http")
//                        , new HttpHost("127.0.0.1", 9202, "http")));
        return client;
    }

}
