package com.alibaba.dbhub.server.domain.support.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.support.model.DataSourceConnect;
import com.alibaba.dbhub.server.domain.support.operations.DataSourceOperations;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCloseParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceCreateParam;
import com.alibaba.dbhub.server.domain.support.param.datasource.DataSourceTestParam;
import com.alibaba.dbhub.server.domain.support.sql.DbhubDataSource;
import com.alibaba.dbhub.server.tools.base.excption.CommonErrorEnum;
import com.alibaba.dbhub.server.tools.base.excption.SystemException;
import com.alibaba.dbhub.server.tools.common.util.EasyEnumUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

/**
 * 数据库连接源服务
 *
 * @author Jiaju Zhuang
 */
@Service
@Slf4j
public class DataSourceTemplate implements DataSourceOperations {

    @Override
    public DataSourceConnect test(DataSourceTestParam param) {
        DbTypeEnum dbType = EasyEnumUtils.getEnum(DbTypeEnum.class, param.getDbType());
        return testConnect(param.getUrl(),param.getUsername(),param.getPassword(), dbType);
    }

    private DataSourceConnect testConnect(String url,String userName,String password, DbTypeEnum dbType) {
        DataSourceConnect dataSourceConnect = DataSourceConnect.builder()
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
            connection = DriverManager.getConnection(url,userName,password);
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
    public DataSourceConnect create(DataSourceCreateParam param) {
        log.info("创建数据库连接源:{}", param.getDataSourceId());
        Long dataSourceId = param.getDataSourceId();
        // 尝试先关闭连接源
        DbTypeEnum dbType = EasyEnumUtils.getEnum(DbTypeEnum.class, param.getDbType());
        DataSourceConnect dataSourceConnect = testConnect(param.getUrl(),param.getUsername(), param.getPassword(), dbType);
        if (BooleanUtils.isNotTrue(dataSourceConnect.getSuccess())) {
            // 参数有一次
            return dataSourceConnect;
        }
        // 放入缓存
        return dataSourceConnect;
    }

    @Override
    public void close(DataSourceCloseParam param) {
        DbhubDataSource.getInstance().close();
    }

}
