package com.alibaba.dataops.server.domain.data.core.util;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.tools.base.excption.CommonErrorEnum;
import com.alibaba.dataops.server.tools.base.excption.SystemException;
import com.alibaba.druid.DbType;

/**
 * 驱动类工具
 *
 * @author Jiaju Zhuang
 */
public class DriverClassUtils {

    /**
     * 将一个驱动类型转换成数据库类型
     *
     * @param driverClass
     * @return
     */
    public static DbType parse2dbType(DbTypeEnum driverClass) {
        switch (driverClass) {
            case H2:
                return DbType.h2;
            case MYSQL:
                return DbType.mysql;
            case ORACLE:
                return DbType.oracle;
            case SQLITE:
                return DbType.sqlite;
            default:
                throw new SystemException(CommonErrorEnum.PARAM_ERROR);
        }
    }
}
