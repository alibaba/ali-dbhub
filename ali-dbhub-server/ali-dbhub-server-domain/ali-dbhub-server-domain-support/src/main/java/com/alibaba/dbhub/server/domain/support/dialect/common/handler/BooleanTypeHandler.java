/**
 * alibaba.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alibaba.dbhub.server.domain.support.dialect.common.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.dbhub.server.tools.base.enums.YesOrNoEnum;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author jipengfei
 * @version : NullableTypeHandler.java
 */
public class BooleanTypeHandler implements TypeHandler<Boolean> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
        if (YesOrNoEnum.YES.getCode().equalsIgnoreCase(rs.getString(columnName))) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
        if (YesOrNoEnum.YES.getCode().equalsIgnoreCase(rs.getString(columnIndex))) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (YesOrNoEnum.YES.getCode().equalsIgnoreCase(cs.getString(columnIndex))) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}