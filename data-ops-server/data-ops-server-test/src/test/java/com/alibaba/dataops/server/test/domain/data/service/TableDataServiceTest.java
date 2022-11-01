package com.alibaba.dataops.server.test.domain.data.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.enums.CollationEnum;
import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.data.api.enums.IndexTypeEnum;
import com.alibaba.dataops.server.domain.data.api.model.TableColumnDTO;
import com.alibaba.dataops.server.domain.data.api.model.TableDTO;
import com.alibaba.dataops.server.domain.data.api.model.TableIndexDTO;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.table.TablePageQueryParam;
import com.alibaba.dataops.server.domain.data.api.param.table.TableSelector;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateExecuteParam;
import com.alibaba.dataops.server.domain.data.api.service.ConsoleDataService;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.domain.data.api.service.TableDataService;
import com.alibaba.dataops.server.test.common.BaseTest;
import com.alibaba.dataops.server.test.domain.data.service.dialect.DialectProperties;
import com.alibaba.dataops.server.test.domain.data.utils.TestUtils;
import com.alibaba.dataops.server.tools.base.enums.YesOrNoEnum;
import com.alibaba.dataops.server.tools.common.util.EasyCollectionUtils;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据源测试
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class TableDataServiceTest extends BaseTest {
    /**
     * 表名
     */
    public static final String TABLE_NAME = "data_ops_table_test_" + System.currentTimeMillis();

    @Resource
    private DataSourceDataService dataSourceDataService;
    @Resource
    private ConsoleDataService consoleDataService;
    @Autowired
    private List<DialectProperties> dialectPropertiesList;
    @Resource
    private JdbcTemplateDataService jdbcTemplateDataService;
    @Resource
    private TableDataService tableDataService;

    @Test
    @Order(1)
    public void table() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();
            Long consoleId = TestUtils.nextLong();

            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceDataService.create(dataSourceCreateParam);

            // 创建控制台
            ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
            consoleCreateParam.setDataSourceId(dataSourceId);
            consoleCreateParam.setConsoleId(consoleId);
            consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
            consoleDataService.create(consoleCreateParam);

            // 创建表结构
            TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
            templateQueryParam.setConsoleId(consoleId);
            templateQueryParam.setDataSourceId(dataSourceId);
            templateQueryParam.setSql(dialectProperties.getCrateTableSql(TABLE_NAME));
            jdbcTemplateDataService.execute(templateQueryParam);

            //  查询表结构
            TablePageQueryParam tablePageQueryParam = new TablePageQueryParam();
            tablePageQueryParam.setDataSourceId(dataSourceId);
            tablePageQueryParam.setDatabaseName(dialectProperties.getDatabaseName());
            tablePageQueryParam.setTableName(dialectProperties.toCase(TABLE_NAME));
            List<TableDTO> tableList = tableDataService.pageQuery(tablePageQueryParam, TableSelector.builder()
                .columnList(Boolean.TRUE)
                .indexList(Boolean.TRUE)
                .build()).getData();
            log.info("分析数据返回{}", JSON.toJSONString(tableList));
            Assertions.assertEquals(1L, tableList.size(), "查询表结构失败");
            TableDTO table = tableList.get(0);
            Assertions.assertEquals(dialectProperties.toCase(TABLE_NAME), table.getName(), "查询表结构失败");
            Assertions.assertEquals("测试表", table.getComment(), "查询表结构失败");

            List<TableColumnDTO> columnList = table.getColumnList();
            Assertions.assertEquals(4L, columnList.size(), "查询表结构失败");
            TableColumnDTO id = columnList.get(0);
            Assertions.assertEquals(dialectProperties.toCase("id"), id.getName(), "查询表结构失败");
            Assertions.assertEquals("主键自增", id.getComment(), "查询表结构失败");
            Assertions.assertEquals(YesOrNoEnum.YES.getCode(), id.getAutoIncrement(), "查询表结构失败");
            Assertions.assertEquals(YesOrNoEnum.NO.getCode(), id.getNullable(), "查询表结构失败");

            TableColumnDTO string = columnList.get(3);
            Assertions.assertEquals(dialectProperties.toCase("string"), string.getName(), "查询表结构失败");
            Assertions.assertEquals(YesOrNoEnum.YES.getCode(), string.getNullable(), "查询表结构失败");
            Assertions.assertEquals("DATA", TestUtils.unWrapperDefaultValue(string.getDefaultValue()), "查询表结构失败");

            List<TableIndexDTO> tableIndexList = table.getIndexList();
            Assertions.assertEquals(4L, tableIndexList.size(), "查询表结构失败");
            Map<String, TableIndexDTO> tableIndexMap = EasyCollectionUtils.toIdentityMap(tableIndexList,
                TableIndexDTO::getName);
            TableIndexDTO idxDate = tableIndexMap.get(dialectProperties.toCase(TABLE_NAME + "_idx_date"));
            Assertions.assertEquals("日期索引", idxDate.getComment(), "查询表结构失败");
            Assertions.assertEquals(IndexTypeEnum.NORMAL.getCode(), idxDate.getType(), "查询表结构失败");
            Assertions.assertEquals(1L, idxDate.getColumnList().size(), "查询表结构失败");
            Assertions.assertEquals(dialectProperties.toCase("date"), idxDate.getColumnList().get(0).getName(), "查询表结构失败");
            Assertions.assertEquals(CollationEnum.DESC.getCode(), idxDate.getColumnList().get(0).getCollation(),
                "查询表结构失败");

            TableIndexDTO ukNumber = tableIndexMap.get(dialectProperties.toCase(TABLE_NAME + "_uk_number"));
            Assertions.assertEquals("唯一索引", ukNumber.getComment(), "查询表结构失败");
            Assertions.assertEquals(IndexTypeEnum.UNIQUE.getCode(), ukNumber.getType(), "查询表结构失败");

            TableIndexDTO idxNumberString = tableIndexMap.get(dialectProperties.toCase(TABLE_NAME + "_idx_number_string"));
            Assertions.assertEquals(2, idxNumberString.getColumnList().size(), "查询表结构失败");
        }

    }

    @Test
    @Order(Integer.MAX_VALUE)
    public void dropTable() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            try {
                DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
                Long dataSourceId = TestUtils.nextLong();
                Long consoleId = TestUtils.nextLong();

                DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
                dataSourceCreateParam.setDataSourceId(dataSourceId);
                dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
                dataSourceCreateParam.setUrl(dialectProperties.getUrl());
                dataSourceCreateParam.setUsername(dialectProperties.getUsername());
                dataSourceCreateParam.setPassword(dialectProperties.getPassword());
                dataSourceDataService.create(dataSourceCreateParam);

                // 创建控制台
                ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
                consoleCreateParam.setDataSourceId(dataSourceId);
                consoleCreateParam.setConsoleId(consoleId);
                consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
                consoleDataService.create(consoleCreateParam);

                // 创建表结构
                TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
                templateQueryParam.setConsoleId(consoleId);
                templateQueryParam.setDataSourceId(dataSourceId);
                templateQueryParam.setSql(dialectProperties.getDropTableSql(TABLE_NAME));
                jdbcTemplateDataService.execute(templateQueryParam);
            } catch (Exception e) {
                log.warn("删除表结构失败.", e);
            }
        }
    }

}
