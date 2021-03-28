package ac.cn.saya.elasticsearch.rest.entity;

import java.io.Serializable;

/**
 * @Title: UserEntity
 * @ProjectName elasticsearch
 * @Author saya
 * @Date: 2021/3/28 17:50
 * @Description: TODO
 * 用户索引映射关系
 *       "name":{"type": "text"},
 *       "account":{"type": "keyword"},
 *       "passwprd":{"type": "keyword"},
 *       "sex":{"type": "boolean"},
 *       "age":{"type": "integer"},
 *       "email":{"type": "keyword"},
 *       "phone":{"type": "keyword"}
 */

public class UserEntity implements Serializable {

    private static final long serialVersionUID = 5559523739520543306L;
    
    private Integer id;
    private String name;
    private String account;
    private String passwprd;
    private Boolean sex;
    private Integer age;
    private String email;
    private String phone;

    public UserEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswprd() {
        return passwprd;
    }

    public void setPasswprd(String passwprd) {
        this.passwprd = passwprd;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
