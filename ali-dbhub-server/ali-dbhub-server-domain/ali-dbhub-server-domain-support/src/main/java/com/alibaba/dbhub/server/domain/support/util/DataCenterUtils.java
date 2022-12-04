package com.alibaba.dbhub.server.domain.support.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.support.dialect.DatabaseSpi;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.support.DataDataSource;
import com.alibaba.dbhub.server.domain.support.model.support.JdbcDataTemplate;
import com.alibaba.dbhub.server.domain.support.operations.ConsoleOperations;
import com.alibaba.dbhub.server.domain.support.param.console.ConsoleCreateParam;
import com.alibaba.dbhub.server.tools.base.excption.BusinessException;
import com.alibaba.dbhub.server.tools.base.excption.CommonErrorEnum;
import com.alibaba.dbhub.server.tools.base.excption.SystemException;
import com.alibaba.dbhub.server.tools.common.enums.ErrorEnum;
import com.alibaba.dbhub.server.tools.common.util.EasyCollectionUtils;
import com.alibaba.druid.DbType;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 用来存储连接等数据
 *
 * @author 是仪
 */
@Component
public class DataCenterUtils implements InitializingBean {
    @Autowired
    private List<DatabaseSpi> databaseSpiList;
    @Resource
    private ConsoleOperations consoleOperations;
    private static ConsoleOperations consoleOperationsStatic;

    /**
     * sql执行器的列表
     */
    private static final Map<DbTypeEnum, DatabaseSpi> SQL_EXECUTOR_MAP = new HashMap<>();
    /**
     * 数据源
     * key: dataSourceId
     */
    public static final Map<Long, DataDataSource> DATA_SOURCE_CACHE = Maps.newConcurrentMap();

    /**
     * 数据执行模板列表
     * key: dataSourceId
     */
    public static final Map<Long, Map<Long, JdbcDataTemplate>> JDBC_TEMPLATE_CACHE = Maps.newConcurrentMap();

    /**
     * 默认的的数据执行模板列表
     * key: dataSourceId
     */
    public static final Map<Long, NamedParameterJdbcTemplate> DEFAULT_JDBC_TEMPLATE_CACHE = Maps.newConcurrentMap();

    /**
     * 获取默认的JdbcDataTemplate
     *
     * @param dataSourceId
     * @return
     */
    public static NamedParameterJdbcTemplate getDefaultJdbcTemplate(Long dataSourceId) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = DEFAULT_JDBC_TEMPLATE_CACHE.get(dataSourceId);
        if (namedParameterJdbcTemplate == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        return namedParameterJdbcTemplate;
    }

    /**
     * 根据  dataSourceId  consoleId 获取JdbcDataTemplate
     *
     * @param dataSourceId
     * @param consoleId
     * @param databaseName
     * @return
     */
    public static JdbcDataTemplate getJdbcDataTemplate(Long dataSourceId, Long consoleId, String databaseName) {
        Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = JDBC_TEMPLATE_CACHE.get(dataSourceId);
        if (jdbcDataTemplateMap == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        JdbcDataTemplate jdbcDataTemplate = jdbcDataTemplateMap.get(consoleId);
        if (jdbcDataTemplate == null) {
            consoleOperationsStatic.create(ConsoleCreateParam.builder()
                .dataSourceId(dataSourceId)
                .consoleId(consoleId)
                .databaseName(databaseName).build());
            jdbcDataTemplate = jdbcDataTemplateMap.get(consoleId);
            if (jdbcDataTemplate == null) {
                throw new BusinessException(ErrorEnum.CONSOLE_NOT_FOUND);
            }
        }
        return jdbcDataTemplate;
    }

    /**
     * 根据dataSourceId 获取数据库类型
     *
     * @param dataSourceId
     * @return
     */
    public static DbTypeEnum getDbTypeByDataSourceId(Long dataSourceId) {
        DataDataSource dataDataSource = DATA_SOURCE_CACHE.get(dataSourceId);
        if (dataDataSource == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        return dataDataSource.getDbType();
    }

    /**
     * 根据dataSourceId 获取Druid数据库类型
     *
     * @param dataSourceId
     * @return
     */
    public static DbType getDruidDbTypeByDataSourceId(Long dataSourceId) {
        return JdbcUtils.parse2DruidDbType(getDbTypeByDataSourceId(dataSourceId));
    }

    /**
     * 根据dataSourceId 获取Dialect方言类型
     *
     * @param dataSourceId
     * @return
     */
    public static DatabaseSpi getDatabaseSpiByDataSourceId(Long dataSourceId) {
        return getDatabaseSpi(getDbTypeByDataSourceId(dataSourceId));
    }

    /**
     * 根据dataSourceId 获取Dialect方言类型
     *
     * @param dbType
     * @return
     */
    public static DatabaseSpi getDatabaseSpi(DbTypeEnum dbType) {
        DatabaseSpi databaseSpi = SQL_EXECUTOR_MAP.get(dbType);
        if (databaseSpi == null) {
            throw new SystemException(CommonErrorEnum.PARAM_ERROR);
        }
        return databaseSpi;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SQL_EXECUTOR_MAP.putAll(EasyCollectionUtils.toIdentityMap(databaseSpiList, DatabaseSpi::supportDbType));
        consoleOperationsStatic = consoleOperations;
    }
}
