package com.alibaba.dbhub.server.domain.support.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dbhub.server.domain.support.dialect.DatabaseMetaSchema;
import com.alibaba.dbhub.server.domain.support.dialect.MetaSchema;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.support.DataDataSource;
import com.alibaba.dbhub.server.domain.support.model.support.JdbcAccessor;
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
import org.apache.ibatis.session.SqlSession;
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

    @Resource
    private ConsoleOperations consoleOperations;

    private static ConsoleOperations consoleOperationsStatic;

    /**
     * 数据执行模板列表
     * key: dataSourceId
     */
    public static final Map<Long, Map<Long, JdbcDataTemplate>> JDBC_TEMPLATE_CACHE = Maps.newConcurrentMap();


    /**
     * mybatis模板
     */
    public static final Map<Long, JdbcAccessor> JDBC_ACCESSOR_MAP = Maps.newConcurrentMap();

    /**
     * 获取默认的JdbcDataTemplate
     *
     * @param dataSourceId
     * @return
     */
    public static NamedParameterJdbcTemplate getDefaultJdbcTemplate(Long dataSourceId) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = JDBC_ACCESSOR_MAP.get(dataSourceId).getJdbcTemplate();
        if (namedParameterJdbcTemplate == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        return namedParameterJdbcTemplate;
    }

    public static JdbcAccessor getJdbcAccessor(Long dataSourceId) {
        JdbcAccessor jdbcAccessor = JDBC_ACCESSOR_MAP.get(dataSourceId);
        if (jdbcAccessor == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        return jdbcAccessor;
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
        DataDataSource dataDataSource = JDBC_ACCESSOR_MAP.get(dataSourceId).getDataDataSource();
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

    public static MetaSchema getMetaSchema(Long dataSourceId) {
        return JDBC_ACCESSOR_MAP.get(dataSourceId).getService();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //SQL_EXECUTOR_MAP.putAll(EasyCollectionUtils.toIdentityMap(databaseMetaSchemaList, DatabaseMetaSchema::supportDbType));
        //META_SCHEMA_SERVICE_MAP.putAll(EasyCollectionUtils.toIdentityMap(metaSchemaServiceList, MetaSchema::supportDbType));
        consoleOperationsStatic = consoleOperations;
    }
}
