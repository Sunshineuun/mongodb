package com.qiusm.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <code>@Document</code>指定要对应的文档名(表名）
 *
 * @author qiushengming
 * @since 2022-04-17
 */
@Data
@Document(collection = "user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Long maxId;

    private LocalDateTime createTime;

    private String password;

    private String username;

}
