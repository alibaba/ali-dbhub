package com.alibaba.dataops.server.domain.data.core.service.impl;

import com.alibaba.dataops.server.domain.data.api.model.ExecuteResultDTO;
import com.alibaba.dataops.server.domain.data.api.param.template.TemplateExecuteParam;
import com.alibaba.dataops.server.domain.data.api.service.JdbcTemplateDataService;
import com.alibaba.dataops.server.domain.data.core.dialect.SqlExecutor;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.constant.EasyToolsConstant;
import com.alibaba.dataops.server.tools.base.constant.SymbolConstant;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.excption.CommonErrorEnum;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;

import com.github.pagehelper.Page;
import com.github.pagehelper.parser.CountSqlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * jdbc服务实现
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class JdbcTemplateDataServiceImpl implements JdbcTemplateDataService {

    /**
     * 统计语句的执行器
     */
    private static final CountSqlParser COUNT_SQL_PARSER = new CountSqlParser();

    @Override
    public DataResult<ExecuteResultDTO> execute(TemplateExecuteParam param) {
        JdbcDataTemplate jdbcDataTemplate = DataCenterUtils.getJdbcDataTemplate(param.getDataSourceId(),
            param.getConsoleId());

        String sql = param.getSql();
        if (sql == null) {
            // TODO 需要输出具体异常
            throw new BusinessException(CommonErrorEnum.PARAM_ERROR);
        }
        sql = sql.trim();
        // 去除最后的分号
        if (sql.endsWith(SymbolConstant.SEMICOLON)) {
            sql = sql.substring(0, sql.length() - 1);
        }

        // 解析sql
        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(param.getSql(),
            DataCenterUtils.getDruidDbTypeByDataSourceId(param.getDataSourceId()));
        //TODO 其他语句的解析也要补充进来
        // TODO 如果本来就带了分页就不用加了？
        if (sqlStatement instanceof SQLSelectStatement) {
            // 统计行数
            String countSql = COUNT_SQL_PARSER.getSmartCountSql(sql);
            long count = jdbcDataTemplate.count(countSql);

            // 添加分页参数 并查询
            SqlExecutor sqlExecutor = DataCenterUtils.getSqlExecutorByDataSourceId(param.getDataSourceId());
            Page page = new Page(param.getPageNo() == null ? 1 : param.getPageNo(),
                param.getPageSize() == null ? EasyToolsConstant.MAX_PAGE_SIZE : param.getPageSize());
            // TODO 改成用 方言凭借
            //String pageSql = sqlExecutor.getPageSql(param.getSql(), page);
            StringBuilder sqlBuilder = new StringBuilder(sql.length() + 20);
            sqlBuilder.append(sql);
            if (page.getPageSize() > 0) {
                sqlBuilder.append("\n LIMIT " + page.getPageSize() + " ");
            }
            if (page.getStartRow() > 0) {
                sqlBuilder.append("\n OFFSET " + page.getStartRow() + " ");
            }
            String pageSql = sqlBuilder.toString();
            ExecuteResultDTO executeResult = jdbcDataTemplate.query(pageSql);
            // 设置最终的执行sql
            executeResult.setSql(pageSql);

            // 设置分页信息
            executeResult.setTotal(count);
            executeResult.setPageNo(page.getPageNum());
            //TODO pagesize为最大的时候有问题
            executeResult.setPageSize(page.getPageSize());
            return DataResult.of(executeResult);
        } else {
            return DataResult.of(jdbcDataTemplate.update(sql));
        }
    }
}
