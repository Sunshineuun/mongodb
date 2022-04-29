package com.qiusm.mongodb.config;

import com.qiusm.mongodb.converter.ArrayListToStringConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>@EnableReactiveMongoRepositories</code>用于指定mongo存储库的指定路径<br>
 *
 * @author qiushengming
 */
@Configuration
public class MongoConfig {

    /**
     * Mongo 自定义类型转换
     */
    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new ArrayListToStringConverter());
        return new MongoCustomConversions(converterList);
    }
}
