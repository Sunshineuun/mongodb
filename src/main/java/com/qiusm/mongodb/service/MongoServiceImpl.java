package com.qiusm.mongodb.service;

import com.alibaba.fastjson.JSONObject;
import com.qiusm.spring.service.MongoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

/**
 * @author qiushengming
 */
@Slf4j
@DubboService(version = "0.0.1")
public class MongoServiceImpl implements MongoService {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 保存挂号登记信息
     *
     * @param s json 字符串
     * @return 默认true
     */
    @Override
    public Boolean insert(String s, String collectionName) {
        try {
            JSONObject jsonobj = JSONObject.parseObject(s);
            mongoTemplate.insert(jsonobj, collectionName);
            return true;
        } catch (Exception e) {
            log.error("params: {}, error_message: {}", s, e.getMessage());
            return false;
        }
    }
}
