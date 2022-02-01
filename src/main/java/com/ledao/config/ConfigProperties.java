package com.ledao.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件属性实体类
 *
 * @author LeDao
 * @company
 * @create 2022-02-01 11:22
 */
@Data
@Component
public class ConfigProperties {

    @Value("${maxBorrowBookSize}")
    private Integer maxBorrowBookSize;
}
