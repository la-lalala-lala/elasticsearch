package ac.cn.saya.elasticsearch.rest.mapping;

/**
 * @Title: UserMapping
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author liunengkai
 * @Date: 3/31/21 22:22
 * @Description:
 */

public class UserMapping {

    private FiledType name = new FiledType("text");
    private FiledType account = new FiledType("keyword");
    private FiledType passwprd = new FiledType("keyword");
    private FiledType sex = new FiledType("boolean");
    private FiledType age = new FiledType("integer");
    private FiledType email = new FiledType("keyword");
    private FiledType phone = new FiledType("keyword");

    public UserMapping() {
    }

    public FiledType getName() {
        return name;
    }

    public void setName(FiledType name) {
        this.name = name;
    }

    public FiledType getAccount() {
        return account;
    }

    public void setAccount(FiledType account) {
        this.account = account;
    }

    public FiledType getPasswprd() {
        return passwprd;
    }

    public void setPasswprd(FiledType passwprd) {
        this.passwprd = passwprd;
    }

    public FiledType getSex() {
        return sex;
    }

    public void setSex(FiledType sex) {
        this.sex = sex;
    }

    public FiledType getAge() {
        return age;
    }

    public void setAge(FiledType age) {
        this.age = age;
    }

    public FiledType getEmail() {
        return email;
    }

    public void setEmail(FiledType email) {
        this.email = email;
    }

    public FiledType getPhone() {
        return phone;
    }

    public void setPhone(FiledType phone) {
        this.phone = phone;
    }
}
