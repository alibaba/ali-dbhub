/** alibaba.com Inc. Copyright (c) 2004-2023 All Rights Reserved. */
package com.alibaba.dbhub.server.domain.support.dialect.common;

/**
 * 需要执行的sql类型
 *
 * @author jipengfei
 * @version : SQLType.java
 */
public enum SQLType {
    MODIFY_DATABASE,

    CREATE_DATABASE,

    DROP_DATABASE,

    CREATE_SCHEMA,
    DROP_SCHEMA,
    MODIFY_SCHEMA;
}
