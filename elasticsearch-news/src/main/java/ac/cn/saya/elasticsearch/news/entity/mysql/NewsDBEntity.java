package ac.cn.saya.elasticsearch.news.entity.mysql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
@Table(name = "news")
@Entity
public class NewsDBEntity implements Serializable {

    private static final long serialVersionUID = 7061122726179213920L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String author;
    private String content;
    @Column(name="create_time")
    private String createTime;
    @Column(name="update_time")
    private String updateTime;

}
