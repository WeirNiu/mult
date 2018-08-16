package com.mult.basic.init;

import org.apache.commons.dbcp.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Weirdo
 * 项目启动时同步数据库结构
 */
@Component
public class MigrationSqlite {

    @Value("#{jdbc}")
    Properties jdbc;

    public DataSource getDataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(jdbc.getProperty("jdbc.driver"));
        ds.setUrl(jdbc.getProperty("jdbc.url"));
        ds.setUsername(jdbc.getProperty("jdbc.username"));
        ds.setPassword(jdbc.getProperty("jdbc.password"));
        return ds;
    }

    /**
     * 数据库结构迁移入口
     */
    @PostConstruct
    public void migrate() {
        //初始化flyway类
        Flyway flyway = new Flyway();
        //设置加载数据库的相关配置信息
        flyway.setDataSource(getDataSource());
        //设置接受flyway进行版本管理的多个数据库setSchemas("")
        //设置存放flyway metadata数据的表名，默认"schema_version"，可不写;
        flyway.setTable("schema_version");
        //设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径，默认"db/migration"，可不写
        flyway.setLocations("db");
        //设置sql脚本文件的编码，默认"UTF-8"
        flyway.setEncoding("UTF-8");
        flyway.setBaselineOnMigrate(true);
        flyway.migrate();
    }
}