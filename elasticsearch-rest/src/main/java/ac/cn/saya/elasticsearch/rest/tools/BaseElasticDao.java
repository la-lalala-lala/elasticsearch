package ac.cn.saya.elasticsearch.rest.tools;


import ac.cn.saya.elasticsearch.rest.config.ESRestClientConfig;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Title: BaseElasticDao
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author liunengkai
 * @Date: 3/22/21 22:34
 * @Description: 官方api：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-supported-apis.html
 * https://github.com/king-angmar/weathertop/tree/master/akkad-springboot/springboot-elasticsearch/es-footman
 */
@Repository
public class BaseElasticDao {

    @Resource
    RestHighLevelClient client;

    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     * @throws IOException
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-indices-exists.html
     */
    public boolean indexExist(String indexName) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        return client.indices().exists(getIndexRequest, ESRestClientConfig.COMMON_OPTIONS);
    }

    /**
     * 删除索引
     * @param indexName
     * @throws IOException
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-delete-index.html
     */
    public void deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        request.timeout(TimeValue.timeValueMinutes(2));
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, ESRestClientConfig.COMMON_OPTIONS);

    }


}
