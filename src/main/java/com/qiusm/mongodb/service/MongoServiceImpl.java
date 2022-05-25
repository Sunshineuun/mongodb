package com.qiusm.mongodb.service;

import com.alibaba.fastjson.JSONObject;
import com.qiusm.spring.service.MongoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiushengming
 */
@Slf4j
@DubboService(version = "0.0.1")
public class MongoServiceImpl implements MongoService, DisposableBean {

    @Resource
    private MongoTemplate mongoTemplate;

    private final List<JSONObject> cacheList = new ArrayList<>(10000);

    private static final Boolean LOCK = true;
    final static private Integer UPPER_LIMIT = 1;

    private Integer count = 0;

    /**
     * 保存挂号登记信息
     *
     * @param s json 字符串
     * @return 默认true
     */
    @Override
    public Boolean insert(String s, String collectionName) {
        count++;
        try {
            JSONObject jsonobj = JSONObject.parseObject(s);
            cacheList.add(jsonobj);
            if (cacheList.size() > UPPER_LIMIT) {
                synchronized (LOCK) {
                    mongoTemplate.insert(cacheList, collectionName);
                    // 在数据量少的情况下，通过bulkOps插入，无法存储数据到mongo中 todo
//                    BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, collectionName);
//                    BulkOperations r = bulkOps.insert(cacheList);
//                    r.execute();
                    cacheList.clear();
                    log.info("已经处理数量：{}", count);
                }
            }

            return true;
        } catch (Exception e) {
            log.error("params: {}, error_message: {}", s, e.getMessage());
            return false;
        }
    }

    @Override
    public void destroy() throws Exception {
    }
}
