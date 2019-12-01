package ac.cn.saya.elasticsearch.news.entity.es;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;


/**
 * @Title: NewsDBEntity
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/24 0024 13:58
 * @Description:
 */

@NoArgsConstructor
@Setter
@Getter
@Document(indexName = "news",type = "article",useServerConfiguration = true,createIndex = false)
public class NewsESEntity implements Serializable {

    private static final long serialVersionUID = -2673151864123097794L;
    @Id
    private Integer id;

    @Field(type = FieldType.text,analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.text,analyzer = "ik_max_word")
    private String author;

    @Field(type = FieldType.text,analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;

}
