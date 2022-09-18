package com.alibaba.dataops.server.start.test.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.alibaba.dataops.server.start.test.common.BaseTest;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.SqliteTypeConvert;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 生成mybatis 的mapper
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class MybatisGeneratorTest extends BaseTest {
    @Resource
    private DataSource dataSource;

    @Test
    public void coreGenerator() {
        doGenerator("core", Lists.newArrayList("test"));
    }

    private void doGenerator(String repository, List<String> tableList) {

        // 当前项目地址 拿到的是data-ops-server-start地址
        String outputDir = System.getProperty("user.dir")
            + "/../data-ops-server-domain/data-ops-server-domain-core/data-ops-server-domain-" + repository
            + "-repository/src/main/java";
        String xmlDir = System.getProperty("user.dir")
            + "/../data-ops-server-domain/data-ops-server-domain-core/data-ops-server-domain-" + repository
            + "-repository/src/main/resources/com/alibaba/dataops/server/domain/" + repository + "/repository";

        // 不要生成service controller
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.service, null);
        pathInfo.put(OutputFile.serviceImpl, null);
        pathInfo.put(OutputFile.xml, xmlDir);
        pathInfo.put(OutputFile.controller, null);

        FastAutoGenerator
            .create(new DataSourceConfig.Builder(dataSource)
                .typeConvert(new SqliteTypeConvert()))
            //全局配置
            .globalConfig(builder -> {
                // 设置作者
                builder.author("data-ops")
                    //执行完毕不打开文件夹
                    .disableOpenDir()
                    // 指定输出目录
                    .outputDir(outputDir);
            })
            //包配置
            .packageConfig(builder -> {
                // 设置父包名
                builder.parent("com.alibaba.dataops.server.domain." + repository + ".repository")
                    //生成实体层
                    .entity("entity")
                    .pathInfo(pathInfo)
                    //生成mapper层
                    .mapper("mapper");
            })
            //策略配置
            .strategyConfig(builder -> {
                // 设置需要生成的表名
                builder.addInclude(tableList)
                    //开启实体类配置
                    .entityBuilder()
                    .formatFileName("%sDO")
                    // 覆盖文件
                    .enableFileOverride()
                    //.addTableFills(new Column("gmt_create", FieldFill.INSERT)) // 表字段填充
                    //.addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE)) // 表字段填充
                    //开启lombok
                    .enableLombok()
                    .mapperBuilder()
                    // 覆盖文件
                    .enableFileOverride()
                ;

            })
            //模板配置
            .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
            //执行
            .execute();
    }

}