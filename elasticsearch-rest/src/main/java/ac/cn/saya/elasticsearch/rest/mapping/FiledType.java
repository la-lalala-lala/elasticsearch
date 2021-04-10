package ac.cn.saya.elasticsearch.rest.mapping;

/**
 * @Title: FiledType
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author liunengkai
 * @Date: 3/31/21 22:20
 * @Description: ES字段类型
 */

public class FiledType {

    /**
     * ES字段类型
     */
    private String type;

    public FiledType(String type) {
        this.type = type;
    }

    public FiledType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
