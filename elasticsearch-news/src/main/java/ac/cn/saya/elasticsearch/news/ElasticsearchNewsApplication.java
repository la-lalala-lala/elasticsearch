package ac.cn.saya.elasticsearch.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticsearchNewsApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ElasticsearchNewsApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
