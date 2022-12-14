package com.alibaba.dbhub.server.test.domain.data.service;

import java.util.ArrayList;
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
import com.alibaba.dbhub.server.domain.support.model.TableIndexColumn;
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

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ???????????????
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class TableOperationsTest extends BaseTest {
    /**
     * ??????
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

            // ???????????????
            putConnect(dialectProperties.getUrl(), dialectProperties.getUsername(), dialectProperties.getPassword(),
                dialectProperties.getDbType(), dialectProperties.getDatabaseName(), dataSourceId, consoleId);

            DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
            dataSourceCreateParam.setDataSourceId(dataSourceId);
            dataSourceCreateParam.setDbType(dbTypeEnum.getCode());
            dataSourceCreateParam.setUrl(dialectProperties.getUrl());
            dataSourceCreateParam.setUsername(dialectProperties.getUsername());
            dataSourceCreateParam.setPassword(dialectProperties.getPassword());
            dataSourceOperations.create(dataSourceCreateParam);

            // ???????????????
            ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
            consoleCreateParam.setDataSourceId(dataSourceId);
            consoleCreateParam.setConsoleId(consoleId);
            consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
            consoleOperations.create(consoleCreateParam);

            // ???????????????
            List<Sql> sqlList = sqlOperations.analyse(SqlAnalyseParam.builder().dataSourceId(dataSourceId)
                .sql(dialectProperties.getCrateTableSql(TABLE_NAME)).build());
            for (Sql sql : sqlList) {
                TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
                templateQueryParam.setConsoleId(consoleId);
                templateQueryParam.setDataSourceId(dataSourceId);
                templateQueryParam.setSql(sql.getSql());
                jdbcOperations.execute(templateQueryParam);
            }

            // ??????????????????
            ShowCreateTableParam showCreateTableParam = ShowCreateTableParam.builder()
                .dataSourceId(dataSourceId)
                .databaseName(dialectProperties.getDatabaseName())
                .tableName(dialectProperties.toCase(TABLE_NAME))
                .build();
            String createTable = tableOperations.showCreateTable(showCreateTableParam);
            log.info("????????????:{}", createTable);
            if (dialectProperties.getDbType() != DbTypeEnum.H2) {
                Assertions.assertTrue(createTable.contains(dialectProperties.toCase(TABLE_NAME)), "?????????????????????");
            }

            //  ???????????????
            TablePageQueryParam tablePageQueryParam = new TablePageQueryParam();
            tablePageQueryParam.setDataSourceId(dataSourceId);
            tablePageQueryParam.setDatabaseName(dialectProperties.getDatabaseName());
            tablePageQueryParam.setTableName(dialectProperties.toCase(TABLE_NAME));
            List<Table> tableList = tableOperations.pageQuery(tablePageQueryParam, TableSelector.builder()
                .columnList(Boolean.TRUE)
                .indexList(Boolean.TRUE)
                .build()).getData();
            log.info("??????????????????{}", JSON.toJSONString(tableList));
            Assertions.assertEquals(1L, tableList.size(), "?????????????????????");
            Table table = tableList.get(0);
            Assertions.assertEquals(dialectProperties.toCase(TABLE_NAME), table.getName(), "?????????????????????");
            Assertions.assertEquals("?????????", table.getComment(), "?????????????????????");

            List<TableColumn> columnList = table.getColumnList();
            Assertions.assertEquals(4L, columnList.size(), "?????????????????????");
            TableColumn id = columnList.get(0);
            Assertions.assertEquals(dialectProperties.toCase("id"), id.getName(), "?????????????????????");
            Assertions.assertEquals("????????????", id.getComment(), "?????????????????????");
            Assertions.assertTrue(id.getAutoIncrement(), "?????????????????????");
            Assertions.assertFalse(id.getNullable(), "?????????????????????");

            TableColumn string = columnList.get(3);
            Assertions.assertEquals(dialectProperties.toCase("string"), string.getName(), "?????????????????????");
            Assertions.assertTrue(string.getNullable(), "?????????????????????");
            Assertions.assertEquals("DATA", TestUtils.unWrapperDefaultValue(string.getDefaultValue()),
                "?????????????????????");

            List<TableIndex> tableIndexList = table.getIndexList();
            Assertions.assertEquals(3L, tableIndexList.size(), "?????????????????????");
            Map<String, TableIndex> tableIndexMap = EasyCollectionUtils.toIdentityMap(tableIndexList,
                TableIndex::getName);
            TableIndex idxDate = tableIndexMap.get(dialectProperties.toCase(TABLE_NAME + "_idx_date"));
            Assertions.assertEquals("????????????", idxDate.getComment(), "?????????????????????");
            Assertions.assertEquals(IndexTypeEnum.NORMAL.getCode(), idxDate.getType(), "?????????????????????");
            Assertions.assertEquals(1L, idxDate.getColumnList().size(), "?????????????????????");
            Assertions.assertEquals(dialectProperties.toCase("date"), idxDate.getColumnList().get(0).getName(),
                "?????????????????????");
            Assertions.assertEquals(CollationEnum.DESC.getCode(), idxDate.getColumnList().get(0).getCollation(),
                "?????????????????????");

            TableIndex ukNumber = tableIndexMap.get(dialectProperties.toCase(TABLE_NAME + "_uk_number"));
            Assertions.assertEquals("????????????", ukNumber.getComment(), "?????????????????????");
            Assertions.assertEquals(IndexTypeEnum.UNIQUE.getCode(), ukNumber.getType(), "?????????????????????");

            TableIndex idxNumberString = tableIndexMap.get(dialectProperties.toCase(TABLE_NAME + "_idx_number_string"));
            Assertions.assertEquals(2, idxNumberString.getColumnList().size(), "?????????????????????");

            // ???????????????
            DropParam dropParam = DropParam.builder()
                .dataSourceId(dataSourceId)
                .databaseName(dialectProperties.getDatabaseName())
                .tableName(dialectProperties.toCase(TABLE_NAME))
                .build();
            tableOperations.drop(dropParam);
            //  ???????????????
            tablePageQueryParam = new TablePageQueryParam();
            tablePageQueryParam.setDataSourceId(dataSourceId);
            tablePageQueryParam.setDatabaseName(dialectProperties.getDatabaseName());
            tablePageQueryParam.setTableName(dialectProperties.toCase(TABLE_NAME));
            tableList = tableOperations.pageQuery(tablePageQueryParam, TableSelector.builder()
                .columnList(Boolean.TRUE)
                .indexList(Boolean.TRUE)
                .build()).getData();
            log.info("????????????????????????{}", JSON.toJSONString(tableList));
            Assertions.assertEquals(0L, tableList.size(), "?????????????????????");

            // ??????????????????
            testBuildSql(dialectProperties, dataSourceId, consoleId);

            removeConnect();
        }

    }

    private void testBuildSql(DialectProperties dialectProperties, Long dataSourceId, Long consoleId) {
        if (dialectProperties.getDbType() != DbTypeEnum.MYSQL) {
            log.error("???????????????????????????mysql");
            return;
        }
        // ?????????
        //    CREATE TABLE `DATA_OPS_TEMPLATE_TEST_1673093980449`
        //    (
        //    `id`     bigint PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '????????????',
        //    `date`   datetime(3)                          not null COMMENT '??????',
        //    `number` bigint COMMENT '?????????',
        //    `string` VARCHAR(100) default 'DATA' COMMENT '??????',
        //        index DATA_OPS_TEMPLATE_TEST_1673093980449_idx_date (date desc) comment '????????????',
        //        unique DATA_OPS_TEMPLATE_TEST_1673093980449_uk_number (number) comment '????????????',
        //        index DATA_OPS_TEMPLATE_TEST_1673093980449_idx_number_string (number, date) comment '????????????'
        //) COMMENT ='?????????';
        //        * ???????????????????????????????????????
        //* ?????????????????? : ?????????
        //       * ?????????
        //* id   ????????????
        //* date ?????? ??????
        //       * number ?????????
        //       * string  ????????? ??????100 ????????? "DATA"
        //       *
        //* ??????(??????$tableName_ ????????? ???????????????????????????????????????)???
        //* $tableName_idx_date ???????????? ??????
        //       * $tableName_uk_number ????????????
        //       * $tableName_idx_number_string ????????????
        String tableName = dialectProperties.toCase("data_ops_table_test_" + System.currentTimeMillis());
        Table newTable = new Table();
        newTable.setName(tableName);
        newTable.setComment("?????????");
        List<TableColumn> tableColumnList = new ArrayList<>();
        newTable.setColumnList(tableColumnList);
        //id
        TableColumn idTableColumn = new TableColumn();
        idTableColumn.setName("id");
        idTableColumn.setAutoIncrement(Boolean.TRUE);
        idTableColumn.setPrimaryKey(Boolean.TRUE);
        idTableColumn.setNullable(Boolean.FALSE);
        idTableColumn.setComment("????????????");
        idTableColumn.setColumnType("bigint");
        tableColumnList.add(idTableColumn);

        // date
        TableColumn dateTableColumn = new TableColumn();
        dateTableColumn.setName("date");
        dateTableColumn.setNullable(Boolean.FALSE);
        dateTableColumn.setComment("??????");
        dateTableColumn.setColumnType("datetime(3)");
        tableColumnList.add(dateTableColumn);

        // number
        TableColumn numberTableColumn = new TableColumn();
        numberTableColumn.setName("number");
        numberTableColumn.setComment("?????????");
        numberTableColumn.setColumnType("bigint");
        tableColumnList.add(numberTableColumn);

        // string
        TableColumn stringTableColumn = new TableColumn();
        stringTableColumn.setName("string");
        stringTableColumn.setComment("??????");
        stringTableColumn.setColumnType("varchar(100)");
        stringTableColumn.setDefaultValue("DATA");
        tableColumnList.add(stringTableColumn);

        // ??????
        List<TableIndex> tableIndexList = new ArrayList<>();
        newTable.setIndexList(tableIndexList);

        //        index DATA_OPS_TEMPLATE_TEST_1673093980449_idx_date (date desc) comment '????????????',
        tableIndexList.add(TableIndex.builder()
            .name(tableName + "_idx_date")
            .type(IndexTypeEnum.NORMAL.getCode())
            .comment("????????????")
            .columnList(Lists.newArrayList(TableIndexColumn.builder()
                .name("date")
                .collation(CollationEnum.DESC.getCode())
                .build()))
            .build());

        //        unique DATA_OPS_TEMPLATE_TEST_1673093980449_uk_number (number) comment '????????????',
        tableIndexList.add(TableIndex.builder()
            .name(tableName + "_uk_number")
            .type(IndexTypeEnum.UNIQUE.getCode())
            .comment("????????????")
            .columnList(Lists.newArrayList(TableIndexColumn.builder()
                .name("number")
                .build()))
            .build());
        //        index DATA_OPS_TEMPLATE_TEST_1673093980449_idx_number_string (number, date) comment '????????????'
        tableIndexList.add(TableIndex.builder()
            .name(tableName + "_idx_number_string")
            .type(IndexTypeEnum.NORMAL.getCode())
            .comment("????????????")
            .columnList(Lists.newArrayList(TableIndexColumn.builder()
                    .name("number")
                    .build(),
                TableIndexColumn.builder()
                    .name("date")
                    .build()))
            .build());
        // ??????sql
        List<Sql> buildTableSqlList = tableOperations.buildSql(null, newTable);
        log.info("???????????????????????????:{}", JSON.toJSONString(buildTableSqlList));
        for (Sql sql : buildTableSqlList) {
            TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
            templateQueryParam.setConsoleId(consoleId);
            templateQueryParam.setDataSourceId(dataSourceId);
            templateQueryParam.setSql(sql.getSql());
            jdbcOperations.execute(templateQueryParam);
        }

        // ???????????????
        checkTable(tableName, dialectProperties, dataSourceId);

        //  ???????????????????????????
        TablePageQueryParam tablePageQueryParam = new TablePageQueryParam();
        tablePageQueryParam.setDataSourceId(dataSourceId);
        tablePageQueryParam.setDatabaseName(dialectProperties.getDatabaseName());
        tablePageQueryParam.setTableName(dialectProperties.toCase(tableName));
        List<Table> tableList = tableOperations.pageQuery(tablePageQueryParam, TableSelector.builder()
            .columnList(Boolean.TRUE)
            .indexList(Boolean.TRUE)
            .build()).getData();
        log.info("??????????????????{}", JSON.toJSONString(tableList));
        Assertions.assertEquals(1L, tableList.size(), "?????????????????????");
        Table oldTable = tableList.get(0);
        Assertions.assertEquals(dialectProperties.toCase(tableName), oldTable.getName(), "?????????????????????");
        Assertions.assertEquals("?????????", oldTable.getComment(), "?????????????????????");

        // ???????????????
        // ??????sql
        log.info("oldTable???{}", JSON.toJSONString(oldTable));
        log.info("newTable???{}", JSON.toJSONString(newTable));
        buildTableSqlList = tableOperations.buildSql(oldTable, newTable);
        log.info("??????????????????:{}", JSON.toJSONString(buildTableSqlList));
        Assertions.assertTrue(buildTableSqlList.isEmpty(), "??????sql??????");
        //  ?????????????????? ?????????2?????????
        tablePageQueryParam = new TablePageQueryParam();
        tablePageQueryParam.setDataSourceId(dataSourceId);
        tablePageQueryParam.setDatabaseName(dialectProperties.getDatabaseName());
        tablePageQueryParam.setTableName(dialectProperties.toCase(tableName));
        tableList = tableOperations.pageQuery(tablePageQueryParam, TableSelector.builder()
            .columnList(Boolean.TRUE)
            .indexList(Boolean.TRUE)
            .build()).getData();
        newTable = tableList.get(0);

        // ????????????

        // ??????????????????
        newTable.getColumnList().add(TableColumn.builder()
            .name("add_string")
            .columnType("varchar(20)")
            .comment("??????????????????")
            .build());

        // ??????????????????
        newTable.getIndexList().add(TableIndex.builder()
            .name(tableName + "_idx_string_new")
            .type(IndexTypeEnum.NORMAL.getCode())
            .comment("?????????????????????")
            .columnList(Lists.newArrayList(TableIndexColumn.builder()
                .name("add_string")
                .collation(CollationEnum.DESC.getCode())
                .build()))
            .build());

        // ?????????????????????
        log.info("oldTable???{}", JSON.toJSONString(oldTable));
        log.info("newTable???{}", JSON.toJSONString(newTable));
        buildTableSqlList = tableOperations.buildSql(oldTable, newTable);
        log.info("??????????????????:{}", JSON.toJSONString(buildTableSqlList));

        // ???????????????
        dropTable(tableName, dialectProperties, dataSourceId);
    }

    private void dropTable(String tableName, DialectProperties dialectProperties, Long dataSourceId) {
        // ???????????????
        DropParam dropParam = DropParam.builder()
            .dataSourceId(dataSourceId)
            .databaseName(dialectProperties.getDatabaseName())
            .tableName(dialectProperties.toCase(tableName))
            .build();
        tableOperations.drop(dropParam);
        //  ???????????????
        TablePageQueryParam tablePageQueryParam = new TablePageQueryParam();
        tablePageQueryParam.setDataSourceId(dataSourceId);
        tablePageQueryParam.setDatabaseName(dialectProperties.getDatabaseName());
        tablePageQueryParam.setTableName(dialectProperties.toCase(tableName));
        List<Table> tableList = tableOperations.pageQuery(tablePageQueryParam, TableSelector.builder()
            .columnList(Boolean.TRUE)
            .indexList(Boolean.TRUE)
            .build()).getData();
        log.info("????????????????????????{}", JSON.toJSONString(tableList));
        Assertions.assertEquals(0L, tableList.size(), "?????????????????????");
    }

    private void checkTable(String tableName, DialectProperties dialectProperties, Long dataSourceId) {
        //  ???????????????
        TablePageQueryParam tablePageQueryParam = new TablePageQueryParam();
        tablePageQueryParam.setDataSourceId(dataSourceId);
        tablePageQueryParam.setDatabaseName(dialectProperties.getDatabaseName());
        tablePageQueryParam.setTableName(dialectProperties.toCase(tableName));
        List<Table> tableList = tableOperations.pageQuery(tablePageQueryParam, TableSelector.builder()
            .columnList(Boolean.TRUE)
            .indexList(Boolean.TRUE)
            .build()).getData();
        log.info("??????????????????{}", JSON.toJSONString(tableList));
        Assertions.assertEquals(1L, tableList.size(), "?????????????????????");
        Table table = tableList.get(0);
        Assertions.assertEquals(dialectProperties.toCase(tableName), table.getName(), "?????????????????????");
        Assertions.assertEquals("?????????", table.getComment(), "?????????????????????");

        List<TableColumn> columnList = table.getColumnList();
        Assertions.assertEquals(4L, columnList.size(), "?????????????????????");
        TableColumn id = columnList.get(0);
        Assertions.assertEquals(dialectProperties.toCase("id"), id.getName(), "?????????????????????");
        Assertions.assertEquals("????????????", id.getComment(), "?????????????????????");
        Assertions.assertTrue(id.getAutoIncrement(), "?????????????????????");
        Assertions.assertFalse(id.getNullable(), "?????????????????????");
        Assertions.assertTrue(id.getPrimaryKey(), "?????????????????????");

        TableColumn string = columnList.get(3);
        Assertions.assertEquals(dialectProperties.toCase("string"), string.getName(), "?????????????????????");
        Assertions.assertTrue(string.getNullable(), "?????????????????????");
        Assertions.assertEquals("DATA", TestUtils.unWrapperDefaultValue(string.getDefaultValue()),
            "?????????????????????");

        List<TableIndex> tableIndexList = table.getIndexList();
        Assertions.assertEquals(3L, tableIndexList.size(), "?????????????????????");
        Map<String, TableIndex> tableIndexMap = EasyCollectionUtils.toIdentityMap(tableIndexList,
            TableIndex::getName);
        TableIndex idxDate = tableIndexMap.get(dialectProperties.toCase(tableName + "_idx_date"));
        Assertions.assertEquals("????????????", idxDate.getComment(), "?????????????????????");
        Assertions.assertEquals(IndexTypeEnum.NORMAL.getCode(), idxDate.getType(), "?????????????????????");
        Assertions.assertEquals(1L, idxDate.getColumnList().size(), "?????????????????????");
        Assertions.assertEquals(dialectProperties.toCase("date"), idxDate.getColumnList().get(0).getName(),
            "?????????????????????");
        Assertions.assertEquals(CollationEnum.DESC.getCode(), idxDate.getColumnList().get(0).getCollation(),
            "?????????????????????");

        TableIndex ukNumber = tableIndexMap.get(dialectProperties.toCase(tableName + "_uk_number"));
        Assertions.assertEquals("????????????", ukNumber.getComment(), "?????????????????????");
        Assertions.assertEquals(IndexTypeEnum.UNIQUE.getCode(), ukNumber.getType(), "?????????????????????");

        TableIndex idxNumberString = tableIndexMap.get(dialectProperties.toCase(tableName + "_idx_number_string"));
        Assertions.assertEquals(2, idxNumberString.getColumnList().size(), "?????????????????????");
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

                // ???????????????
                ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
                consoleCreateParam.setDataSourceId(dataSourceId);
                consoleCreateParam.setConsoleId(consoleId);
                consoleCreateParam.setDatabaseName(dialectProperties.getDatabaseName());
                consoleOperations.create(consoleCreateParam);

                // ???????????????
                TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
                templateQueryParam.setConsoleId(consoleId);
                templateQueryParam.setDataSourceId(dataSourceId);
                templateQueryParam.setSql(dialectProperties.getDropTableSql(TABLE_NAME));
                jdbcOperations.execute(templateQueryParam);
            } catch (Exception e) {
                log.warn("?????????????????????.", e);
            }
        }
    }

}
