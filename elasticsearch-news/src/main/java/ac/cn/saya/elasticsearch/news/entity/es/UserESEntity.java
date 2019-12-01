package ac.cn.saya.elasticsearch.news.entity.es;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @Title: UserESEntity
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/27 0027 13:55
 * @Description:
 * 用户索引实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(indexName = "user",type = "member",useServerConfiguration = true,createIndex = false)
public class UserESEntity implements Serializable {

    private static final long serialVersionUID = 1864204555545957262L;
    @Id
    private Integer userId;

    @Field(type = FieldType.text,analyzer = "ik_max_word")
    private String userName;

    @Field(type = FieldType.text,analyzer = "ik_max_word")
    private String nickName;

    @Field(type = FieldType.text)
    private String sex;

    @Field(type = FieldType.text)
    private String phoneNum;

    @Field(type = FieldType.text)
    private String regionName;

    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    private String createDate;

    /**
     * 校验添加时的字段情况
     * @return
     */
    public boolean checkAdd() {
        if (this.getUserId() == null
                || StringUtils.isEmpty(this.getUserName())
                || StringUtils.isEmpty(this.getNickName())
                || StringUtils.isEmpty(this.getSex())
                ||StringUtils.isEmpty(this.getPhoneNum())
                || StringUtils.isEmpty(this.getRegionName())){
            return false;
        }else {
            return true;
        }
    }

}
