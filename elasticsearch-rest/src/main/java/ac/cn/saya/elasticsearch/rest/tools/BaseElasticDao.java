package ac.cn.saya.elasticsearch.rest.tools;


import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Title: BaseElasticDao
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author liunengkai
 * @Date: 3/22/21 22:34
 * @Description:
 * https://github.com/king-angmar/weathertop/tree/master/akkad-springboot/springboot-elasticsearch/es-footman
 */
@Repository
public class BaseElasticDao {

    @Resource
    RestHighLevelClient client;

    /**
     * 判断索引是否存在
     * @param indexName
     * @return
     * @throws IOException
     */
    public boolean indexExist(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(indexName);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

}
