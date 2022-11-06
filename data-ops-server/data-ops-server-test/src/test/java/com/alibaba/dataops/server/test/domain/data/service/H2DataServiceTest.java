package com.alibaba.dataops.server.test.domain.data.service;

import javax.annotation.Resource;

import com.alibaba.dataops.server.domain.data.api.service.ConsoleDataService;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.api.service.DatabaseDataService;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.domain.data.api.service.SqlDataService;
import com.alibaba.dataops.server.domain.data.api.service.TableDataService;
import com.alibaba.dataops.server.test.common.BaseTest;
import com.alibaba.dataops.server.test.domain.data.utils.TestUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * h2的data服测试
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class H2DataServiceTest extends BaseTest {
    private static final String URL = "jdbc:h2:mem:data-ops-test;MODE=MYSQL";

    private static final long DATA_SOURCE_ID = TestUtils.nextLong();
    private static final long CONSOLE_ID = TestUtils.nextLong();
    private static final String DATABASE_NAME = "PUBLIC";

    private static final String DATA_NAME = "姓名";
    @Resource
    private DataSourceDataService dataSourceDataService;
    @Resource
    private ConsoleDataService consoleDataService;
    @Resource
    private JdbcTemplateDataService jdbcTemplateDataService;
    @Resource
    private SqlDataService sqlDataService;
    @Resource
    private DatabaseDataService databaseDataService;
    @Resource
    private TableDataService tableDataService;

    //@Test
    //@Order(1)
    //public void prepareData() {
    //    DruidDataSource druidDataSource = new DruidDataSource();
    //    druidDataSource.setDriverClassName(DbTypeEnum.H2.getClassName());
    //    druidDataSource.setUrl(URL);
    //
    //    // 初始化数据库
    //    DatabaseInitializationSettings databaseInitializationSettings = new DatabaseInitializationSettings();
    //    databaseInitializationSettings.setSchemaLocations(Lists.newArrayList("classpath:h2/init.sql"));
    //    DataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer
    //        = new DataSourceScriptDatabaseInitializer(druidDataSource, databaseInitializationSettings);
    //    dataSourceScriptDatabaseInitializer.initializeDatabase();
    //}
    //
    //@Test
    //@Order(2)
    //public void createDataSource() {
    //    DataSourceCreateParam dataSourceCreateParam = new DataSourceCreateParam();
    //    dataSourceCreateParam.setDataSourceId(DATA_SOURCE_ID);
    //    dataSourceCreateParam.setDbType(DbTypeEnum.H2.getCode());
    //    dataSourceCreateParam.setUrl(URL);
    //    ActionResult actionResult = dataSourceDataService.create(dataSourceCreateParam);
    //    Assertions.assertTrue(actionResult.success(), "创建数据库连接池失败");
    //}
    //
    //@Test
    //@Order(2)
    //public void createConsole() {
    //    ConsoleCreateParam consoleCreateParam = new ConsoleCreateParam();
    //    consoleCreateParam.setDataSourceId(DATA_SOURCE_ID);
    //    consoleCreateParam.setConsoleId(CONSOLE_ID);
    //    consoleCreateParam.setDatabaseName(DATABASE_NAME);
    //    ActionResult actionResult = consoleDataService.create(consoleCreateParam);
    //    Assertions.assertTrue(actionResult.success(), "创建控制台失败");
    //}
    //
    //@Test
    //@Order(3)
    //public void query() {
    //    TemplateExecuteParam templateExecuteParam = new TemplateExecuteParam();
    //    templateExecuteParam.setConsoleId(CONSOLE_ID);
    //    templateExecuteParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateExecuteParam.setSql("select * from test_query where id=1;");
    //    ExecuteResultDTO executeResult = jdbcTemplateDataService.execute(templateExecuteParam).getData();
    //    log.info("查询数据返回{}", JSON.toJSONString(executeResult));
    //    List<List<CellDTO>> dataList = executeResult.getDataList();
    //    Assertions.assertEquals(1, dataList.size(), "查询语句异常");
    //    List<CellDTO> data1 = dataList.get(0);
    //    Assertions.assertEquals(DATA_NAME, data1.get(1).getStringValue(), "未查询到姓名");
    //}
    //
    //@Test
    //@Order(4)
    //public void insert() {
    //    TemplateExecuteParam templateExecuteParam = new TemplateExecuteParam();
    //    templateExecuteParam.setConsoleId(CONSOLE_ID);
    //    templateExecuteParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateExecuteParam.setSql(
    //        "INSERT INTO `test_query` (id,name,date,number) VALUES (9999,'姓名insert','2022-01-02',1234);");
    //    ExecuteResultDTO executeResult = jdbcTemplateDataService.execute(templateExecuteParam).getData();
    //    log.info("插入数据返回:{}", executeResult);
    //    Assertions.assertEquals(1, executeResult.getUpdateCount(), " 插入数据失败");
    //
    //    // 查数据
    //    templateExecuteParam = new TemplateExecuteParam();
    //    templateExecuteParam.setConsoleId(CONSOLE_ID);
    //    templateExecuteParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateExecuteParam.setSql("select * from test_query where id=9999;");
    //    executeResult = jdbcTemplateDataService.execute(templateExecuteParam).getData();
    //    log.info("查询数据返回{}", JSON.toJSONString(executeResult));
    //    List<List<CellDTO>> dataList = executeResult.getDataList();
    //    Assertions.assertEquals(1, dataList.size(), "查询语句异常");
    //    List<CellDTO> data1 = dataList.get(0);
    //    Assertions.assertEquals("姓名insert", data1.get(1).getStringValue(), "未查询到姓名");
    //}
    //
    //@Test
    //@Order(5)
    //public void update() {
    //    TemplateUpdateParam templateUpdateParam = new TemplateUpdateParam();
    //    templateUpdateParam.setConsoleId(CONSOLE_ID);
    //    templateUpdateParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateUpdateParam.setSql(
    //        "update `test_query` set name='姓名update' where id=9999;");
    //    Integer count = jdbcTemplateDataService.update(templateUpdateParam).getData();
    //    log.info("查询数据返回:{}", count);
    //
    //    // 查数据
    //    TemplateQueryParam templateQueryParam = new TemplateQueryParam();
    //    templateQueryParam.setConsoleId(CONSOLE_ID);
    //    templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateQueryParam.setSql("select * from test_query where id=9999;");
    //    List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
    //    log.info("查询数据返回{}", JSON.toJSONString(dataList));
    //    Assertions.assertEquals(1, dataList.size(), "查询语句异常");
    //    Map<String, Object> data1 = dataList.get(0);
    //    Assertions.assertEquals("姓名update", data1.get("name"), "未查询到姓名");
    //}
    //
    //@Test
    //@Order(6)
    //public void delete() {
    //    TemplateUpdateParam templateUpdateParam = new TemplateUpdateParam();
    //    templateUpdateParam.setConsoleId(CONSOLE_ID);
    //    templateUpdateParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateUpdateParam.setSql(
    //        "delete from  `test_query` where id=9999;");
    //    Integer count = jdbcTemplateDataService.update(templateUpdateParam).getData();
    //    log.info("查询数据返回:{}", count);
    //
    //    // 查数据
    //    TemplateQueryParam templateQueryParam = new TemplateQueryParam();
    //    templateQueryParam.setConsoleId(CONSOLE_ID);
    //    templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateQueryParam.setSql("select * from test_query where id=9999;");
    //    List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
    //    log.info("查询数据返回{}", JSON.toJSONString(dataList));
    //    Assertions.assertEquals(0, dataList.size(), "查询语句异常");
    //}
    //
    //@Test
    //@Order(6)
    //public void errorSql() {
    //    Assertions.assertThrows(BadSqlGrammarException.class, () -> {
    //        // 异常sql
    //        TemplateUpdateParam templateUpdateParam = new TemplateUpdateParam();
    //        templateUpdateParam.setConsoleId(CONSOLE_ID);
    //        templateUpdateParam.setDataSourceId(DATA_SOURCE_ID);
    //        templateUpdateParam.setSql(
    //            "delete from1  `test_query` where id=9999;");
    //        jdbcTemplateDataService.update(templateUpdateParam);
    //    }, "关闭连接池失败");
    //
    //    Assertions.assertThrows(BadSqlGrammarException.class, () -> {
    //        // 异常sql
    //        TemplateQueryParam templateQueryParam = new TemplateQueryParam();
    //        templateQueryParam.setConsoleId(CONSOLE_ID);
    //        templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
    //        templateQueryParam.setSql("select * from test_query where1 id=9999;");
    //        jdbcTemplateDataService.queryForList(templateQueryParam);
    //    }, "关闭连接池失败");
    //}
    //
    //@Test
    //@Order(7)
    //public void showTables() {
    //    // 异常sql
    //    TemplateQueryParam templateQueryParam = new TemplateQueryParam();
    //    templateQueryParam.setConsoleId(CONSOLE_ID);
    //    templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateQueryParam.setSql("show tables;");
    //    List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
    //    log.info("查询数据返回{}", JSON.toJSONString(dataList));
    //}
    //
    //@Test
    //@Order(8)
    //public void showDatabases() {
    //    // 异常sql
    //    TemplateQueryParam templateQueryParam = new TemplateQueryParam();
    //    templateQueryParam.setConsoleId(CONSOLE_ID);
    //    templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateQueryParam.setSql("show databases;");
    //    List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
    //    log.info("查询数据返回{}", JSON.toJSONString(dataList));
    //}
    //
    //@Test
    //@Order(9)
    //public void explain() {
    //    // 异常sql
    //    TemplateQueryParam templateQueryParam = new TemplateQueryParam();
    //    templateQueryParam.setConsoleId(CONSOLE_ID);
    //    templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateQueryParam.setSql("explain select * from test_query where id=9999;");
    //    List<Map<String, Object>> dataList = jdbcTemplateDataService.queryForList(templateQueryParam).getData();
    //    log.info("查询数据返回{}", JSON.toJSONString(dataList));
    //}
    //
    //@Test
    //@Order(10)
    //public void execute() {
    //    TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
    //    templateQueryParam.setConsoleId(CONSOLE_ID);
    //    templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateQueryParam.setSql("select * from test_query where id=1;");
    //    ExecuteResultDTO executeResult = jdbcTemplateDataService.execute(templateQueryParam).getData();
    //    log.info("查询数据返回{}", JSON.toJSONString(executeResult));
    //    List<CellDTO> headerList = executeResult.getHeaderList();
    //    Assertions.assertEquals(4, headerList.size(), "查询结果异常");
    //    List<List<CellDTO>> dataList = executeResult.getDataList();
    //    Assertions.assertEquals(1, dataList.size(), "查询结果异常");
    //    List<CellDTO> cellList = dataList.get(0);
    //    Assertions.assertEquals(4, cellList.size(), "查询结果异常");
    //    Assertions.assertEquals(CellTypeEnum.BIG_DECIMAL.getCode(), cellList.get(0).getType(), "查询结果异常");
    //    Assertions.assertEquals(CellTypeEnum.DATA.getCode(), cellList.get(2).getType(), "查询结果异常");
    //}
    //
    //@Test
    //@Order(11)
    //public void analyse() {
    //    SqlAnalyseParam sqlAnalyseParam = new SqlAnalyseParam();
    //    sqlAnalyseParam.setDataSourceId(DATA_SOURCE_ID);
    //    sqlAnalyseParam.setSql("select * from test_query where id=1;select * from test_query where id=122;");
    //    List<SqlDTO> sqlList = sqlDataService.analyse(sqlAnalyseParam).getData();
    //    log.info("分析数据返回{}", JSON.toJSONString(sqlList));
    //    Assertions.assertEquals(2, sqlList.size(), "查询结果异常");
    //    Assertions.assertEquals("SELECT *\nFROM test_query\nWHERE id = 1;", sqlList.get(0).getSql(), "查询结果异常");
    //    Assertions.assertEquals("SELECT *\nFROM test_query\nWHERE id = 122;", sqlList.get(1).getSql(), "查询结果异常");
    //}
    //
    //
    ///**
    // * 测试sql自动分页
    // */
    //@Test
    //@Order(15)
    //public void pageQuery() {
    //    IntStream.range(0, 1000).forEach(i -> {
    //        TemplateExecuteParam templateExecuteParam = new TemplateExecuteParam();
    //        templateExecuteParam.setConsoleId(CONSOLE_ID);
    //        templateExecuteParam.setDataSourceId(DATA_SOURCE_ID);
    //        templateExecuteParam.setSql(
    //            "INSERT INTO `test_query` (name,date,number) VALUES ('pageQueryName','2022-01-02','" + i + "');");
    //        jdbcTemplateDataService.execute(templateExecuteParam);
    //    });
    //
    //    TemplateExecuteParam templateQueryParam = new TemplateExecuteParam();
    //    templateQueryParam.setConsoleId(CONSOLE_ID);
    //    templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateQueryParam.setSql("select * from test_query where name='pageQueryName';");
    //    ExecuteResultDTO executeResult = jdbcTemplateDataService.execute(templateQueryParam).getData();
    //    Assertions.assertEquals(EasyToolsConstant.MAX_PAGE_SIZE, executeResult.getDataList().size(), "查询结果异常");
    //    Assertions.assertEquals(1, executeResult.getPageNo(), "查询结果异常");
    //    Assertions.assertEquals(EasyToolsConstant.MAX_PAGE_SIZE, executeResult.getPageSize(), "查询结果异常");
    //
    //    templateQueryParam = new TemplateExecuteParam();
    //    templateQueryParam.setConsoleId(CONSOLE_ID);
    //    templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateQueryParam.setSql("select * from test_query where name='pageQueryName';");
    //    templateQueryParam.setPageNo(2);
    //    templateQueryParam.setPageSize(100);
    //    executeResult = jdbcTemplateDataService.execute(templateQueryParam).getData();
    //    Assertions.assertEquals(100, executeResult.getDataList().size(), "查询结果异常");
    //    Assertions.assertEquals(2, executeResult.getPageNo(), "查询结果异常");
    //    Assertions.assertEquals(100, executeResult.getPageSize(), "查询结果异常");
    //    Assertions.assertEquals("100", executeResult.getDataList().get(0).get(3).getStringValue(), "查询结果异常");
    //
    //    templateQueryParam = new TemplateExecuteParam();
    //    templateQueryParam.setConsoleId(CONSOLE_ID);
    //    templateQueryParam.setDataSourceId(DATA_SOURCE_ID);
    //    templateQueryParam.setSql("select * from test_query where name='pageQueryName';");
    //    templateQueryParam.setPageSize(Integer.MAX_VALUE);
    //    executeResult = jdbcTemplateDataService.execute(templateQueryParam).getData();
    //    Assertions.assertEquals(1000, executeResult.getDataList().size(), "查询结果异常");
    //    Assertions.assertEquals(1, executeResult.getPageNo(), "查询结果异常");
    //    Assertions.assertEquals(0, executeResult.getPageSize(), "查询结果异常");
    //}
}