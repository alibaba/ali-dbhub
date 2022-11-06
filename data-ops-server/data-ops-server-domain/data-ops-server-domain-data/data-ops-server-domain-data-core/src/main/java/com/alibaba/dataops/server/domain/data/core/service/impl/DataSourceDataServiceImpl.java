package com.alibaba.dataops.server.domain.data.core.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.data.api.model.DataSourceConnectDTO;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCloseParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceCreateParam;
import com.alibaba.dataops.server.domain.data.api.param.datasource.DataSourceTestParam;
import com.alibaba.dataops.server.domain.data.api.service.DataSourceDataService;
import com.alibaba.dataops.server.domain.data.core.model.DataDataSource;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.domain.data.core.util.DataCenterUtils;
import com.alibaba.dataops.server.tools.base.excption.CommonErrorEnum;
import com.alibaba.dataops.server.tools.base.excption.SystemException;
import com.alibaba.dataops.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dataops.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dataops.server.tools.common.util.EasyEnumUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
    public DataResult<DataSourceConnectDTO> test(DataSourceTestParam param) {
        DbTypeEnum dbType = EasyEnumUtils.getEnum(DbTypeEnum.class, param.getDbType());
        DataDataSource dataDataSource = DataDataSource.builder()
            .dbType(dbType)
            .url(param.getUrl())
            .username(param.getUsername())
            .password(param.getPassword())
            .build();

        DataSourceConnectDTO dataSourceConnect = testConnect(dataDataSource, dbType);
        return DataResult.of(dataSourceConnect);
    }

    private DataSourceConnectDTO testConnect(DataSource dataSource, DbTypeEnum dbType) {
        DataSourceConnectDTO dataSourceConnect = DataSourceConnectDTO.builder()
            .success(Boolean.TRUE)
            .build();

        // 加载驱动
        try {
            Class.forName(dbType.getClassName());
        } catch (ClassNotFoundException e) {
            // 理论上不会发生
            throw new SystemException(CommonErrorEnum.PARAM_ERROR, e);
        }

        // 创建连接
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            log.info("连接数据库失败", e);
            dataSourceConnect.setSuccess(Boolean.FALSE);
            // 获取最后一个异常的信息给前端
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            dataSourceConnect.setMessage(t.getMessage());
            return dataSourceConnect;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.info("测试连接，关闭数据库失败", e);
                }
            }
        }
        dataSourceConnect.setDescription("成功");
        return dataSourceConnect;
    }

    @Override
    public DataResult<DataSourceConnectDTO> create(DataSourceCreateParam param) {
        log.info("创建数据库连接源:{}", param.getDataSourceId());
        Long dataSourceId = param.getDataSourceId();
        // 尝试先关闭连接源
        close(DataSourceCloseParam.builder().dataSourceId(dataSourceId).build());

        DbTypeEnum dbType = EasyEnumUtils.getEnum(DbTypeEnum.class, param.getDbType());
        DataDataSource dataDataSource = DataDataSource.builder()
            .dbType(dbType)
            .url(param.getUrl())
            .username(param.getUsername())
            .password(param.getPassword())
            .build();

        DataSourceConnectDTO dataSourceConnect = testConnect(dataDataSource, dbType);
        if (BooleanUtils.isNotTrue(dataSourceConnect.getSuccess())) {
            // 参数有一次
            return DataResult.of(dataSourceConnect);
        }

        // 放入缓存
        DataCenterUtils.DATA_SOURCE_CACHE.put(dataSourceId, dataDataSource);

        // 创建一个默认的模板来执行基础sql
        DataCenterUtils.DEFAULT_JDBC_TEMPLATE_CACHE.put(dataSourceId, new NamedParameterJdbcTemplate(dataDataSource));

        return DataResult.of(dataSourceConnect);
    }

    @Override
    public ActionResult close(DataSourceCloseParam param) {
        DataDataSource dataDataSource = DataCenterUtils.DATA_SOURCE_CACHE.remove(param.getDataSourceId());
        if (dataDataSource == null) {
            log.info("数据库连接源:{}不需要关闭", param.getDataSourceId());
            return ActionResult.isSuccess();
        }

        // 关闭连接
        Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = DataCenterUtils.JDBC_TEMPLATE_CACHE.remove(
            param.getDataSourceId());
        if (MapUtils.isNotEmpty(jdbcDataTemplateMap)) {
            for (JdbcDataTemplate jdbcDataTemplate : jdbcDataTemplateMap.values()) {
                try {
                    jdbcDataTemplate.getConnection().close();
                } catch (SQLException e) {
                    log.error("关闭数据库连接异常", e);
                }
            }
        }

        // 关闭连接
        DataCenterUtils.DEFAULT_JDBC_TEMPLATE_CACHE.remove(param.getDataSourceId());
        return ActionResult.isSuccess();
    }

}
