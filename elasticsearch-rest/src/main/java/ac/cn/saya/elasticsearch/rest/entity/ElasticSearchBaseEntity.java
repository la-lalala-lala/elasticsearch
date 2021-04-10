package ac.cn.saya.elasticsearch.rest.entity;

/**
 * @Title: ElasticSearchBaseEntity
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author liunengkai
 * @Date: 4/8/21 23:09
 * @Description:
 */

public class ElasticSearchBaseEntity<T> {

    private String indexName;

    private String docId;

    private T data;

    public ElasticSearchBaseEntity() {
    }

    public ElasticSearchBaseEntity(String indexName, String docId) {
        this.indexName = indexName;
        this.docId = docId;
    }

    public ElasticSearchBaseEntity(String indexName, String docId, T data) {
        this.indexName = indexName;
        this.docId = docId;
        this.data = data;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
