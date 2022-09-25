package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.console.ConsoleCreateParam;
import com.alibaba.dataops.server.domain.data.api.service.ConsoleDataService;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.druid.pool.DruidDataSource;

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
        DruidDataSource druidDataSource = DataCenterUtils.DATA_SOURCE_CACHE.get(param.getDataSourceId());
        if (druidDataSource == null) {
            throw new BusinessException("当前数据源已经被关闭请重新打开数据源");
        }
        Long consoleId = param.getConsoleId();
        // 尝试关闭
        close(ConsoleCloseParam.builder().consoleId(consoleId).build());

        Connection connection;
        try {
            connection = druidDataSource.getConnection().getConnection();
        } catch (SQLException e) {
            throw new BusinessException("连接数据库异常", e);
        }

        // 这里注意 JdbcTemplate 实际上放的是一个 固定连接的DataSource 不会使用到多个连接
        DataCenterUtils.JDBC_TEMPLATE_CACHE.put(consoleId, JdbcDataTemplate.builder().connection(connection).build());
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult close(ConsoleCloseParam param) {
        JdbcDataTemplate jdbcDataTemplate = DataCenterUtils.JDBC_TEMPLATE_CACHE.remove(param.getConsoleId());
        if (jdbcDataTemplate == null) {
            log.info("数据库连接:{}不需要关闭", param.getConsoleId());
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
