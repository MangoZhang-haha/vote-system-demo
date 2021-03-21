package com.config.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Mango
 * @Date: 2021/1/17 19:26:26
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.mapper", "com.pojo.mapper"})
public class MyBatisPlusConfig {

    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }
}
