package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.domain.data.api.service.ConsoleDataService;
import com.alibaba.dataops.server.domain.data.core.model.DataSourceWrapper;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.common.enums.ErrorEnum;
import com.alibaba.druid.pool.DruidDataSource;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 控制台服务
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class ConsoleDataServiceImpl implements ConsoleDataService {

    @Override
    public ActionResult create(ConsoleCreateParam param) {
        DataSourceWrapper dataSourceWrapper = DataCenterUtils.DATA_SOURCE_CACHE.get(param.getDataSourceId());
        if (dataSourceWrapper == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        DruidDataSource druidDataSource = dataSourceWrapper.getDruidDataSource();

        Long consoleId = param.getConsoleId();
        // 尝试关闭
        close(ConsoleCloseParam.builder().dataSourceId(param.getDataSourceId()).consoleId(consoleId).build());
        Connection connection;
        try {
            connection = druidDataSource.getConnection();
        } catch (SQLException e) {
            throw new BusinessException("连接数据库异常", e);
        }
        // 放入连接队列
        Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = DataCenterUtils.JDBC_TEMPLATE_CACHE.computeIfAbsent(
            param.getDataSourceId(), key -> Maps.newConcurrentMap());
        JdbcDataTemplate jdbcDataTemplate = new JdbcDataTemplate(param.getDataSourceId(), consoleId, connection,
            druidDataSource);
        jdbcDataTemplateMap.put(consoleId, jdbcDataTemplate);
        // 切换到当前database
        jdbcDataTemplate.update("use " + param.getDatabaseName() + ";");
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult close(ConsoleCloseParam param) {
        Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = DataCenterUtils.JDBC_TEMPLATE_CACHE.get(
            param.getDataSourceId());
        if (jdbcDataTemplateMap == null) {
            log.info("数据库连接:{}不需要关闭", param.getDataSourceId());
            return ActionResult.isSuccess();
        }
        JdbcDataTemplate jdbcDataTemplate = jdbcDataTemplateMap.remove(param.getConsoleId());
        if (jdbcDataTemplate == null) {
            log.info("数据库连接:{}不需要关闭", param.getDataSourceId());
            return ActionResult.isSuccess();
        }
        try {
            jdbcDataTemplate.getConnection().close();
        } catch (SQLException e) {
            throw new BusinessException("数据库关闭连接异常", e);
        }
        return ActionResult.isSuccess();
    }
}
