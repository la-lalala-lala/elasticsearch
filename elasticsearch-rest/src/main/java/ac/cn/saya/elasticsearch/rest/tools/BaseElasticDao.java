package ac.cn.saya.elasticsearch.rest.tools;


import ac.cn.saya.elasticsearch.rest.config.ESRestClientConfig;
import ac.cn.saya.elasticsearch.rest.entity.ElasticSearchBaseEntity;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public boolean deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        request.timeout(TimeValue.timeValueMinutes(2));
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, ESRestClientConfig.COMMON_OPTIONS);
        return deleteIndexResponse.isAcknowledged();
    }

    /**
     * 创建索引及映射
     * @param indexName
     * @param mapping
     * @throws IOException
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-create-index.html
     */
    public boolean createIndexMapping(String indexName,Object mapping) throws IOException{
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        Map<String, Object> mappings = new HashMap<>();
        mappings.put("properties", mapping);
        request.mapping(JackJsonUtil.toJson(mappings,false),XContentType.JSON);
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));
        CreateIndexResponse createIndexResponse  = client.indices().create(request, ESRestClientConfig.COMMON_OPTIONS);
        return createIndexResponse.isAcknowledged();
    }

    /**
     * 读取映射
     * @param indexName
     * @throws IOException
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-get-mappings.html
     */
    public void getIndexMapping(String indexName) throws IOException{
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(indexName);
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));
        GetMappingsResponse getMappingResponse = client.indices().getMapping(request, ESRestClientConfig.COMMON_OPTIONS);
        Map<String, MappingMetaData> allMappings = getMappingResponse.mappings();
        MappingMetaData indexMapping = allMappings.get(indexName);
        Map<String, Object> mapping = indexMapping.sourceAsMap();
        for (String key:mapping.keySet()){
            System.out.println("key:"+key+",type:"+mapping.get(key));
        }
    }

    /**
     * 创建文档，添加一行数据
     * @param entity
     * @throws IOException
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-document-index.html
     */
    public void insertDocument(ElasticSearchBaseEntity entity) throws IOException{
        IndexRequest request = new IndexRequest(entity.getIndexName());
        request.source(JackJsonUtil.toJson(entity.getData(),false),XContentType.JSON);
        request.id(entity.getDocId());
        request.timeout(TimeValue.timeValueSeconds(1));
        IndexResponse response = client.index(request, ESRestClientConfig.COMMON_OPTIONS);
        return;
    }

    /**
     * 修改/创建文档
     * @param docAsUpsert 是否强制执行，若文档不存在，则按添加进行执行
     * @param entity
     * @throws IOException
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-document-update.html
     */
    public void updateDocument(boolean docAsUpsert,ElasticSearchBaseEntity entity) throws IOException{
        UpdateRequest request = new UpdateRequest(entity.getIndexName(), entity.getDocId());
        request.doc(JackJsonUtil.toJson(entity.getData(),false),XContentType.JSON);
        request.timeout(TimeValue.timeValueSeconds(1));
        request.docAsUpsert(docAsUpsert);
        UpdateResponse response = client.update(request, ESRestClientConfig.COMMON_OPTIONS);
    }

    /**
     * 根据索引和id查询文档
     * @param entity
     * @throws IOException
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-document-get.html
     */
    public String getDocumentById(ElasticSearchBaseEntity entity) throws IOException{
        GetRequest request = new GetRequest(entity.getIndexName(),entity.getDocId());
        GetResponse response = client.get(request, ESRestClientConfig.COMMON_OPTIONS);
        if (response.isExists() && !response.isSourceEmpty()){
            return response.getSourceAsString();
        }
        return null;
    }

    /**
     * 删除文档
     * @param entity
     * @return
     * @throws IOException
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-document-delete.html
     */
    public DocWriteResponse.Result deleteDocument(ElasticSearchBaseEntity entity) throws IOException{
        DeleteRequest request = new DeleteRequest(entity.getIndexName(), entity.getDocId());
        request.timeout(TimeValue.timeValueSeconds(1));
        DeleteResponse response = client.delete(request, ESRestClientConfig.COMMON_OPTIONS);
        return response.getResult();
    }

    /**
     * 文档批量操作
     * @param datas
     * @throws Exception
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-document-bulk.html
     */
    public void bulkDocument(List<DocWriteRequest> datas) throws Exception{
        if (CollectionUtils.isEmpty(datas)){
            throw new NullPointerException("data collection don't null");
        }
        BulkRequest request = new BulkRequest();
        datas.stream().forEach(e -> request.add(e));
        BulkResponse response = client.bulk(request, ESRestClientConfig.COMMON_OPTIONS);
    }

    /**
     * 文档检索
     * @param indexName
     * @param where 查询条件
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-search.html
     */
    public SearchHits searchDocument(String indexName,SearchSourceBuilder where) throws IOException{
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchRequest request = searchRequest.source(where);
        SearchResponse response = client.search(request, ESRestClientConfig.COMMON_OPTIONS);
        return response.getHits();
    }
}
