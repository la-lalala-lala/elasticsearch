package ac.cn.saya.elasticsearch.rest.tools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: JackJsonUtil
 * @ProjectName shmily-gui
 * @Description: TODO
 * @Author Pandora
 * @Date: 2020/11/30 11:01
 * @Description:
 */

public class JackJsonUtil {

    private ObjectMapper objectMapper;

    private JackJsonUtil(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    /**
     * 构建一个对象（单一类型）类型的Node
     * @return
     */
    public static ObjectNode createObjectNode(){
        return (JackJsonUtil.getInstance().objectMapper).createObjectNode();
    }

    /**
     * 构建一个集合类型的Node
     * @return
     */
    public static ArrayNode createArrayNode(){
        return (JackJsonUtil.getInstance().objectMapper).createArrayNode();
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<String,Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                // 记住！对于BigDecimal类型的数据，建议使用String进行初始化，直接使用数字类型时，在进行传输转换后，tostring将转义成科学计数法来表示
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // 只有不为空的数据才放入
            if (null != value){
                map.put(fieldName, value);
            }
        }
        return map;
    }

    /**
     * 对象转Json格式字符串
     * @param object 对象
     * @param model true：全量序列化，false：为空时不给予序列化
     * @return Json格式字符串
     */
    public static <T> String toJson(T object,boolean model) {
        if (null == object){
            return null;
        }
        try {
            if (!model){
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                return object instanceof String ? (String) object :mapper.writeValueAsString(object);
            }
            return object instanceof String ? (String) object :(JackJsonUtil.getInstance().objectMapper).writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对象转Json格式字符串(格式化的Json字符串)
     * @param object 对象
     * @return 美化的Json格式字符串
     */
    public static <T> String toJsonPretty(T object) {
        if (object == null) {
            return null;
        }
        try {
            return object instanceof String ? (String) object : (JackJsonUtil.getInstance().objectMapper).writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转换为树模型(tree model)
     * @param json 要转换的字符串
     * @return JsonNode
     */
    public static JsonNode readTree(String json){
        if(StringUtils.isEmpty(json)){
            return null;
        }
        try {
            return (JackJsonUtil.getInstance().objectMapper).readTree(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转换为自定义对象(JavaBean)
     * @param json 要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     */
    public static <T> T readValue(String json, Class<T> clazz){
        if(StringUtils.isEmpty(json) || null == clazz){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) json : (JackJsonUtil.getInstance().objectMapper).readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转换为自定义List对象
     * @param json 要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义List对象
     */
    public static <T> List<T> readListValue(String json, Class<T> clazz) {
        if(StringUtils.isEmpty(json) || null == clazz){
            return Collections.emptyList();
        }
        JavaType javaType = (JackJsonUtil.getInstance().objectMapper).getTypeFactory().constructParametricType(List.class, clazz);
        if(null == javaType){
            return Collections.emptyList();
        }
        try {
            List<T> resultList = (JackJsonUtil.getInstance().objectMapper).readValue(json, javaType);
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 字符串转换为自定义List对象
     * @param json 要转换的字符串
     * @param keyType 自定义Map的key类型
     * @param valueType 自定义Map的value类型
     * @return 自定义List对象
     */
    public static <K, V> Map<K, V> jsonToMap(String json, Class<K> keyType, Class<V> valueType) {
        if(StringUtils.isEmpty(json) || null == keyType || null == valueType){
            return null;
        }
        JavaType javaType = (JackJsonUtil.getInstance().objectMapper).getTypeFactory().constructMapType(Map.class, keyType, valueType);
        if(null == javaType){
            return null;
        }
        try {
            Map<K, V> resultMap = (JackJsonUtil.getInstance().objectMapper).readValue(json, javaType);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JackJsonUtil getInstance(){
        // 当JSON中包含了目标Java对象没有的属性,直接忽略目标对象没有的属性
        // 参见https://blog.csdn.net/gavincook/article/details/46574661?utm_source=blogxgwz2
        (innerStaticClass.instance.objectMapper).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return innerStaticClass.instance;
    }

    static class innerStaticClass{
        private static JackJsonUtil instance = new JackJsonUtil(new ObjectMapper());
    }

}
