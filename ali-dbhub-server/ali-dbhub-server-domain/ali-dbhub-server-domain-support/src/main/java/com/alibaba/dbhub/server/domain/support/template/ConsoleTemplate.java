package com.alibaba.dbhub.server.domain.support.template;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.alibaba.dbhub.server.domain.support.model.support.DbhubDataSource;
import com.alibaba.dbhub.server.domain.support.model.support.JdbcAccessor;
import com.alibaba.dbhub.server.domain.support.model.support.JdbcDataTemplate;
import com.alibaba.dbhub.server.domain.support.operations.ConsoleOperations;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCloseParam;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCreateParam;
import com.alibaba.dbhub.server.domain.support.util.DataCenterUtils;
import com.alibaba.dbhub.server.tools.base.excption.BusinessException;
import com.alibaba.dbhub.server.tools.base.excption.CommonErrorEnum;
import com.alibaba.dbhub.server.tools.base.excption.SystemException;
import com.alibaba.dbhub.server.tools.common.enums.ErrorEnum;

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
public class ConsoleTemplate implements ConsoleOperations {

    @Override
    public void create(ConsoleCreateParam param) {
        JdbcAccessor jdbcAccessor = DataCenterUtils.JDBC_ACCESSOR_MAP.get(param.getDataSourceId());
        if (jdbcAccessor == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        DbhubDataSource dbhubDataSource = jdbcAccessor.getDbhubDataSource();
        if (dbhubDataSource == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }

        Long consoleId = param.getConsoleId();
        switch (dbhubDataSource.getDbType()) {
            case MYSQL, H2 -> {
                // 尝试关闭
                close(ConsoleCloseParam.builder().dataSourceId(param.getDataSourceId()).consoleId(consoleId).build());
                Connection connection;
                try {
                    connection = dbhubDataSource.getConnection();
                } catch (SQLException e) {
                    throw new BusinessException("连接数据库异常", e);
                }
                // 放入连接队列
                Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = DataCenterUtils.JDBC_TEMPLATE_CACHE.computeIfAbsent(
                    param.getDataSourceId(), key -> Maps.newConcurrentMap());
                JdbcDataTemplate jdbcDataTemplate = new JdbcDataTemplate(param.getDataSourceId(), consoleId, connection,
                    dbhubDataSource);
                jdbcDataTemplateMap.put(consoleId, jdbcDataTemplate);

                // 切换到当前database
                try {
                    jdbcDataTemplate.execute("use " + param.getDatabaseName() + ";", null);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            case POSTGRESQL -> {
                close(ConsoleCloseParam.builder().dataSourceId(param.getDataSourceId()).consoleId(consoleId).build());
                if (!param.getDatabaseName().equals(dbhubDataSource.getDatabase())) {
                    dbhubDataSource.setDatabase(param.getDatabaseName());
                }
                Connection connection;
                try {
                    connection = dbhubDataSource.getConnection();
                } catch (SQLException e) {
                    throw new BusinessException("连接数据库异常", e);
                }
                // 放入连接队列
                Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = DataCenterUtils.JDBC_TEMPLATE_CACHE.computeIfAbsent(
                    param.getDataSourceId(), key -> Maps.newConcurrentMap());
                JdbcDataTemplate jdbcDataTemplate = new JdbcDataTemplate(param.getDataSourceId(), consoleId, connection,
                    dbhubDataSource);
                jdbcDataTemplateMap.put(consoleId, jdbcDataTemplate);
                DataCenterUtils.JDBC_ACCESSOR_MAP.put(param.getDataSourceId(),
                    new JdbcAccessor(param.getDataSourceId(), dbhubDataSource));
            }
            default -> throw new SystemException(CommonErrorEnum.PARAM_ERROR);
        }

    }

    @Override
    public void close(ConsoleCloseParam param) {
        Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = DataCenterUtils.JDBC_TEMPLATE_CACHE.get(
            param.getDataSourceId());

        if (jdbcDataTemplateMap == null) {
            log.info("数据库连接:{}不需要关闭", param.getDataSourceId());
            return;
        }
        JdbcDataTemplate jdbcDataTemplate = jdbcDataTemplateMap.remove(param.getConsoleId());
        if (jdbcDataTemplate == null) {
            log.info("数据库连接:{}不需要关闭", param.getDataSourceId());
            return;
        }
        try {
            jdbcDataTemplate.getConnection().close();
        } catch (SQLException e) {
            log.error("关闭数据库连接异常", e);
        }

        JdbcAccessor jdbcAccessor = DataCenterUtils.JDBC_ACCESSOR_MAP.get(param.getDataSourceId());
        if (jdbcAccessor == null) {
            log.info("数据库连接:{}不需要关闭", param.getDataSourceId());
        } else if (jdbcAccessor.getSqlSession() != null) {
            jdbcAccessor.getSqlSession().close();
        }

    }
}
