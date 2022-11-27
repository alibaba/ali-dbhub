package com.alibaba.dbhub.server.domain.data.core.dialect;

import com.alibaba.dbhub.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dbhub.server.domain.data.core.dialect.common.model.ExecutorTableColumnDTO;
import com.alibaba.dbhub.server.domain.data.core.dialect.common.model.ExecutorTableDTO;
import com.alibaba.dbhub.server.domain.data.core.dialect.common.model.ExecutorTableIndexDTO;
import com.alibaba.dbhub.server.domain.data.core.dialect.common.param.ExecutorColumnQueryParam;
import com.alibaba.dbhub.server.domain.data.core.dialect.common.param.ExecutorIndexQueryParam;
import com.alibaba.dbhub.server.domain.data.core.dialect.common.param.ExecutorTablePageQueryParam;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;

/**
 * sql执行器
 *
 * @author 是仪
 */
public interface SqlExecutor {

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
    PageResult<ExecutorTableDTO> pageQueryTable(ExecutorTablePageQueryParam param);

    /**
     * 查询列的信息
     *
     * @param param
     * @return
     */
    ListResult<ExecutorTableColumnDTO> queryListColumn(ExecutorColumnQueryParam param);

    /**
     * 查询列的信息
     *
     * @param param
     * @return
     */
    ListResult<ExecutorTableIndexDTO> queryListIndex(ExecutorIndexQueryParam param);
}
