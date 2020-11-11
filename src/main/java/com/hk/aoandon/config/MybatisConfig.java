package com.hk.aoandon.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author kai.hu
 * @date 2020/11/4 11:53
 */
@MapperScan(basePackages = "com.hk.aoandon.mapper")
@Configuration
@Log4j2
public class MybatisConfig {
    /**
     * 配置postgresql数据源
     *
     * @return 数据源
     */
    @Bean(name = "postgresqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.postgresql")
    public DataSource udalDataSource() {
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        log.info("postgresql数据源初始化.");
        return dataSource;
    }
}
