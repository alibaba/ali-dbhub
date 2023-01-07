package com.alibaba.dbhub.server.test.domain.data.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.support.enums.CollationEnum;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.enums.IndexTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.Sql;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.model.TableColumn;
import com.alibaba.dbhub.server.domain.support.model.TableIndex;
import com.alibaba.dbhub.server.domain.support.operations.ConsoleOperations;
import com.alibaba.dbhub.server.domain.support.operations.DataSourceOperations;
import com.alibaba.dbhub.server.domain.support.operations.JdbcOperations;
import com.alibaba.dbhub.server.domain.support.operations.SqlOperations;
import com.alibaba.dbhub.server.domain.support.operations.TableOperations;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCreateParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.support.param.sql.SqlAnalyseParam;
import com.alibaba.dbhub.server.domain.support.param.table.DropParam;
import com.alibaba.dbhub.server.domain.support.param.table.ShowCreateTableParam;
import com.alibaba.dbhub.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableSelector;
import com.alibaba.dbhub.server.domain.support.param.template.TemplateExecuteParam;
import com.alibaba.dbhub.server.test.common.BaseTest;
import com.alibaba.dbhub.server.test.domain.data.service.dialect.DialectProperties;
import com.alibaba.dbhub.server.test.domain.data.utils.TestUtils;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;
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
public class TableOperationsTest extends BaseTest {
    /**
     * 表名
     */
    public static final String TABLE_NAME = "data_ops_table_test_" + System.currentTimeMillis();

    @Resource
    private DataSourceOperations dataSourceOperations;
    @Resource
    private ConsoleOperations consoleOperations;
    @Autowired
    private List<DialectProperties> dialectPropertiesList;
    @Resource
    private JdbcOperations jdbcOperations;
    @Resource
    private TableOperations tableOperations;
    @Resource
    private SqlOperations sqlOperations;

