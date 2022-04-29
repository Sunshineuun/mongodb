package com.qiusm.mongodb;

import com.qiusm.mongodb.entity.UserEntity;
import com.qiusm.mongodb.repository.UserMonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
public class MongodbTest extends MongodbApplicationTests {
    @Resource
    private UserMonRepository userMonRepository;

    @Test
    public void toMongodb() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("qiusm");
        user.setPassword("1234");
        user.setCreateTime(LocalDateTime.now());
        Mono<UserEntity> r = userMonRepository.save(user);
        log.info("{}", r.block());
    }

    @Test
    public void reactiveMongodbAdd() {
        Mono<UserEntity> userMono = userMonRepository.findById(1L);
        log.info("reactive mongodb find by id . user : {}", userMono.block());
    }

    @Test
    public void mongodbLog() {
        log.info("mongodb log");
    }
}
