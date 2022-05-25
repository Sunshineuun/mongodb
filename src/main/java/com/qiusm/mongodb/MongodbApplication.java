package com.qiusm.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(exclude = {
//        DubboAutoConfiguration.class,
//        DubboMetadataAutoConfiguration.class,
//        DubboServiceAutoConfiguration.class,
//        DubboOpenFeignAutoConfiguration.class,
//        DubboServiceDiscoveryAutoConfiguration.class,
//        DubboMetadataEndpointAutoConfiguration.class,
//        DubboLoadBalancedRestTemplateAutoConfiguration.class,
//        DubboServiceRegistrationAutoConfiguration.class
})
public class MongodbApplication {

    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled","false");
        SpringApplication.run(MongodbApplication.class, args);
        log.info("Mongo DB 启动成功！！！");
    }

}
