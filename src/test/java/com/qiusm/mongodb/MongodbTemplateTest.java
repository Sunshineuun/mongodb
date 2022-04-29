package com.qiusm.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.qiusm.mongodb.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.query.Criteria;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author qiushengming
 */
@Slf4j
public class MongodbTemplateTest extends MongodbApplicationTests {
    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    public void getMongoTemplate() {
        log.info("{}", mongoTemplate);
    }

    @Test
    public void template() {
        IndexOperations ops = mongoTemplate.indexOps(UserEntity.class);
    }

    /**
     * 1.使用save函数里，如果原来的对象不存在，那他们都可以向collection里插入数据。
     * 如果已经存在，save会调用update更新里面的记录，而insert则会忽略操作。<br>
     * <p>
     * 2. insert可以一次性插入一个列表，而不用遍历，效率高， save则需要遍历列表，一个个插入。 <br>
     */
    @Test
    public void insert() {
        String[] username = new String[]{"qiusm", "luyun", "guobailu", "zhangyiwen"};
        List<UserEntity> users = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            UserEntity user = new UserEntity();
            user.setId((long) i);
            user.setPassword(UUID.randomUUID().toString());
            user.setUsername(username[i % 4]);
            user.setCreateTime(LocalDateTime.now());
            users.add(user);
        }
        // insert 时当主键已存在则会报重复键的错。效率高
        // UserEntity result = mongoTemplate.insert(user);
        // save 时当主键已存在则回去更新。效率低，因为存在检查是否存在的操作
        // UserEntity result = mongoTemplate.insert(user);
        mongoTemplate.dropCollection(UserEntity.class);
        Collection<UserEntity> result = mongoTemplate.insertAll(users);
        log.info("{}", result);
    }

    /**
     * 聚合操作
     */
    @Test
    public void agg() {
        /*
        // 匹配条件
        Aggregation.match(Criteria.where("start_time")
                .gte(startTime.toEpochSecond(ZoneOffset.of("+8")))
                .lt(endTime.toEpochSecond(ZoneOffset.of("+8")))
                .and("gender").is(1)
                .and("test_code").in("qqqq", "wwww")),

        // 关联查询
        // .lookup(String fromCollectionName, String localFeild, String foreginFeild, String as)
        // 关联集合名称、主表关联字段、从表关联字段、别名
        Aggregation.lookup("collection_two", "test_code", "test_code", "detail"),

        // 分组查询
        // .first()、.last()、.min()、max()、.avg()、.count() 后需调用 .as() 对结果值进行重命名
        Aggregation.group("test_code")   // 分组条件
                .first("test_code").as("test_code")   // 获取分组后查询到的该字段的第一个值
                .first("detail.test_name").as("test_name")
                .sum("age").as("age")   // 按分组对该字段求和
                .min("age").as("min_age")   // 按分组求该字段最小值
                .max("age").as("max_age")   // 按分组求该字段最大值
                .avg("age").as("avg_age")   // 按分组求该字段平均值
                .first("start_time").as("start_time")
                .last("end_time").as("end_time")   // 获取分组后查询到的该字段的最后一个值
                .first("gender").as("gender")
                .count().as("count"),   // 统计分组后组内条数

        Aggregation.sort(Sort.Direction.DESC, "avg_age"),   // 排序

        Aggregation.skip(0L),   // 分页
        Aggregation.limit(10L),

        // 输出的列
        Aggregation.project("test_code", "test_name", "start_time", "end_time", "age",
                        "min_age", "max_age", "avg_age", "gender", "count")
        * */
        Aggregation aggregation = Aggregation.newAggregation(
                UserEntity.class,
                // Aggregation.match(Criteria.where("username").is("qiusm")),
                Aggregation.sort(Sort.Direction.ASC, "password"),
                Aggregation.group("username")
                        .last("username").as("username")
                        .last("password").as("pw")
                        // .addToSet("password").as("pw_set")
                        .last("id").as("last_id")
                        .last("createTime").as("last_ct")
                ,
                Aggregation.sort(Sort.Direction.ASC, "username"),
                Aggregation.skip(0L),
                Aggregation.limit(10L)
                // Aggregation.project("id", "maxId", "username", "password", "createTime")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "user", Map.class);
        log.info("{}", JSONObject.toJSONString(results.getMappedResults()));


    }
}
