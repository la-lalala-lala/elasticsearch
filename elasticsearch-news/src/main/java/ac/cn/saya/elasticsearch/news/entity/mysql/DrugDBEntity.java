package ac.cn.saya.elasticsearch.news.entity.mysql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @Title: DrugDBEntity
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/28 0028 14:18
 * @Description: 药品信息实体
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "drug")
@Entity
public class DrugDBEntity implements Serializable {

    private static final long serialVersionUID = 7701819780242808030L;
    /**
     * 药品编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drug_id")
    private Integer drugId;

    /**
     * 中文名称
     */
    @Column(name = "cn_name")
    private String cnName;

    /**
     * 拼音全称
     */
    private String pinyin;

    /**
     * 通用名称
     */
    @Column(name = "common_name")
    private String commonName;

    /**
     * 批准文号
     */
    @Column(name = "appr_number")
    private String apprNumber;

    /**
     * 生产厂家
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 主要成份
     */
    private String component;

    /**
     * 适应症
     */
    private String indication;

    /**
     * 用法用量
     */
    private String dosage;

    /**
     * 不良反应
     */
    private String adversereactions;

    /**
     * 注意事项
     */
    private String precautions;

}
