
package com.alibaba.dbhub.server.domain.support.operations;

import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;

/**
 * 样例
 *
 * @author Jiaju Zhuang
 */
public interface ExampleOperations {

    /**
     * 创建表结构的样例
     *
     * @return
     */
    String createTable(DbTypeEnum dbType);

    /**
     * 修改表结构的样例
     *
     * @return
     */
    String alterTable(DbTypeEnum dbType);

}
