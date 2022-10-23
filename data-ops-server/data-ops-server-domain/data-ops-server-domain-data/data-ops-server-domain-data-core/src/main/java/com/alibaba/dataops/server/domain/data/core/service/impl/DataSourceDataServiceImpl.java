package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.data.api.model.DatabaseDTO;
import com.alibaba.dataops.server.domain.data.api.model.TableBriefDTO;
import com.alibaba.dataops.server.domain.data.api.model.TableDTO;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceDescTableParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceShowCreateTableParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceShowDatabasesParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceShowTablesParam;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.core.model.DataSourceWrapper;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dataops.server.tools.common.util.EasyEnumUtils;
import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

/**
 * 数据库连接源服务
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class DataSourceDataServiceImpl implements DataSourceDataService {

    @Override
    public ActionResult create(DataSourceCreateParam param) {
        log.info("创建数据库连接源:{}", param.getDataSourceId());
        Long dataSourceId = param.getDataSourceId();
        // 尝试先关闭连接源
        close(DataSourceCloseParam.builder().dataSourceId(dataSourceId).build());

        DbTypeEnum driverClass = EasyEnumUtils.getEnum(DbTypeEnum.class, param.getDbType());
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClass.getClassName());
        druidDataSource.setUrl(param.getUrl());
        druidDataSource.setPassword(param.getPassword());
        druidDataSource.setUrl(param.getUrl());
        // 不设置最大连接数
        druidDataSource.setMaxActive(999);
        druidDataSource.setInitialSize(0);

        // 放入缓存
        DataCenterUtils.DATA_SOURCE_CACHE.put(dataSourceId, DataSourceWrapper.builder()
            .driverClass(driverClass)
            .druidDataSource(druidDataSource)
            .build());
        return ActionResult.isSuccess();
    }

    @Override
    public ActionResult close(DataSourceCloseParam param) {
        DataSourceWrapper dataSourceWrapper = DataCenterUtils.DATA_SOURCE_CACHE.remove(param.getDataSourceId());
        if (dataSourceWrapper == null) {
            log.info("数据库连接源:{}不需要关闭", param.getDataSourceId());
            return ActionResult.isSuccess();
        }
        dataSourceWrapper.getDruidDataSource().close();

        // 关闭连接
        Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = DataCenterUtils.JDBC_TEMPLATE_CACHE.remove(
            param.getDataSourceId());
        if (MapUtils.isNotEmpty(jdbcDataTemplateMap)) {
            for (JdbcDataTemplate jdbcDataTemplate : jdbcDataTemplateMap.values()) {
                try {
                    jdbcDataTemplate.getConnection().close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ActionResult.isSuccess();
    }

    @Override
    public ListResult<DatabaseDTO> showDatabases(DataSourceShowDatabasesParam param) {
        return null;
    }

    @Override
    public ListResult<TableBriefDTO> showTables(DataSourceShowTablesParam param) {
        return null;
    }

    @Override
    public DataResult<TableDTO> descTable(DataSourceDescTableParam param) {
        return null;
    }

    @Override
    public ActionResult showCreateTable(DataSourceShowCreateTableParam param) {
        return null;
    }
}
