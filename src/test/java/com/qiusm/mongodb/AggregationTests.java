package com.qiusm.mongodb;

import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;

/**
 * Mongo 聚合查询组装
 *
 * @author qiushengming
 */
public class AggregationTests extends MongodbApplicationTests {

    /**
     * $project: {field: {convert:{imput:'$version', to:'int',onError:0, onNull:0}}} <br>
     * <code>.and()</code>, 相当于增加一个字段，该字段可以进行一些处理，比如类型转换，pow计算，等等 <br>
     * <a href='https://www.mongodb.com/docs/manual/reference/operator/aggregation/project/'>refernce</a>
     */
    @Test
    void name() {
        ProjectionOperation project = Aggregation.project()
                .and(ConvertOperators.Convert.convertValue("version").to("int").onErrorReturn(0).onNullReturn(0))
                .as("version")
                .and("v").pow(1).as("v");
    }
}
