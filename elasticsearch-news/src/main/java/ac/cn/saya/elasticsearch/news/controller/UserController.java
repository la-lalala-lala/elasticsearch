package ac.cn.saya.elasticsearch.news.controller;

import ac.cn.saya.elasticsearch.news.entity.es.UserESEntity;
import ac.cn.saya.elasticsearch.news.entity.mysql.UserDBEntity;
import ac.cn.saya.elasticsearch.news.repository.es.UserESRepository;
import ac.cn.saya.elasticsearch.news.repository.mysql.UserDBRepository;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * @Title: UserController
 * @ProjectName elasticsearch
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/11/27 0027 15:49
 * @Description: 会员用户controller
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserESRepository userESRepository;

    @Autowired
    private UserDBRepository userDBRepository;

    //http://127.0.0.1:8080/user/es/batch
    @GetMapping(value = "/es/batch")
    public JSONObject batchToES() {
        JSONObject result = new JSONObject();
        List<UserDBEntity> userList = userDBRepository.getNotNullUser();
        int size = userList.size();
        if (size > 0) {
            // 清空之前的索引信息
            userESRepository.deleteAll();
            String nowDateTime = (LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            for (int i = 0; i < size; i++) {
                UserESEntity esEntity = new UserESEntity(
                        i + 1,
                        (userList.get(i)).getUserName(),
                        (userList.get(i)).getNickName(),
                        (userList.get(i)).getSex(),
                        (userList.get(i)).getPhoneNum(),
                        (userList.get(i)).getRegionName(),
                        nowDateTime);
                userESRepository.save(esEntity);
            }
            result.put("code", "0");
            result.put("msg", "同步成功");
        } else {
            result.put("code", "-1");
            result.put("msg", "数据库中没有记录");
        }
        return result;
    }

    @GetMapping(value = "/es/all")
    public JSONObject shoeAllESData() {
        JSONObject result = new JSONObject();
        // 查询所有的索引
        Iterable<UserESEntity> iterable = userESRepository.search (QueryBuilders.matchAllQuery());
        Optional<Iterable<UserESEntity>> optional = Optional.ofNullable(iterable);
        boolean exsit = optional.isPresent();
        if (exsit){
            List<UserESEntity> esList = Lists.newArrayList(iterable);
            result.put("code", "-1");
            result.put("msg", "查询成功");
            result.put("data", esList);
        }else {
            result.put("code", "-1");
            result.put("msg", "ES中暂未有数据");
        }
        return result;
    }

    // http://127.0.0.1:8080/user/add?userId=1&userName=saya&nickName=saya&sex=w&phoneNum=12323&regionName=%E5%AE%BF%E8%BF%81
    @GetMapping(value = "/add")
    public JSONObject addUser(UserESEntity entity) {
        JSONObject result = new JSONObject();
        if (null == entity || !entity.checkAdd()) {
            result.put("code", "-1");
            result.put("msg", "缺少参数");
            return result;
        } else {
            UserESEntity data = userESRepository.save(entity);
            result.put("data", data);
            result.put("code", "0");
            result.put("msg", "添加成功");
            return result;
        }
    }

    // http://127.0.0.1:8080/user/delete/2
    @GetMapping(value = "/delete/{id}")
    public JSONObject deleteUser(@PathVariable("id") Integer id) {
        JSONObject result = new JSONObject();
        UserESEntity userESEntity = new UserESEntity();
        userESEntity.setUserId(id);
        userESRepository.delete(userESEntity);
        result.put("code", "0");
        result.put("msg", "删除成功");
        return result;
    }

    //http://127.0.0.1:8080/user/edit?userId=1&userName=saya&nickName=saya&sex=w&createTime=2019-11-16&phoneNum=6666&regionName=%E4%BB%81%E6%80%80
    @GetMapping(value = "/edit")
    public JSONObject updateUser(UserESEntity entity) {
        JSONObject result = new JSONObject();
        if (null == entity || !entity.checkAdd()) {
            result.put("code", "-1");
            result.put("msg", "缺少参数");
            return result;
        } else {
            // 若不存在会添加
            UserESEntity data = userESRepository.save(entity);
            result.put("data", data);
            result.put("code", "0");
            result.put("msg", "修改成功");
            return result;
        }
    }

    // http://127.0.0.1:8080/user/search/{}?name=saya
    @GetMapping(value = "/search/{type}")
    public JSONObject searchUser(@PathVariable("type") String type,@RequestParam("name") String name) {
        JSONObject result = new JSONObject();
        StopWatch watch = new StopWatch();
        if ("es".equals(type)){
            watch.start();
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("userName", name));
            builder.should(QueryBuilders.matchPhraseQuery("nickName", name));
            Page<UserESEntity> search = (Page<UserESEntity>) userESRepository.search(builder);
            watch.stop();
            result.put("data", search);
            result.put("code", "0");
        }else {
            watch.start();
            result.put("data", userDBRepository.getUserList(name));
            watch.stop();
            result.put("code", "0");
        }
        result.put("msg", "查询成功");
        result.put("search-time",watch.getTotalTimeMillis());
        return result;
    }

}
