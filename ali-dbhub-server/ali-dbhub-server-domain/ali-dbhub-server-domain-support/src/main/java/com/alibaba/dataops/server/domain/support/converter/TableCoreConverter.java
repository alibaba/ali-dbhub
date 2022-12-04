package com.alibaba.dataops.server.domain.support.converter;

import java.util.List;

import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTable;
import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTableColumn;
import com.alibaba.dataops.server.domain.support.dialect.common.model.SpiTableIndex;
import com.alibaba.dataops.server.domain.support.dialect.common.param.SpiColumnQueryParam;
import com.alibaba.dataops.server.domain.support.dialect.common.param.SpiIndexQueryParam;
import com.alibaba.dataops.server.domain.support.dialect.common.param.SpiTablePageQueryParam;
import com.alibaba.dataops.server.domain.support.model.Table;
import com.alibaba.dataops.server.domain.support.model.TableColumn;
import com.alibaba.dataops.server.domain.support.model.TableIndex;
import com.alibaba.dataops.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dataops.server.domain.support.template.TableTemplate.QueryContext;

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
    public abstract List<Table> dto2dto(List<SpiTable> list);

    /**
     * 转换
     *
     * @param param
     * @return
     */
    public abstract SpiTablePageQueryParam param2param(TablePageQueryParam param);

    /**
     * 转换
     *
     * @param context
     * @return
     */
    public abstract SpiColumnQueryParam context2paramColumn(QueryContext context);

    /**
     * 转换
     *
     * @param context
     * @return
     */
    public abstract SpiIndexQueryParam context2paramIndex(QueryContext context);

    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<TableColumn> dto2dtoColumn(List<SpiTableColumn> list);


    /**
     * 转换
     *
     * @param list
     * @return
     */
    public abstract List<TableIndex> dto2dtoIndex(List<SpiTableIndex> list);
}
