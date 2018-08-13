package com.mult.basic.init;

import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

public class MigrationSqlite {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void migrate() {
        //初始化flyway类
        Flyway flyway = new Flyway();
        //设置加载数据库的相关配置信息
        flyway.setDataSource(dataSource);
        // 设置接受flyway进行版本管理的多个数据库
        //flyway.setSchemas("mult");
        //设置存放flyway metadata数据的表名，默认"schema_version"，可不写
        flyway.setTable("schema_version");
        //设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径，默认"db/migration"，可不写
        flyway.setLocations("db");
        //设置sql脚本文件的编码，默认"UTF-8"，可不写
        flyway.setEncoding("UTF-8");
        flyway.setBaselineOnMigrate(true);
        flyway.migrate();
    }
}