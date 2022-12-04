package com.alibaba.dbhub.server.domain.support.dialect;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiExample;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiTable;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiTableColumn;
import com.alibaba.dbhub.server.domain.support.dialect.common.model.SpiTableIndex;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiColumnQueryParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiDropParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiIndexQueryParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiShowCrateTableParam;
import com.alibaba.dbhub.server.domain.support.dialect.common.param.SpiTablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

/**
 * sql执行器
 *
 * @author 是仪
 */
public interface DatabaseSpi {

    /**
     * 支持的数据库类型
     *
     * @return
     */
    DbTypeEnum supportDbType();

    /**
     * 样例
     *
     * @return
     */
    SpiExample example();

    /**
     * 展示建表语句
     *
     * @param param
     * @return
     */
    String showCrateTable(SpiShowCrateTableParam param);

    /**
     * 删除表结构
     *
     * @param param
     * @return
     */
    void drop(SpiDropParam param);

    /**
     * 分页查询表信息
     *
     * @param param
     * @return
     */
    PageResult<SpiTable> pageQueryTable(SpiTablePageQueryParam param);

    /**
     * 查询列的信息
     *
     * @param param
     * @return
     */
    List<SpiTableColumn> queryListColumn(SpiColumnQueryParam param);

    /**
     * 查询列的信息
     *
     * @param param
     * @return
     */
    List<SpiTableIndex> queryListIndex(SpiIndexQueryParam param);
}
