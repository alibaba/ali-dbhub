package com.alibaba.dataops.server.domain.data.core.converter;

import java.util.List;

import com.alibaba.dataops.server.domain.data.api.model.TableColumnDTO;
import com.alibaba.dataops.server.domain.data.api.model.TableDTO;
import com.alibaba.dataops.server.domain.data.api.model.TableIndexDTO;
import com.alibaba.dataops.server.domain.data.api.param.table.TablePageQueryParam;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorColumnQueryParam;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorIndexQueryParam;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorTableColumnDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorTableDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorTableIndexDTO;
import com.alibaba.dataops.server.domain.data.core.dialect.ExecutorTablePageQueryParam;
import com.alibaba.dataops.server.domain.data.core.service.impl.TableDataServiceImpl.QueryContext;

import org.mapstruct.Mapper;

/**
 * 转换器
 *
 * @author zyb
 */
@Mapper(componentModel = "spring")
public abstract class TableCoreConverter {

    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<TableDTO> dto2dto(List<ExecutorTableDTO> list);

    /**
     * 转换
     *
     * @param param
     * @return
     */
    public abstract ExecutorTablePageQueryParam param2param(TablePageQueryParam param);

    /**
     * 转换
     *
     * @param context
     * @return
     */
    public abstract ExecutorColumnQueryParam context2paramColumn(QueryContext context);

    /**
     * 转换
     *
     * @param context
     * @return
     */
    public abstract ExecutorIndexQueryParam context2paramIndex(QueryContext context);

    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<TableColumnDTO> dto2dtoColumn(List<ExecutorTableColumnDTO> list);


    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<TableIndexDTO> dto2dtoIndex(List<ExecutorTableIndexDTO> list);
}
