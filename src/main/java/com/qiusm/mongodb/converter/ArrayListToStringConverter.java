package com.qiusm.mongodb.converter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qiushengming
 */
@Component
@ReadingConverter
public class ArrayListToStringConverter implements Converter<List, String> {
    @Override
    public String convert(List source) {
        return JSONObject.toJSONString(source);
    }
}
