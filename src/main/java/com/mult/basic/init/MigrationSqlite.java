package com.mult.basic.init;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

/**
 * @author Weirdo
 * 项目启动时同步数据库结构
 */
public class MigrationSqlite {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 数据库结构迁移入口
     */
    public void migrate() {
        //初始化flyway类
        Flyway flyway = new Flyway();
        //设置加载数据库的相关配置信息
        flyway.setDataSource(dataSource);
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