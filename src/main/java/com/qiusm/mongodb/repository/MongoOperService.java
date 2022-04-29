package com.qiusm.mongodb.repository;

import com.alibaba.fastjson.JSONObject;
import com.qiusm.mongodb.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author qiushengming
 */
@Slf4j
@Service
public class MongoOperService {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * mongo 聚合操作
     */
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
