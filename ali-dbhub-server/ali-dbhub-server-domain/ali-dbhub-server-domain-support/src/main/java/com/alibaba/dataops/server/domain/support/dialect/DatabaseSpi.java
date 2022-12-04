package com.alibaba.dataops.server.domain.support.dialect;

import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTable;
import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTableColumn;
import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTableIndex;
import com.alibaba.dataops.server.domain.support.dialect.common.param.SpiColumnQueryParam;
import com.alibaba.dataops.server.domain.support.dialect.common.param.SpiIndexQueryParam;
import com.alibaba.dataops.server.domain.support.dialect.common.param.SpiTablePageQueryParam;
import com.alibaba.dataops.server.domain.support.enums.DbTypeEnum;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
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
    ListResult<SpiTableColumn> queryListColumn(SpiColumnQueryParam param);

    /**
     * 查询列的信息
     *
     * @param param
     * @return
     */
    ListResult<SpiTableIndex> queryListIndex(SpiIndexQueryParam param);
}
