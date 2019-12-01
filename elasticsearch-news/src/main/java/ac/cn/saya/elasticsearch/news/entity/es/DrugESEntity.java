package ac.cn.saya.elasticsearch.news.entity.es;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @Title: DrugESEntity
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/28 0028 14:13
 * @Description:
 * 药品索引信息
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "drug",type = "info",useServerConfiguration = true,createIndex = false)
public class DrugESEntity implements Serializable {

    private static final long serialVersionUID = -1188717694329870969L;
    /**
     * 药品编号
     */
    @Id
    private Integer drugId;

    /**
     * 中文名称
     */
    @Field(type = FieldType.text)
    private String cnName;

    /**
     * 拼音全称
     */
    @Field(type = FieldType.text)
    private String pinyin;

    /**
     * 通用名称
     */
    @Field(type = FieldType.text)
    private String commonName;

    /**
     * 批准文号
     */
    @Field(type = FieldType.text,index = false)
    private String apprNumber;

    /**
     * 生产厂家
     */
    @Field(type = FieldType.text)
    private String companyName;

    /**
     * 主要成份
     */
    @Field(type = FieldType.text,analyzer = "ik_max_word")
    private String component;

    /**
     * 适应症
     */
    @Field(type = FieldType.text,analyzer = "ik_max_word")
    private String indication;

    /**
     * 用法用量
     */
    @Field(type = FieldType.text)
    private String dosage;

    /**
     * 不良反应
     */
    @Field(type = FieldType.text,analyzer = "ik_max_word")
    private String adversereactions;

    /**
     * 注意事项
     */
    @Field(type = FieldType.text,analyzer = "ik_max_word")
    private String precautions;

}
