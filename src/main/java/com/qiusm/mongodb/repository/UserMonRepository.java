package com.qiusm.mongodb.repository;

import com.qiusm.mongodb.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * 响应式mongo接口
 *
 * @author qiushengming
 */
public interface UserMonRepository extends ReactiveMongoRepository<UserEntity, Long> {
}