    @Test
    @Order(1)
    public void table() {
        for (DialectProperties dialectProperties : dialectPropertiesList) {
            DbTypeEnum dbTypeEnum = dialectProperties.getDbType();
            Long dataSourceId = TestUtils.nextLong();
            Long consoleId = TestUtils.nextLong();

            // 准备上下文
            putConnect(dialectProperties.getUrl(), dialectProperties.getUsername(), dialectProperties.getPassword(),
                dialectProperties.getDbType(), dialectProperties.getDatabaseName(), dataSourceId, consoleId);

            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceOperations.create(dataSourceCreateParam);

            // 创建控制台
            ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
            consoleCreateParam.setDataSourceId(dataSourceId);
            consoleCreateParam.setConsoleId(consoleId);
            consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
            consoleOperations.create(consoleCreateParam);

            // 创建表结构
            // 创建表结构
            List<Sql> sqlList = sqlOperations.analyse(SqlAnalyseParam.builder().dataSourceId(dataSourceId)
                .sql(dialectProperties.getCrateTableSql(TABLE_NAME)).build());
            for (Sql sql : sqlList) {
                TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
                templateQueryParam.setConsoleId(consoleId);
                templateQueryParam.setDataSourceId(dataSourceId);
                templateQueryParam.setSql(sql.getSql());
                jdbcOperations.execute(templateQueryParam);
            }

            // 查询建表语句
            ShowCreateTableParam showCreateTableParam = ShowCreateTableParam.builder()
                .dataSourceId(dataSourceId)
                .databaseName(dialectProperties.getDatabaseName())
                .tableName(dialectProperties.toCase(TABLE_NAME))
                .build();
            String createTable = tableOperations.showCreateTable(showCreateTableParam);
            log.info("建表语句:{}", createTable);
            if (dialectProperties.getDbType() != DbTypeEnum.H2) {
                Assertions.assertTrue(createTable.contains(dialectProperties.toCase(TABLE_NAME)), "查询表结构失败");
            }

            //  查询表结构
            TablePageQueryParam tablePageQueryParam = new TablePageQueryParam();
            tablePageQueryParam.setDataSourceId(dataSourceId);
            tablePageQueryParam.setDatabaseName(dialectProperties.getDatabaseName());
            tablePageQueryParam.setTableName(dialectProperties.toCase(TABLE_NAME));
            List<Table> tableList = tableOperations.pageQuery(tablePageQueryParam, TableSelector.builder()
                .columnList(Boolean.TRUE)
                .indexList(Boolean.TRUE)
                .build()).getData();
            log.info("分析数据返回{}", JSON.toJSONString(tableList));
            Assertions.assertEquals(1L, tableList.size(), "查询表结构失败");
            Table table = tableList.get(0);
            Assertions.assertEquals(dialectProperties.toCase(TABLE_NAME), table.getName(), "查询表结构失败");
            Assertions.assertEquals("测试表", table.getComment(), "查询表结构失败");

            List<TableColumn> columnList = table.getColumnList();
            Assertions.assertEquals(4L, columnList.size(), "查询表结构失败");
            TableColumn id = columnList.get(0);
            Assertions.assertEquals(dialectProperties.toCase("id"), id.getName(), "查询表结构失败");
            Assertions.assertEquals("主键自增", id.getComment(), "查询表结构失败");
            Assertions.assertTrue(id.getAutoIncrement(), "查询表结构失败");
            Assertions.assertFalse(id.getNullable(), "查询表结构失败");

            TableColumn string = columnList.get(3);
            Assertions.assertEquals(dialectProperties.toCase("string"), string.getName(), "查询表结构失败");
            Assertions.assertTrue(string.getNullable(), "查询表结构失败");
            Assertions.assertEquals("DATA", TestUtils.unWrapperDefaultValue(string.getDefaultValue()),
                "查询表结构失败");

            List<TableIndex> tableIndexList = table.getIndexList();
            Assertions.assertEquals(4L, tableIndexList.size(), "查询表结构失败");
            Map<String, TableIndex> tableIndexMap = EasyCollectionUtils.toIdentityMap(tableIndexList,
                TableIndex::getName);
            TableIndex idxDate = tableIndexMap.get(dialectProperties.toCase(TABLE_NAME + "_idx_date"));
            Assertions.assertEquals("日期索引", idxDate.getComment(), "查询表结构失败");
            Assertions.assertEquals(IndexTypeEnum.NORMAL.getCode(), idxDate.getType(), "查询表结构失败");
            Assertions.assertEquals(1L, idxDate.getColumnList().size(), "查询表结构失败");
            Assertions.assertEquals(dialectProperties.toCase("date"), idxDate.getColumnList().get(0).getName(),
                "查询表结构失败");
            Assertions.assertEquals(CollationEnum.DESC.getCode(), idxDate.getColumnList().get(0).getCollation(),
                "查询表结构失败");

            TableIndex ukNumber = tableIndexMap.get(dialectProperties.toCase(TABLE_NAME + "_uk_number"));
            Assertions.assertEquals("唯一索引", ukNumber.getComment(), "查询表结构失败");
            Assertions.assertEquals(IndexTypeEnum.UNIQUE.getCode(), ukNumber.getType(), "查询表结构失败");

            TableIndex idxNumberString = tableIndexMap.get(dialectProperties.toCase(TABLE_NAME + "_idx_number_string"));
            Assertions.assertEquals(2, idxNumberString.getColumnList().size(), "查询表结构失败");

            // 删除表结构
            DropParam dropParam = DropParam.builder()
                .dataSourceId(dataSourceId)
                .databaseName(dialectProperties.getDatabaseName())
                .tableName(dialectProperties.toCase(TABLE_NAME))
                .build();
            tableOperations.drop(dropParam);
            //  查询表结构
            tablePageQueryParam = new TablePageQueryParam();
            tablePageQueryParam.setDataSourceId(dataSourceId);
            tablePageQueryParam.setDatabaseName(dialectProperties.getDatabaseName());
            tablePageQueryParam.setTableName(dialectProperties.toCase(TABLE_NAME));
            tableList = tableOperations.pageQuery(tablePageQueryParam, TableSelector.builder()
                .columnList(Boolean.TRUE)
                .indexList(Boolean.TRUE)
                .build()).getData();
            log.info("删除表后数据返回{}", JSON.toJSONString(tableList));
            Assertions.assertEquals(0L, tableList.size(), "查询表结构失败");

            removeConnect();
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
                dataSourceOperations.create(dataSourceCreateParam);

                // 创建控制台
                ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
                consoleCreateParam.setDataSourceId(dataSourceId);
                consoleCreateParam.setConsoleId(consoleId);
                consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
                consoleOperations.create(consoleCreateParam);

                // 创建表结构
                TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
                templateQueryParam.setConsoleId(consoleId);
                templateQueryParam.setDataSourceId(dataSourceId);
                templateQueryParam.setSql(dialectProperties.getDropTableSql(TABLE_NAME));
                jdbcOperations.execute(templateQueryParam);
            } catch (Exception e) {
                log.warn("删除表结构失败.", e);
            }
        }
    }

}
