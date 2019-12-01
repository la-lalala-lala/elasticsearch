package ac.cn.saya.elasticsearch.news.entity.mysql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Title: UserDBEntity
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/27 0027 14:04
 * @Description: 用户会员数据库实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "user")
@Entity
public class UserDBEntity implements Serializable {

    private static final long serialVersionUID = 4456603659450588011L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "nick_name")
    private String nickName;

    private String sex;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "create_date")
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
